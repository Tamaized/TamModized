package Tamaized.TamModized.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamAxe extends ItemAxe implements ITamModel {

	private final String name;

	public TamAxe(CreativeTabs tab, ToolMaterial material, String n) {
		super(material, material.getDamageVsEntity(), -3.0f);
		name = n;
		setUnlocalizedName(name);
		GameRegistry.register(this.setRegistryName("tools/" + n));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

}
