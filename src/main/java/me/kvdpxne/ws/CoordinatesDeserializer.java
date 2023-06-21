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
    if (!json.isJsonArray()) {
      throw new UnsupportedOperationException("Deserialization of coordinates"
        + "from an element other than an array is not supported."
      );
    }

    final JsonObject node = json.getAsJsonArray()
      .get(0)
      .getAsJsonObject();

    return new Coordinates(
      node.getAsJsonPrimitive(Coordinates.FIELD_LATITUDE).getAsDouble(),
      node.getAsJsonPrimitive(Coordinates.FIELD_LONGITUDE).getAsDouble()
    );
  }
}
