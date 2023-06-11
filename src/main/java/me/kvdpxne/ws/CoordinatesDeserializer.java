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
    final JsonObject node = json.isJsonArray()
      ? json.getAsJsonArray().get(0).getAsJsonObject()
      : json.isJsonObject()
      ? json.getAsJsonObject()
      : null;

    if (null == node) {
      // TODO add exception detail message
      throw new UnsupportedOperationException();
    }

    final double lat = node.getAsJsonPrimitive("lat").getAsDouble();
    final double lon = node.getAsJsonPrimitive("lon").getAsDouble();

    return new Coordinates(
      lat,
      lon
    );
  }
}
