package me.kvdpxne.ws;

final class Coordinates {

  private final float latitude;
  private final float longitude;

  Coordinates(final float latitude, final float longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Geographical coordinates of the found location.
   */
  public float getLatitude() {
    return this.latitude;
  }

  /**
   * Geographical coordinates of the found location.
   */
  public float getLongitude() {
    return this.longitude;
  }
}
