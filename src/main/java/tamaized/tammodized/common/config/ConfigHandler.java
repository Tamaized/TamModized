package tamaized.tammodized.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.tammodized.TamModized;

@Mod.EventBusSubscriber(modid = TamModized.modid)
@Config(modid = TamModized.modid)
public class ConfigHandler {

	@Config.Name("Patreon Particles")
	@Config.Comment("Should Patreon Particles Render?")
	public static boolean patreonClient = true;

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(TamModized.modid)) {
			ConfigManager.sync(TamModized.modid, Config.Type.INSTANCE);
		}
	}

}
