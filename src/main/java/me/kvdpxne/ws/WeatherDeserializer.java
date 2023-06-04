package me.kvdpxne.ws;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

final class WeatherDeserializer
  implements JsonDeserializer<Weather> {

  WeatherDeserializer() {
    super();
  }

  @Override
  public Weather deserialize(
    final JsonElement json,
    final Type typeOfT,
    final JsonDeserializationContext context
  ) throws JsonParseException {
    final JsonObject node = json.getAsJsonObject()
      .getAsJsonArray("weather")
      .get(0)
      .getAsJsonObject();
    return new Weather(
      node.getAsJsonPrimitive("id")
        .getAsInt()
    );
  }
}
