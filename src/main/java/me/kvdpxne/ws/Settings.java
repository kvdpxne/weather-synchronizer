package me.kvdpxne.ws;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public final class Settings {

  private String location;

  private boolean hasExported;

  Settings() {
    this.location = "Warsaw,PL";
    this.hasExported = false;
  }

  private Path getPath() {
    return Paths.get("./weather-synchronizer/settings.yml");
  }

  private void deserialize(final Map<String, ?> data) {
    this.location = (String) data.get("location");
  }

  /**
   * Exports the configuration file from inside the jar archive to the
   * plugin directory if the configuration file in the plugin directory
   * does not exist.
   */
  void export() {
    final Path destination = this.getPath();
    if (Files.exists(destination)) {
      return;
    }

    final Path parent = Paths.get("./weather-synchronizer");

    try (final InputStream input = this.getClass().getResourceAsStream("/settings.yml")) {
      if (null == input) {
        throw new RuntimeException("");
      }

      Files.createDirectory(parent);
      Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);

      this.hasExported = true;
    } catch (final IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  void load() {
    // If the configuration file has been exported, reading values from it
    // does not make sense because they have not yet been modified.
    if (this.hasExported) {
      return;
    }

    try (final InputStream input = Files.newInputStream(this.getPath())) {
      final Yaml yaml = new Yaml();
      final Map<String, ?> data = yaml.load(input);

      this.deserialize(data);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getLocation() {
    return this.location;
  }
}
