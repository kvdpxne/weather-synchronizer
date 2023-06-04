package me.kvdpxne.ws;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

final class CoordinatesDeserializer
  implements JsonDeserializer<Coordinates> {

  CoordinatesDeserializer() {
    super();
  }

  @Override
  public Coordinates deserialize(
    final JsonElement json,
    final Type typeOfT,
    final JsonDeserializationContext context
  ) throws JsonParseException {
    final JsonObject node = json.getAsJsonArray()
      .get(0)
      .getAsJsonObject();

    final float lat = node.getAsJsonPrimitive("lat").getAsFloat();
    final float lon = node.getAsJsonPrimitive("lon").getAsFloat();

    return new Coordinates(
      lat,
      lon
    );
  }
}
