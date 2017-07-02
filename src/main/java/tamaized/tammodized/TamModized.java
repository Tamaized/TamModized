package tamaized.tammodized;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tamaized.tammodized.registry.PortalHandlerRegistry;
import tamaized.tammodized.common.capabilities.dimTracker.DimensionCapabilityHandler;
import tamaized.tammodized.common.capabilities.dimTracker.DimensionCapabilityStorage;
import tamaized.tammodized.common.capabilities.dimTracker.IDimensionCapability;
import tamaized.tammodized.common.entity.EntityDragonOld;
import tamaized.tammodized.common.handler.ContributorHandler;
import tamaized.tammodized.proxy.AbstractProxy;
import tamaized.tammodized.registry.TamModizedParticles;

@Mod(modid = TamModized.modid, name = "TamModized", version = TamModized.version)
public class TamModized extends TamModBase {

	public final static String version = "${version}";
	public static final String modid = "tammodized";
	public static final String networkChannelName = "TamModized";
	@SidedProxy(clientSide = "tamaized.tammodized.proxy.ClientProxy", serverSide = "tamaized.tammodized.proxy.ServerProxy")
	public static AbstractProxy proxy;
	@Instance(modid)
	public static TamModized instance = new TamModized();

	public static FMLEventChannel channel;
	public static TamModizedParticles particles;

	public static String getVersion() {
		return version;
	}

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

		ContributorHandler.start();

		TamModizedParticles.register();

		registerEntity(EntityDragonOld.class, "DragonOld", this, modid, 64, 1, true);

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		MinecraftForge.EVENT_BUS.register(new PortalHandlerRegistry());

		CapabilityManager.INSTANCE.register(IDimensionCapability.class, new DimensionCapabilityStorage(), DimensionCapabilityHandler.class);
		MinecraftForge.EVENT_BUS.register(new tamaized.tammodized.common.capabilities.EventHandler());
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {

	}

}
