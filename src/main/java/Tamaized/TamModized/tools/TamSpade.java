package Tamaized.TamModized.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamSpade extends ItemSpade implements ITamModel {

	private final String name;

	public TamSpade(CreativeTabs tab, ToolMaterial material, String n) {
		super(material);
		name = n;
		GameRegistry.register(this.setRegistryName("tools/" + n));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

}
