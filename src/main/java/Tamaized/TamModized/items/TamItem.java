package Tamaized.TamModized.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamItem extends Item implements ITamModel {

	private final String name;

	public TamItem(CreativeTabs tab, String n) {
		super();
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
		return "items";
	}

	@Override
	public Item getAsItem() {
		return this;
	}
}
