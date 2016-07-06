package Tamaized.TamModized.proxy;

import Tamaized.TamModized.registry.TamRegistryHandler;

public abstract class AbstractProxy {

	public void preInit(){
		TamRegistryHandler.instance.preInit();
	}

	public void init(){
		TamRegistryHandler.instance.init();
	}

	public void postInit(){
		TamRegistryHandler.instance.postInit();
	}

}
