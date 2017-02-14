package Tamaized.TamModized.config;

import java.io.File;
import java.io.IOException;

import Tamaized.TamModized.TamModBase;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler extends AbstractConfigHandler {

	private final boolean default_dragonEgg = true;

	private boolean dragonEgg = default_dragonEgg;

	public ConfigHandler(TamModBase instance, File file, Configuration c) {
		super(instance, file, c);
	}

	protected void loadData(boolean firstLoad) {
		dragonEgg = config.get(Configuration.CATEGORY_GENERAL, "Dragon Drops Egg Beyond First Death", default_dragonEgg).getBoolean();
	}

	protected void cleanup() throws IOException {
		config.get(Configuration.CATEGORY_GENERAL, "Dragon Drops Egg Beyond First Death", default_dragonEgg).set(dragonEgg);
	}

	public boolean getDragonEggConfig() {
		return dragonEgg;
	}

}
