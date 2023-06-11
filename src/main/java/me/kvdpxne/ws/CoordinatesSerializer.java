package me.kvdpxne.ws;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

final class CoordinatesSerializer
  implements JsonSerializer<Coordinates> {

  @Override
  public JsonElement serialize(
    final Coordinates src,
    final Type typeOfSrc,
    final JsonSerializationContext context
  ) {
    final JsonObject node = new JsonObject();

    node.add("lat", context.serialize(src.getLatitude()));
    node.add("lot", context.serialize(src.getLongitude()));

    return node;
  }
}
