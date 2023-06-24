package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

@SuppressWarnings("unused")
public final class WeatherSynchronizer
  extends JavaPlugin {

  private final WorldWeatherStorage storage;
  private final Settings settings;

  public WeatherSynchronizer() {
    final File rootDirectory = this.getDataFolder();
    if (!rootDirectory.exists()) {
      // noinspection ResultOfMethodCallIgnored
      rootDirectory.mkdir();
    }

    // Path to the root directory of the plugin.
    final Path path = rootDirectory.toPath();

    this.storage = new WorldWeatherStorage(path);
    this.settings = new Settings(path);
  }

  @Override
  public void onLoad() {
    this.settings.export();

    this.storage.loadAll();
    this.settings.load();
  }

  @Override
  public void onEnable() {
    final Thread thread = new Thread(() -> {
      for (Map.Entry<String, String> entry : this.settings.getLocationByWorld().entrySet()) {
        final GeographicalCoordinatesRequester requester = new GeographicalCoordinatesRequester(entry.getValue());
        final Thread thread2 = new Thread(requester);
        thread2.start();
        try {
          thread2.join();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        final World world = Bukkit.getWorld(entry.getKey());
        if (null == world) {
          System.out.println("world == null");
          continue;
        }
        final Coordinates coordinates = requester.getRequestResult();
        this.storage.addCoordinates(new WorldWeather(world.getUID(), coordinates.getLatitude(), coordinates.getLongitude()));
      }
    });
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    final BukkitScheduler scheduler = Bukkit.getScheduler();

    // TODO its not working
    this.storage.getCoordinates().forEach(coordinates -> {
      final LocationCurrentWeatherRequester task = new LocationCurrentWeatherRequester(coordinates);
      final BukkitTask task1= scheduler.runTaskTimerAsynchronously(this, task, 100L, 1_000L);
      System.out.println(task.getRequestResult());
      coordinates.updateCurrentWeather(task.getRequestResult());
    });

    scheduler.runTaskTimer(
      this,
      new WeatherChanger(this.storage),
      100L,
      1_000L
    );
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
    this.storage.saveAll();
  }
}
