package Tamaized.TamModized;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tamaized.TamModized.proxy.AbstractProxy;

@Mod(modid = TamModized.modid, name = "VoidCraft", version = TamModized.version)
public class TamModized {

	protected final static String version = "0.0.0";
	public static final String modid = "tammodized";

	public static String getVersion() {
		return version;
	}

	@Instance(modid)
	public static TamModized instance = new TamModized();

	public static FMLEventChannel channel;
	public static final String networkChannelName = "TamModized";

	@SidedProxy(clientSide = "Tamaized.TamModized.proxy.ClientProxy", serverSide = "Tamaized.TamModized.proxy.ServerProxy")
	public static AbstractProxy proxy;

	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

	public static Logger logger;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger("TamModized");
		logger.info("Tamaized is now taking over >:)");

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		proxy.preInit();
	}

	@EventHandler
	public void InitVoidCraft(FMLInitializationEvent event) {
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit();
	}

}