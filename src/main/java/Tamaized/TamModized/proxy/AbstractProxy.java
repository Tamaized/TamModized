package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamRegistryHandler;

public abstract class AbstractProxy {

	public abstract void preRegisters();

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

}
