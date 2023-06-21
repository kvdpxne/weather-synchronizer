package me.kvdpxne.ws;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

final class WorldWeatherSerializer
  implements JsonSerializer<WorldWeather> {

  WorldWeatherSerializer() {
    super();
  }

  @Override
  public JsonElement serialize(
    final WorldWeather src,
    final Type typeOfSrc,
    final JsonSerializationContext context
  ) {
    final JsonObject node = new JsonObject();

    final JsonElement latitude = context.serialize(src.getLatitude());
    final JsonElement longitude = context.serialize(src.getLongitude());

    node.addProperty("identifier", src.getIdentifier().toString());
    node.add(Coordinates.FIELD_LATITUDE, latitude);
    node.add(Coordinates.FIELD_LONGITUDE, longitude);

    return node;
  }
}
