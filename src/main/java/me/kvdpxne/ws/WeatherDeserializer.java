package me.kvdpxne.ws;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

final class WeatherDeserializer
  implements JsonDeserializer<CurrentWeather> {

  WeatherDeserializer() {
    super();
  }

  @Override
  public CurrentWeather deserialize(
    final JsonElement json,
    final Type typeOfT,
    final JsonDeserializationContext context
  ) throws JsonParseException {
    final JsonObject node = json.getAsJsonObject()
      .getAsJsonArray("weather")
      .get(0)
      .getAsJsonObject();
    return new CurrentWeather(
      node.getAsJsonPrimitive("id")
        .getAsInt()
    );
  }
}
