package io.github.doublestar101.xaerodaytweaks;

import io.github.doublestar101.xaerodaytweaks.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XaeroDayTweaks implements ModInitializer {

	public static final String MOD_ID = "xaerodaytweaks";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ConfigManager.load();

		LOGGER.info("Xaero Day Tweaks initialized.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}