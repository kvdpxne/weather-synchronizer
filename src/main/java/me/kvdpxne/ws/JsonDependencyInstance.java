package me.kvdpxne.ws;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

final class JsonDependencyInstance {

  static final Gson GSON = new GsonBuilder()
    .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
    .registerTypeAdapter(WorldWeather.class, new WorldWeatherSerializer())
    .registerTypeAdapter(CurrentWeather.class, new CurrentWeatherDeserializer())
    .create();

  public static Gson getGson() {
    return GSON;
  }
}
