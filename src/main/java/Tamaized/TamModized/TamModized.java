package Tamaized.TamModized;

import Tamaized.TamModized.events.DragonDeathEvent;
import Tamaized.TamModized.proxy.AbstractProxy;
import Tamaized.TamModized.registry.TamModizedParticles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

	@Override
	protected AbstractProxy getProxy() {
		return proxy;
	}

	@Override
	protected String getModID() {
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

		register(particles = new TamModizedParticles());

		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(networkChannelName);

		MinecraftForge.EVENT_BUS.register(new DragonDeathEvent());
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {

	}

}
