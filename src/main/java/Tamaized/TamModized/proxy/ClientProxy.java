package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamModelResourceHandler;
import Tamaized.TamModized.registry.TamRegistryHandler;

public class ClientProxy extends AbstractProxy {

	@Override
	public void preInit() {
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
		TamModelResourceHandler.instance.compile();
	}

	@Override
	public void postInit() {
		super.postInit();
	}

}
