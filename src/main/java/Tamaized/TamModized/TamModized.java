package Tamaized.TamModized;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tamaized.TamModized.proxy.AbstractProxy;
import Tamaized.TamModized.registry.TamModizedParticles;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = TamModized.modid, name = "TamModized", version = TamModized.version)
public class TamModized extends TamModBase {

	public final static String version = "${version}";
	public static final String modid = "tammodized";

	@SidedProxy(clientSide = "Tamaized.TamModized.proxy.ClientProxy", serverSide = "Tamaized.TamModized.proxy.ServerProxy")
	public static AbstractProxy proxy;

	@Instance(modid)
	public static TamModized instance = new TamModized();

	public static String getVersion() {
		return version;
	}

	public static FMLEventChannel channel;
	public static final String networkChannelName = "TamModized";

	public static TamModizedParticles particles;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger("TamModized");
		logger.info("Tamaized is now taking over >:)");

		register(particles = new TamModizedParticles());
		
		super.preInit(event);

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		proxy.preInit();
	}

	@EventHandler
	public void InitVoidCraft(FMLInitializationEvent event) {
		super.init(event);
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		proxy.postInit();
	}

}
