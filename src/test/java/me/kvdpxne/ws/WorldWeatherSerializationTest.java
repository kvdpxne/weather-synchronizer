package me.kvdpxne.ws;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class WorldWeatherSerializationTest {

  final double[] coordinates = {
    51.1089776D, 17.0326689D,
    52.7309926D, 15.2400451D,
    50.678792900000005D, 17.929884436033525D,
    50.0374531D, 22.0047174D,
    53.7767239D, 20.477780523409734D,
    50.0619474D, 19.9368564D,
    51.7687323D, 19.4569911D,
    52.4006632D, 16.91973259178088D,
    53.127505049999996D, 23.147050870161664D,
    51.9383777D, 15.5050408D,
    52.2319581D, 21.0067249D,
    53.1219648D, 18.0002529D,
    51.250559D, 22.5701022D,
    50.85403585D, 20.609914352101452D,
    53.4301818D, 14.5509623D,
    54.42880315D, 18.798325902846855D,
    50.2598987D, 19.0215852D,
    53.0102721D, 18.6048094D
  };

  int getCapacity() {
    return this.coordinates.length / 2;
  }

  WorldWeatherStorage getStorage() {
    final Path path = Paths.get("./run");
    final WorldWeatherStorage storage = new WorldWeatherStorage(path);

    try {
      if (Files.notExists(path)) {
        Files.createDirectory(path);
      }
    } catch (final IOException ignored) {
      // Ignore.
    }

    return storage;
  }

  @Test
  @DisplayName("Serialize to Stored File")
  @Order(1)
  void serializeToStoredFile() {
    final WorldWeatherStorage storage = this.getStorage();

    for (int i = 0; i < this.getCapacity(); i++) {
      storage.addCoordinates(new WorldWeather(
        UUID.randomUUID(),
        this.coordinates[i],
        this.coordinates[i + 1]
      ));
    }

    storage.saveAll();
  }

  @Test
  @DisplayName("Deserialize from Stored File")
  @Order(2)
  void deserializeFromStoredFile() {
    final WorldWeatherStorage storage = this.getStorage();

    storage.loadAll();
    storage.getCoordinates().forEach(System.out::println);

    Assertions.assertEquals(this.getCapacity(), storage.size());
  }
}
