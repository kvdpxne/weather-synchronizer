package me.kvdpxne.ws;

import com.google.gson.Gson;
import me.kvdpxne.cricket.Cricket;
import me.kvdpxne.cricket.CricketFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
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

  private static final Cricket logger;

  static {
    logger = CricketFactory.of(WorldWeatherStorage.class);
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
    logger.debug("Added coordinates: {0}.", coordinates);
  }

  public void removeCoordinates(final UUID identifier) {
    this.coordinates.remove(identifier);
    logger.debug("Removed coordinates with identifier: {0}.", identifier);
  }

  public void removeCoordinates(final WorldWeather coordinates) {
    this.coordinates.remove(coordinates.getIdentifier());
    logger.debug("Removed coordinates: {0}.", coordinates);
  }

  private void serialize(final Writer writer) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;
    gson.toJson(this.getCoordinates(), writer);
  }

  /**
   * Saves all the coordinates that are stored in the coordinates collection.
   */
  public void saveAll() {
    logger.debug("Preparing to save all registered coordinates.");

    try (final Writer writer = Files.newBufferedWriter(this.getPath())) {
      this.serialize(writer);
      logger.info("All registered coordinates have been saved.");
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  private void deserialize(final Reader reader) throws IOException {
    final Gson gson = JsonDependencyInstance.GSON;

    final WorldWeather[] data = gson.fromJson(reader, WorldWeather[].class);

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
    logger.debug("Preparing to load all stored coordinates.");

    final Path path = this.getPath();
    if (Files.notExists(path)) {
      this.saveAll();
      return;
    }

    try (final Reader reader = Files.newBufferedReader(this.getPath())) {
      this.deserialize(reader);
      logger.info("All stored coordinates have been loaded.");
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
  }

  public int size() {
    return this.coordinates.size();
  }
}
