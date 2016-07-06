package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamBlockFence extends BlockFence implements ITamModel {

	private final String name;

	public TamBlockFence(CreativeTabs tab, Material materialIn, MapColor mapColor, String n) {
		super(materialIn, mapColor);
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
