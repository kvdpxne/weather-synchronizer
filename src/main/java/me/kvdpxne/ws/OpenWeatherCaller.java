package me.kvdpxne.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

final class OpenWeatherCaller {

  private static final String GEOGRAPHY_API =
    "http://api.openweathermap.org/geo/1.0/direct";

  private static final String WEATHER_API =
    "https://api.openweathermap.org/data/2.5/weather";

  private static final Gson GSON = new GsonBuilder()
    .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
    .registerTypeAdapter(Weather.class, new WeatherDeserializer())
    .create();

  private static final TypeToken<Coordinates> COORDINATES_TOKEN =
    TypeToken.get(Coordinates.class);

  private static final TypeToken<Weather> WEATHER_TOKEN =
    TypeToken.get(Weather.class);

  private float latitude;

  private float longitude;

  OpenWeatherCaller() {
    super();
  }

  public Coordinates requestGeographyCoordinates() throws IOException {
    final URL target = new URL(
      GEOGRAPHY_API
        + "?q=" + "Lublin" + ',' + "PL"
        + "&appid=" + "64d67f5919f7491f642edd5e6fbf1054"
    );

    final HttpURLConnection connection = (HttpURLConnection) target.openConnection();
    connection.setRequestMethod("GET");

    final int responseCode = connection.getResponseCode();
    if (200 != responseCode) {
      throw new OpenWeatherCallException("Response code:" + responseCode);
    }

    final Reader reader = new InputStreamReader(
      connection.getInputStream(),
      StandardCharsets.UTF_8
    );

    final Coordinates coordinates = GSON.fromJson(
      GSON.newJsonReader(reader),
      COORDINATES_TOKEN
    );

    reader.close();
    connection.disconnect();

    this.latitude = coordinates.getLatitude();
    this.longitude = coordinates.getLongitude();

    return coordinates;
  }

  public Weather requestWeather() throws IOException {
    final URL target = new URL(
      WEATHER_API
        + "?lat=" + this.latitude
        + "&lon=" + this.longitude
        + "&appid=" + "64d67f5919f7491f642edd5e6fbf1054"
    );

    final HttpURLConnection connection = (HttpURLConnection) target.openConnection();
    connection.setRequestMethod("GET");

    final int responseCode = connection.getResponseCode();
    if (200 != responseCode) {
      throw new OpenWeatherCallException("Response code:" + responseCode);
    }

    final Reader reader = new InputStreamReader(
      connection.getInputStream(),
      StandardCharsets.UTF_8
    );

    final Weather weather = GSON.fromJson(
      GSON.newJsonReader(reader),
      WEATHER_TOKEN
    );

    reader.close();
    connection.disconnect();

    return weather;
  }
}
