package me.kvdpxne.ws;

public final class CurrentWeather {

  /**
   * A unique identifier that specifies weather.
   */
  private final int identifier;

  boolean changed;

  /**
   * Creates a new instance of the current weather condition given a unique
   * identifier that specifies the current weather.
   *
   * @param identifier A unique weather identifier.
   */
  CurrentWeather(final int identifier) {
    this.identifier = identifier;
  }

  /**
   * Checks whether the unique identifier specifies stormy weather.
   */
  public boolean isStormy() {
    return 200 <= this.identifier && 300 > this.identifier;
  }

  /**
   * Checks whether the unique identifier specifies rainy weather.
   * <br>
   * OpenWeather specifies different states of rainy weather such as drizzle,
   * rain and snow but the game does not support drizzle, snow is treated as
   * rain in specific biomes, therefore all three states will be treated as
   * rainy weather.
   */
  public boolean isRainy() {
    return 300 <= this.identifier && 700 > this.identifier;
  }

  public boolean wasChanged() {
    return this.changed;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    //
    if (null == o || this.getClass() != o.getClass()) {
      return false;
    }

    final CurrentWeather that = (CurrentWeather) o;
    return this.identifier == that.identifier;
  }

  @Override
  public int hashCode() {
    return this.identifier;
  }

  @Override
  public String toString() {
    return "CurrentWeather{"
      + "identifier=" + this.identifier
      + ", isRain=" + this.isRainy()
      + ", isThunderstorm=" + this.isStormy()
      + '}';
  }
}
