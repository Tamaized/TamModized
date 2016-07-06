package Tamaized.TamModized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public abstract class TamBlock extends Block implements ITamModel {

	private final String name;

	public TamBlock(CreativeTabs tab, Material material, String n) {
		super(material);
		name = n;
		setUnlocalizedName(name);
		GameRegistry.register(this.setRegistryName("blocks/" + n));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

}
