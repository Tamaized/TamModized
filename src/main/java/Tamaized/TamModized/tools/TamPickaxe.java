package Tamaized.TamModized.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamPickaxe extends ItemPickaxe implements ITamModel {

	private final String name;

	public TamPickaxe(CreativeTabs tab, ToolMaterial material, String n) {
		super(material);
		name = n;
		setUnlocalizedName(name);
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + n));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "tools";
	}

	@Override
	public Item getAsItem() {
		return this;
	}

}
