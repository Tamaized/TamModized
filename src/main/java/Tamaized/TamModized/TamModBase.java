package Tamaized.TamModized;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Tamaized.TamModized.proxy.AbstractProxy;
import Tamaized.TamModized.registry.ITamRegistry;
import Tamaized.TamModized.registry.TamRegistryHandler;

public abstract class TamModBase {

	public static final int WILDCARD_VALUE = OreDictionary.WILDCARD_VALUE;

	public Logger logger;

	private TamRegistryHandler registryHandler = new TamRegistryHandler();

	private int modEntityID = 0;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	protected abstract AbstractProxy getProxy();

	protected abstract String getModID();

	/**
	 * Override and Super this with @EventHandler
	 */
	public void FMLpreInit(FMLPreInitializationEvent event) {
		logger = LogManager.getLogger(getModID());
		getProxy().preRegisters();
		preInit(event);
		registryHandler.preInit();
		getProxy().preInit();
	}

	/**
	 * Override and Super this with @EventHandler
	 */
	public void FMLinit(FMLInitializationEvent event) {
		init(event);
		registryHandler.init();
		getProxy().init();
	}

	/**
	 * Override and Super this with @EventHandler
	 */
	public void FMLpostInit(FMLPostInitializationEvent event) {
		postInit(event);
		registryHandler.postInit();
		getProxy().postInit();
	}

	protected abstract void preInit(FMLPreInitializationEvent event);

	protected abstract void init(FMLInitializationEvent event);

	protected abstract void postInit(FMLPostInitializationEvent event);

	protected void register(ITamRegistry r) {
		registryHandler.register(r);
	}

	/**
	 * call this in clientProxy
	 */
	@SideOnly(Side.CLIENT)
	public void clientPreInit() {
		registryHandler.clientPreInit();
	}

	/**
	 * call this in clientProxy
	 */
	@SideOnly(Side.CLIENT)
	public void clientInit() {
		registryHandler.clientInit();
	}

	/**
	 * call this in clientProxy
	 */
	@SideOnly(Side.CLIENT)
	public void clientPostInit() {
		registryHandler.clientPostInit();
	}

	protected void registerEntity(Class<? extends Entity> entityClass, String entityName, Object mod, String modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(new ResourceLocation(modid, entityName), entityClass, entityName, modEntityID, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		modEntityID++;
	}

	protected void registerEntityWithEgg(Class<? extends Entity> entityClass, String entityName, Object mod, String modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimaryColor, int eggSecondaryColor) {
		EntityRegistry.registerModEntity(new ResourceLocation(modid, entityName), entityClass, entityName, modEntityID, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimaryColor, eggSecondaryColor);
		modEntityID++;
	}

}
