package me.kvdpxne.ws;

import java.util.UUID;

public final class WorldWeather
  extends Coordinates {

  /**
   *
   */
  private final UUID identifier;
  private CurrentWeather currentWeather;

  WorldWeather(
    final UUID identifier,
    final double latitude,
    final double longitude
  ) {
    super(latitude, longitude);
    this.identifier = identifier;
  }

  /**
   * A unique identifier of the world to which the geographical coordinates of
   * the city from which the weather will be read belong.
   */
  public UUID getIdentifier() {
    return this.identifier;
  }

  public CurrentWeather getCurrentWeather() {
    return this.currentWeather;
  }

  public void updateCurrentWeather(final CurrentWeather newWeather) {
    this.currentWeather = newWeather;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    final WorldWeather that = (WorldWeather) o;
    return this.identifier.equals(that.identifier);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + this.identifier.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "WorldWithCoordinates{"
      + "identifier=" + this.identifier
      + ", latitude=" + this.getLatitude()
      + ", longitude=" + this.getLongitude()
      + ", weather=" + this.currentWeather.toString()
      + '}';
  }
}
