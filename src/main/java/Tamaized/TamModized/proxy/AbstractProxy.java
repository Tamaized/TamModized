package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamRegistryHandler;

public abstract class AbstractProxy {

	protected static enum Side {
		CLIENT, SERVER
	}

	private final Side side;

	public AbstractProxy(Side side) {
		this.side = side;
	}

	public abstract void preRegisters();

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

	public final void registryHandlerPreInit(TamRegistryHandler handler) {
		if (side == Side.CLIENT) handler.clientPreInit();
	}

	public final void registryHandlerInit(TamRegistryHandler handler) {
		if (side == Side.CLIENT) handler.clientInit();
	}

	public final void registryHandlerPostInit(TamRegistryHandler handler) {
		if (side == Side.CLIENT) handler.clientPostInit();
	}

}
