package Tamaized.TamModized.registry;

import java.util.ArrayList;

public class TamRegistryHandler {

	private ArrayList<ITamRegistry> registry;

	public static final TamRegistryHandler instance = new TamRegistryHandler();

	private TamRegistryHandler() {
		registry = new ArrayList<ITamRegistry>();
	}

	public void register(ITamRegistry reg) {
		registry.add(reg);
	}
	
	public void preInit(){
		for(ITamRegistry reg : registry) reg.preInit();
	}
	
	public void init(){
		for(ITamRegistry reg : registry) reg.init();
	}
	
	public void postInit(){
		for(ITamRegistry reg : registry) reg.postInit();
	}
	
	public void clientPreInit(){
		//for(ITamRegistry reg : registry) reg.preInit();
	}
	
	public void clientInit(){
		for(ITamRegistry reg : registry) reg.setupRender();
	}
	
	public void clientPostInit(){
		//for(ITamRegistry reg : registry) reg.postInit();
	}

}
