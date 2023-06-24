package me.kvdpxne.ws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class GeographicalCoordinatesRequesterTest {

  @Test
  void sendRequest() {
    final String information = "Lublin, PL";
    final Requester<Coordinates> requester = new GeographicalCoordinatesRequester(information);

    requester.run();

    final Coordinates expected = new Coordinates(51.250559D, 22.5701022D);
    final Coordinates actual = requester.getRequestResult();

    System.out.printf("Expected: %s, Actual: %s%n", expected, actual);
    Assertions.assertEquals(expected, actual);
  }
}
