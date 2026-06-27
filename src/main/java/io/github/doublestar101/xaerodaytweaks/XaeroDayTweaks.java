package io.github.doublestar101.xaerodaytweaks;

import io.github.doublestar101.xaerodaytweaks.config.ConfigWatcher;
import io.github.doublestar101.xaerodaytweaks.config.ConfigManager;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XaeroDayTweaks implements ModInitializer {

	public static final String MOD_ID = "xaerodaytweaks";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ConfigManager.load();
		ConfigWatcher.start();

		String version = FabricLoader.getInstance()
				.getModContainer(MOD_ID)
				.orElseThrow()
				.getMetadata()
				.getVersion()
				.getFriendlyString();

		LOGGER.info("Xaero Day Tweaks v{} initialized", version);
		LOGGER.info("Enabled    : {}", ConfigManager.getConfig().enabled);
		LOGGER.info("Day Offset : {}", ConfigManager.getConfig().dayOffset);
		LOGGER.info("Live configuration reloading enabled.");
	}
}