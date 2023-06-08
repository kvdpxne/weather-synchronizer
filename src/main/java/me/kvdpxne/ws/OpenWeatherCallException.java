package me.kvdpxne.ws;

public final class OpenWeatherCallException
  extends RuntimeException {

  OpenWeatherCallException(final String message) {
    super(message);
  }
}
