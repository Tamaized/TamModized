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

	@Config.Name("Instant Portal Teleport")
	@Config.Comment("By default, some portal blocks from mods that depend on this mod have a delay and overlay render. You can toggle that here.")
	public static InstantPortal instantPortal = InstantPortal.NEVER;

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(TamModized.modid)) {
			ConfigManager.sync(TamModized.modid, Config.Type.INSTANCE);
		}
	}

	public enum InstantPortal {
		NEVER, CREATIVE, ALWAYS
	}

}
