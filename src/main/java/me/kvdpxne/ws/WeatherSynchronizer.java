package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@SuppressWarnings("unused")
public final class WeatherSynchronizer
  extends JavaPlugin {

  private final WorldWeatherStorage storage;
  private final Settings settings;

  private final OpenWeatherCaller caller;

  public WeatherSynchronizer() {
    final File rootDirectory = this.getDataFolder();
    if (!rootDirectory.exists()) {
      // noinspection ResultOfMethodCallIgnored
      rootDirectory.mkdir();
    }

    this.storage = new WorldWeatherStorage(rootDirectory.toPath());
    this.settings = new Settings();

    this.caller = new OpenWeatherCaller(this.settings);
  }

  @Override
  public void onLoad() {
    this.settings.export();

    this.storage.loadAll();
    this.settings.load();
  }

  @Override
  public void onEnable() {
    Bukkit.getScheduler().runTaskTimerAsynchronously(
      this,
      new WeatherChanger(this.storage),
      100L,
      18_000L
    );
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
    this.storage.saveAll();
  }
}
