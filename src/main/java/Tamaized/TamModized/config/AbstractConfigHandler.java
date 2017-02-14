package Tamaized.TamModized.config;

import java.io.File;
import java.io.IOException;

import Tamaized.TamModized.TamModBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class AbstractConfigHandler {

	private final File configFile;
	protected Configuration config;
	private final TamModBase mod;

	public AbstractConfigHandler(TamModBase instance, File file, Configuration c) {
		mod = instance;
		configFile = file;
		config = c;
		config.load();
		sync(true);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public final Configuration getConfig() {
		return config;
	}

	public final void sync(boolean firstLoad) {
		try {
			loadData(firstLoad);
			cleanupFile();
			config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected abstract void loadData(boolean firstLoad);

	private void cleanupFile() throws IOException {
		configFile.delete();
		configFile.createNewFile();
		config = new Configuration(configFile);
		cleanup();
	}

	protected abstract void cleanup() throws IOException;

	@SubscribeEvent
	public void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(mod.getModID())) sync(false);
	}

}
