package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamModelResourceHandler;

public class ClientProxy extends AbstractProxy {

	@Override
	public void preInit() {

	}

	@Override
	public void init() {
		TamModelResourceHandler.instance.compile();
	}

	@Override
	public void postInit() {

	}

}
