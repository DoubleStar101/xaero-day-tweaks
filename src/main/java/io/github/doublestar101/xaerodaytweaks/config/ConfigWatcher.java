package io.github.doublestar101.xaerodaytweaks.config;

import io.github.doublestar101.xaerodaytweaks.XaeroDayTweaks;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.*;

public final class ConfigWatcher {

    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final String CONFIG_FILE = "xaerodaytweaks.json";

    private static final long DEBOUNCE_MS = 250;
    private static long lastReload = 0;

    private ConfigWatcher() {}

    public static void start() {
        Thread thread = new Thread(ConfigWatcher::watch, "XaeroDayTweaks ConfigWatcher");
        thread.setDaemon(true);
        thread.start();
    }

    private static void watch() {

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

            CONFIG_DIR.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE
            );

            XaeroDayTweaks.LOGGER.info("Started config watcher.");

            while (true) {

                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {

                    Path changed = (Path) event.context();

                    if (!changed.toString().equals(CONFIG_FILE))
                        continue;

                    long now = System.currentTimeMillis();

                    if (now - lastReload < DEBOUNCE_MS)
                        continue;

                    lastReload = now;

                    // Give the editor time to finish writing.
                    Thread.sleep(100);

                    ConfigManager.load();

                    XaeroDayTweaks.LOGGER.info("Configuration reloaded successfully.");
                    XaeroDayTweaks.LOGGER.info("Enabled    : {}", ConfigManager.getConfig().enabled);
                    XaeroDayTweaks.LOGGER.info("Day Offset : {}", ConfigManager.getConfig().dayOffset);
                }

                key.reset();
            }

        } catch (InterruptedException ignored) {

        } catch (IOException e) {

            XaeroDayTweaks.LOGGER.error("Config watcher failed.", e);

        }
    }
}