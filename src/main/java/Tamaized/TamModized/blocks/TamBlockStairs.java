package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamBlockStairs extends BlockStairs implements ITamModel {

	private final String name;

	protected TamBlockStairs(CreativeTabs tab, IBlockState modelState, String n) {
		super(modelState);
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
