package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamBlockStairs extends BlockStairs implements ITamModel {

	private final String name;

	public TamBlockStairs(CreativeTabs tab, IBlockState modelState, String n) {
		super(modelState);
		name = n;
		setUnlocalizedName(name);
		this.useNeighborBrightness = true;
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + n));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "blocks";
	}

	@Override
	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}

}
