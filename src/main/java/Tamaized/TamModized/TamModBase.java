package Tamaized.TamModized;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Logger;

import Tamaized.TamModized.registry.ITamRegistry;
import Tamaized.TamModized.registry.TamRegistryHandler;

public abstract class TamModBase {

	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

	public static Logger logger;

	private TamRegistryHandler registryHandler = new TamRegistryHandler();

	static {
		FluidRegistry.enableUniversalBucket();
	}

	/**
	 * super this first if possible
	 */
	public void preInit(FMLPreInitializationEvent event) {
		registryHandler.preInit();
	}

	/**
	 * super this first if possible
	 */
	public void init(FMLInitializationEvent event) {
		registryHandler.init();
	}

	/**
	 * super this first if possible
	 */
	public void postInit(FMLPostInitializationEvent e) {
		registryHandler.postInit();
	}

	protected void register(ITamRegistry r) {
		registryHandler.register(r);
	}

	/**
	 * call this in clientProxy
	 */
	public void clientPreInit() {
		registryHandler.clientPreInit();
	}

	/**
	 * call this in clientProxy
	 */
	public void clientInit() {
		registryHandler.clientInit();
	}

	/**
	 * call this in clientProxy
	 */
	public void clientPostInit() {
		registryHandler.clientPostInit();
	}

}
