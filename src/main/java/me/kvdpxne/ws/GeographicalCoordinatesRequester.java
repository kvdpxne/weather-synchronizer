package me.kvdpxne.ws;

import me.kvdpxne.cricket.Cricket;
import me.kvdpxne.cricket.CricketFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * An object to send requests to the api to obtain geographic coordinates from
 * given location information, such as city name, country code, or optionally
 * province name.
 */
public final class GeographicalCoordinatesRequester
  implements Requester<Coordinates> {

  private static final Cricket logger;

  static {
    logger = CricketFactory.of(GeographicalCoordinatesRequester.class);
  }

  private final String providedInformation;
  private Coordinates coordinates;

  /**
   * Creates a new instance of the object by the constructor from the given
   * location information.
   *
   * @param information Location information such as city name, country code,
   *                    and optionally a province name.
   */
  public GeographicalCoordinatesRequester(final String information) {
    this.providedInformation = information;
  }

  /**
   *
   */
  @Override
  public void run() {
    logger.debug("Preparing to send a request with a parameter: {}", this.providedInformation);

    final String url1 = "http://api.openweathermap.org/geo/1.0/direct"
      + "?q=" + this.providedInformation
      + "&appid=" + System.getenv("OPEN_WEATHER_KEY");

    HttpURLConnection connection = null;
    Reader reader = null;

    try {
      final URL url = new URL(url1);
      connection = (HttpURLConnection) url.openConnection();

      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");

      final int responseCode = connection.getResponseCode();
      if (HttpURLConnection.HTTP_OK != responseCode) {
        throw new OpenWeatherCallException("Response code: " + responseCode);
      }

      //
      reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
      this.coordinates = JsonDependencyInstance.getGson().fromJson(reader, Coordinates.class);
    } catch (final IOException exception) {
      exception.printStackTrace();
    } finally {
      try {
        // Closes the stream if it exists.
        if (null != reader) {
          reader.close();
        }

        // Disconnects if there is a connection.
        if (null != connection) {
          connection.disconnect();
        }
      } catch (final IOException exception) {
        exception.printStackTrace();
      }
    }
  }

  @Override
  public Coordinates getRequestResult() {
    return this.coordinates;
  }
}
