package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeatherSynchronizer
  extends JavaPlugin {

  private final Settings settings;

  private final OpenWeatherCaller caller;

  public WeatherSynchronizer() {
    this.settings = new Settings();
    this.caller = new OpenWeatherCaller(this.settings);
  }

  @Override
  public void onLoad() {
    this.settings.export();
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
  }
}
