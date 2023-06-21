package me.kvdpxne.ws;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

final class OpenWeatherCaller {

  private static final String GEOGRAPHY_API =
    "http://api.openweathermap.org/geo/1.0/direct";

  private static final String WEATHER_API =
    "https://api.openweathermap.org/data/2.5/weather";

  private static final Type COORDINATES_TYPE =
    TypeToken.get(Coordinates.class).getType();

  private static final Type WEATHER_TYPE =
    TypeToken.get(CurrentWeather.class).getType();

  OpenWeatherCaller(final Settings settings) {

  }

  private String getOpenWeatherKey() {
    return System.getenv("OPEN_WEATHER_KEY");
  }

  public <T> T openConnection(
    final String target,
    final Function<Reader, T> readerFunction
  ) {
    try {
      final URL targetUrl = new URL(target);

      final HttpURLConnection connection =
        (HttpURLConnection) targetUrl.openConnection();

      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");

      final int responseCode = connection.getResponseCode();
      if (HttpURLConnection.HTTP_OK != responseCode) {
        throw new OpenWeatherCallException("Response code:" + responseCode);
      }

      final Reader reader = new InputStreamReader(
        connection.getInputStream(),
        StandardCharsets.UTF_8
      );

      final T result = readerFunction.apply(reader);

      reader.close();
      connection.disconnect();

      return result;
    } catch (IOException exception) {
      throw new OpenWeatherCallException("");
    }
  }

//  public Coordinates requestGeographyCoordinates() {
//    return this.openConnection(GEOGRAPHY_API
//        + "?q=" + this.settings.getLocation()
//        + "&appid=" + this.getOpenWeatherKey(),
//      reader -> {
//        final Coordinates coordinates = JsonDependencyInstance.GSON.fromJson(
//          reader, COORDINATES_TYPE
//        );
//
//        this.latitude = coordinates.getLatitude();
//        this.longitude = coordinates.getLongitude();
//
//        return coordinates;
//      }
//    );
//  }

//  public CurrentWeather requestWeather() {
//    return this.openConnection(
//      WEATHER_API
//        + "?lat=" + this.latitude
//        + "&lon=" + this.longitude
//        + "&appid=" + this.getOpenWeatherKey(),
//      reader -> JsonDependencyInstance.GSON.fromJson(
//        reader,
//        WEATHER_TYPE
//      )
//    );
//  }

  public CurrentWeather getCurrentWeatherByCoordinates(final Coordinates coordinates) {
    return this.openConnection(
      WEATHER_API
        + "?lat=" + coordinates.getLatitude()
        + "&lon=" + coordinates.getLongitude()
        + "&appid=" + this.getOpenWeatherKey(),
      reader -> JsonDependencyInstance.GSON.fromJson(
        reader,
        WEATHER_TYPE
      )
    );
  }
}
