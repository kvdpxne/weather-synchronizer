package me.kvdpxne.ws;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class LocationCurrentWeatherRequester
  implements Requester<CurrentWeather> {

  private final Coordinates coordinates;
  private CurrentWeather currentWeather;

  public LocationCurrentWeatherRequester(final Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  @Override
  public void run() {
    final String url1 = "https://api.openweathermap.org/data/2.5/weather"
      + "?lat=" + this.coordinates.getLatitude()
      + "&lon=" + this.coordinates.getLongitude()
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
      this.currentWeather = JsonDependencyInstance.getGson().fromJson(reader, CurrentWeather.class);

      if (this.coordinates instanceof WorldWeather) {
        ((WorldWeather) this.coordinates).updateCurrentWeather(this.currentWeather);
      }
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
  public CurrentWeather getRequestResult() {
    return this.currentWeather;
  }
}
