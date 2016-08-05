package Tamaized.TamModized.registry;

import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.registry.TamModelResourceHandler.TamModelResource;

public class TamRegistryHandler {

	private ArrayList<ITamRegistry> registry;

	public TamRegistryHandler() {
		registry = new ArrayList<ITamRegistry>();
	}

	public void register(ITamRegistry reg) {
		registry.add(reg);
	}
	
	public void preInit(){
		for(ITamRegistry reg : registry){
			reg.preInit();
			if(reg instanceof TamFluidRegistryBase) continue;
			for(ITamModel model : reg.getModelList()) TamModelResourceHandler.instance.register(TamModelResourceHandler.instance.new TamModelResource(model, reg.getModID()));
		}
	}
	
	public void init(){
		for(ITamRegistry reg : registry) reg.init();
	}
	
	public void postInit(){
		for(ITamRegistry reg : registry) reg.postInit();
	}

	@SideOnly(Side.CLIENT)
	public void clientPreInit(){
		for(ITamRegistry reg : registry) reg.clientPreInit();
	}

	@SideOnly(Side.CLIENT)
	public void clientInit(){
		for(ITamRegistry reg : registry) reg.clientInit();
	}

	@SideOnly(Side.CLIENT)
	public void clientPostInit(){
		for(ITamRegistry reg : registry) reg.clientPostInit();
	}

}
