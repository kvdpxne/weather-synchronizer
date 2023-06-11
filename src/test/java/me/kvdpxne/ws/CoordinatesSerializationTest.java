package me.kvdpxne.ws;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

final class CoordinatesSerializationTest {

  @Test
  @DisplayName("Serialize to Stored File")
  void serializeToStoredFile() throws IOException {
    final Path path = Paths.get("./run");
    final CoordinatesStorage storage = new CoordinatesStorage(path);

    if (Files.notExists(path)) {
      Files.createDirectory(path);
    }

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

    final Set<Coordinates> savedCoordinates = storage.getCoordinates();
    for (int i = 0; i < coordinates.length / 2; i++) {
      savedCoordinates.add(new Coordinates(
        coordinates[i],
        coordinates[i + 1]
      ));
    }

    storage.saveAll();
  }
}
