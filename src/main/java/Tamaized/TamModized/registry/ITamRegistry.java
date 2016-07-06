package Tamaized.TamModized.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITamRegistry {
	
	public void preInit();
	
	public void init();
	
	public void postInit();
	
	@SideOnly(Side.CLIENT)
	public void setupRender();

}
