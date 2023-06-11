package me.kvdpxne.ws;

public final class Coordinates {

  private final double latitude;
  private final double longitude;

  Coordinates(
    final double latitude,
    final double longitude
  ) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Geographical coordinates of the found location.
   */
  public double getLatitude() {
    return this.latitude;
  }

  /**
   * Geographical coordinates of the found location.
   */
  public double getLongitude() {
    return this.longitude;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Coordinates)) {
      return false;
    }

    final Coordinates that = (Coordinates) o;
    return 0 == Double.compare(that.latitude, this.latitude)
      && 0 == Double.compare(that.longitude, this.longitude);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(this.latitude);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.longitude);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString() {
    return "Coordinates{"
      + "latitude=" + this.latitude
      + ", longitude=" + this.longitude
      + '}';
  }
}
