package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WeatherSynchronizer
  extends JavaPlugin {

  private final CoordinatesStorage dataManager;
  private final Settings settings;

  private final OpenWeatherCaller caller;

  public WeatherSynchronizer() {
    final File rootDirectory = this.getDataFolder();
    if (!rootDirectory.exists()) {
      // noinspection ResultOfMethodCallIgnored
      rootDirectory.mkdir();
    }

    this.dataManager = new CoordinatesStorage(rootDirectory.toPath());
    this.settings = new Settings();

    this.caller = new OpenWeatherCaller(this.settings);
  }

  @Override
  public void onLoad() {
    this.settings.export();

    this.dataManager.loadAll();
    this.settings.load();
  }

  @Override
  public void onEnable() {
    Bukkit.getScheduler().runTaskTimerAsynchronously(
      this,
      new AsynchronousWeatherChanger(this.caller),
      100L,
      18_000L
    );
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
    this.dataManager.saveAll();
  }
}
