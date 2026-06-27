package io.github.doublestar101.xaerodaytweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.doublestar101.xaerodaytweaks.XaeroDayTweaks;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("xaerodaytweaks.json");

    private static final Path BACKUP_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve("xaerodaytweaks.json.bak");

    private static Config config = new Config();

    private ConfigManager() {
    }

    public static Config getConfig() {
        return config;
    }

    public static void load() {
        try {
            if (!Files.exists(CONFIG_PATH)) {
                config = new Config();
                save();
                return;
            }

            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                Config loaded = GSON.fromJson(reader, Config.class);

                if (loaded == null) {
                    throw new IOException("Config file is empty.");
                }

                config = loaded;

                // Clamp values to prevent absurd offsets.
                config.dayOffset = Math.max(-1_000_000_000,
                        Math.min(1_000_000_000, config.dayOffset));
            }

        } catch (Exception e) {

            XaeroDayTweaks.LOGGER.warn("Config reload failed.", e);

            try {
                if (Files.exists(CONFIG_PATH)) {
                    Files.copy(CONFIG_PATH, BACKUP_PATH, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException ignored) {
            }

            config = new Config();
            save();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }

        } catch (IOException e) {
            XaeroDayTweaks.LOGGER.error("Failed to save config.", e);
        }
    }
}