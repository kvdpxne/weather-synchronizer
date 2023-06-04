package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;

public final class AsynchronousWeatherChanger
  implements Runnable {

  private final OpenWeatherCaller caller;

  AsynchronousWeatherChanger(final OpenWeatherCaller caller) {
    this.caller = caller;
  }

  private synchronized void changeWeather(
    final World world,
    final boolean thunder,
    final boolean rain
  ) {
    world.setThundering(thunder);
    world.setStorm(rain);
  }

  @Override
  public void run() {
    Weather currentWeather;

    try {
      synchronized (this.caller) {
        this.caller.requestGeographyCoordinates();
        currentWeather = this.caller.requestWeather();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    for (final World world : Bukkit.getWorlds()) {
      if (!world.getEnvironment().equals(World.Environment.NORMAL)) {
        continue;
      }

      if (currentWeather.isClear()) {
        this.changeWeather(world, false, false);
        return;
      }

      if (currentWeather.isRain()) {
        this.changeWeather(world, false, true);
        return;
      }

      if (currentWeather.isThunderstorm()) {
        this.changeWeather(world, true, true);
      }
    }
  }
}
