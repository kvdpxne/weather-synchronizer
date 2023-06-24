package me.kvdpxne.ws;

import org.junit.jupiter.api.Test;

final class LocationCurrentWeatherRequesterTest {

  @Test
  void sendRequest() {
    final Coordinates coordinates = new Coordinates(45.6523093D, 25.6102746D);
    final Requester<CurrentWeather> requester = new LocationCurrentWeatherRequester(coordinates);

    requester.run();

    final CurrentWeather weather = requester.getRequestResult();
    System.out.println(weather);
  }
}
