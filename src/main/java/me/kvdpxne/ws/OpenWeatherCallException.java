package me.kvdpxne.ws;

final class OpenWeatherCallException
  extends RuntimeException {

  OpenWeatherCallException(final String message) {
    super(message);
  }
}
