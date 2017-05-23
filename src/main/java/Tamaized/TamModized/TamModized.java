package Tamaized.TamModized;

import java.io.File;

import Tamaized.TamModized.capabilities.dimTracker.DimensionCapabilityHandler;
import Tamaized.TamModized.capabilities.dimTracker.DimensionCapabilityStorage;
import Tamaized.TamModized.capabilities.dimTracker.IDimensionCapability;
import Tamaized.TamModized.config.ConfigHandler;
import Tamaized.TamModized.entity.dragon.EntityDragonOld;
import Tamaized.TamModized.handler.ContributorHandler;
import Tamaized.TamModized.proxy.AbstractProxy;
import Tamaized.TamModized.registry.PortalHandlerRegistry;
import Tamaized.TamModized.registry.TamModizedParticles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = TamModized.modid, name = "TamModized", guiFactory = "Tamaized.TamModized.config.GUIConfigFactory", version = TamModized.version)
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

	public static ConfigHandler config;

	public static FMLEventChannel channel;
	public static final String networkChannelName = "TamModized";

	public static TamModizedParticles particles;

	@Override
	protected AbstractProxy getProxy() {
		return proxy;
	}

	@Override
	public String getModID() {
		return modid;
	}

	@Override
	@EventHandler
	public void FMLpreInit(FMLPreInitializationEvent event) {
		super.FMLpreInit(event);
	}

	@Override
	@EventHandler
	public void FMLinit(FMLInitializationEvent event) {
		super.FMLinit(event);
	}

	@Override
	@EventHandler
	public void FMLpostInit(FMLPostInitializationEvent event) {
		super.FMLpostInit(event);
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		logger.info("Tamaized is now taking over >:)");

		File file = event.getSuggestedConfigurationFile();
		config = new ConfigHandler(this, file, new Configuration(file));

		ContributorHandler.start();

		register(particles = new TamModizedParticles());

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		MinecraftForge.EVENT_BUS.register(new PortalHandlerRegistry());

		CapabilityManager.INSTANCE.register(IDimensionCapability.class, new DimensionCapabilityStorage(), DimensionCapabilityHandler.class);
		MinecraftForge.EVENT_BUS.register(new Tamaized.TamModized.capabilities.EventHandler());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		registerEntity(EntityDragonOld.class, "DragonOld", this, modid, 64, 1, true);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {

	}

}
