package Tamaized.TamModized.config;

import java.io.File;
import java.io.IOException;

import Tamaized.TamModized.TamModBase;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler extends AbstractConfigHandler {

	public ConfigHandler(TamModBase instance, File file, Configuration c) {
		super(instance, file, c);
	}

	protected void loadData(boolean firstLoad) {
		
	}

	protected void cleanup() throws IOException {
		
	}

}
