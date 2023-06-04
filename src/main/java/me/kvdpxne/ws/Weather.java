package me.kvdpxne.ws;

public final class Weather {

  private final int identifier;

  public Weather(final int identifier) {
    this.identifier = identifier;
  }

  public boolean isThunderstorm() {
    return 200 <= this.identifier && 300 > this.identifier;
  }

  public boolean isRain() {
    return 300 <= this.identifier && 400 > this.identifier
      || 500 <= this.identifier && 600 > this.identifier;
  }

  public boolean isClear() {
    return 800 <= this.identifier && 900 > this.identifier;
  }
}
