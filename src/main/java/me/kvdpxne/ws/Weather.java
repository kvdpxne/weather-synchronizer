package me.kvdpxne.ws;

public final class Weather {

  private final int identifier;

  Weather(final int identifier) {
    this.identifier = identifier;
  }

  public boolean isThunderstorm() {
    return 200 <= this.identifier && 300 > this.identifier;
  }

  public boolean isRain() {
    return 300 <= this.identifier && 700 > this.identifier;
  }
}
