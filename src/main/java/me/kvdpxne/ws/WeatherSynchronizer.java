package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeatherSynchronizer
  extends JavaPlugin {

  @Override
  public void onEnable() {
    final OpenWeatherCaller caller = new OpenWeatherCaller();
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AsynchronousWeatherChanger(caller), 100L, 18_000L);
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
  }
}
