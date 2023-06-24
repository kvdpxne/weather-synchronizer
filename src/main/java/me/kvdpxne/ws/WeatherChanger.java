package me.kvdpxne.ws;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;

final class WeatherChanger
  implements Runnable {

  private final WorldWeatherStorage storage;

  WeatherChanger(final WorldWeatherStorage storage) {
    this.storage = storage;
  }

  @Override
  public void run() {
    for (final WorldWeather worldWeather : this.storage.getCoordinates()) {
      final World world = Bukkit.getWorld(worldWeather.getIdentifier());
      if (null == world) {
        // TODO this.storage.removeCoordinates(coordinates);
        continue;
      }

      // Changing the weather in a world with a non-normal environment will
      // not have any visual effects, therefore it is best to skip all worlds
      // with other environments such as the nether.
      if (!world.getEnvironment().equals(Environment.NORMAL)) {
        continue;
      }

      final CurrentWeather weather = worldWeather.getCurrentWeather();

      final boolean stormy = weather.isStormy();
      final boolean rainy = weather.isRainy();

      world.setStorm(rainy || stormy);
      world.setThundering(stormy);
      world.setWeatherDuration(Integer.MAX_VALUE);
    }
  }
}
