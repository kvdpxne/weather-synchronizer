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
import java.util.HashSet;
import java.util.Set;

public final class CoordinatesStorage {

  private static final Type COORDINATES_COLLECTION_TYPE;

  static {
    COORDINATES_COLLECTION_TYPE = new TypeToken<Set<Coordinates>>() {

    }.getType();
  }

  private final Set<Coordinates> coordinates;
  private final Path rootDirectoryPath;

  CoordinatesStorage(final Path path) {
    this.rootDirectoryPath = path;
    this.coordinates = new HashSet<>(8);
  }

  public Set<Coordinates> getCoordinates() {
    return this.coordinates;
  }

  /**
   * Path to the file in the plugin's default directory where the coordinates
   * are or will be stored.
   */
  public Path getPath() {
    return this.rootDirectoryPath.resolve("coordinates.json");
  }

  private void deserialize(final Reader reader) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;

    final Collection<? extends Coordinates> collection = gson.fromJson(
      reader,
      COORDINATES_COLLECTION_TYPE
    );

    if (null != collection) {
      this.coordinates.addAll(collection);
    }
  }

  /**
   * Loads all the coordinates stored in the coordinates.json file.
   */
  public void loadAll() {
    // TODO catch file not exists
    try (final Reader reader = Files.newBufferedReader(this.getPath())) {
      this.deserialize(reader);
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  private void serialize(final Writer writer) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;
    gson.toJson(this.coordinates, COORDINATES_COLLECTION_TYPE, writer);
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
}
