package Tamaized.TamModized.registry;

import java.util.ArrayList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITamRegistry {
	
	public void preInit();
	
	public void init();
	
	public void postInit();

	@SideOnly(Side.CLIENT)
	public void clientPreInit();

	@SideOnly(Side.CLIENT)
	public void clientInit();

	@SideOnly(Side.CLIENT)
	public void clientPostInit();
	
	public ArrayList<ITamModel> getModelList();
	
	public String getModID();

}
