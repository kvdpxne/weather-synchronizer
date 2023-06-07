package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

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
    final Weather weather;

    synchronized (this.caller) {
      this.caller.requestGeographyCoordinates();
      weather = this.caller.requestWeather();
    }

    if (null == weather) {
      return;
    }

    for (final World world : Bukkit.getWorlds()) {
      // Changing the weather in a world with a non-normal environment will
      // not have any visual effects, therefore it is best to skip all worlds
      // with other environments such as the nether.
      if (!world.getEnvironment().equals(Environment.NORMAL)) {
        continue;
      }

      final boolean thunderstorm = weather.isThunderstorm();
      this.changeWeather(world, thunderstorm, thunderstorm || weather.isRain());
    }
  }
}
