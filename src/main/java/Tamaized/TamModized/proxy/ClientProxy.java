package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamModelResourceHandler;
import Tamaized.TamModized.registry.TamRegistryHandler;

public class ClientProxy extends AbstractProxy {

	@Override
	public void preInit() {
		TamRegistryHandler.instance.clientPreInit();
	}

	@Override
	public void init() {
		TamRegistryHandler.instance.clientInit();
		TamModelResourceHandler.instance.compile();
	}

	@Override
	public void postInit() {
		TamRegistryHandler.instance.clientPostInit();
	}

}
