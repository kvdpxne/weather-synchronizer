package me.kvdpxne.ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple object that stores the coordinates of cities according to the
 * unique identifiers of existing worlds.
 */
public final class WorldWeatherStorage {

  private static final Type COORDINATES_TYPE;

  static {
    COORDINATES_TYPE = TypeToken.get(WorldWeather[].class).getType();
  }

  private final Map<UUID, WorldWeather> coordinates;
  private final Path rootDirectoryPath;

  WorldWeatherStorage(final Path path) {
    this.rootDirectoryPath = path;
    this.coordinates = new HashMap<>(8);
  }

  /**
   * An unmodifiable collection of all currently registered city coordinates by
   * world unique identifiers.
   */
  public Collection<WorldWeather> getCoordinates() {
    return this.coordinates.values();
  }

  /**
   * Path to the file in the plugin's default directory where the coordinates
   * are or will be stored.
   */
  public Path getPath() {
    return this.rootDirectoryPath.resolve("coordinates.json");
  }

  public void addCoordinates(final WorldWeather coordinates) {
    this.coordinates.put(coordinates.getIdentifier(), coordinates);
  }

  public void removeCoordinates(final UUID identifier) {
    this.coordinates.remove(identifier);
  }

  public void removeCoordinates(final WorldWeather coordinates) {
    this.removeCoordinates(coordinates.getIdentifier());
  }

  private void serialize(final Writer writer) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;
    gson.toJson(this.getCoordinates(), writer);
  }

  /**
   * Saves all the coordinates that are stored in the coordinates collection.
   */
  public void saveAll() {
    try (final Writer writer = Files.newBufferedWriter(this.getPath())) {
      this.serialize(writer);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  private void deserialize(final Reader reader) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;

    final WorldWeather[] data = gson.fromJson(
      reader,
      COORDINATES_TYPE
    );

    if (null == data) {
      return;
    }

    for (final WorldWeather dataObject : data) {
      this.coordinates.put(dataObject.getIdentifier(), dataObject);
    }
  }

  /**
   * Loads all the coordinates stored in the coordinates.json file.
   */
  public void loadAll() {
    final Path path = this.getPath();
    if (Files.notExists(path)) {
      this.saveAll();
      return;
    }

    try (final Reader reader = Files.newBufferedReader(this.getPath())) {
      this.deserialize(reader);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public int size() {
    return this.coordinates.size();
  }
}
