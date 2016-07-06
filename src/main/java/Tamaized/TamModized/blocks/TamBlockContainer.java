package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public abstract class TamBlockContainer extends BlockContainer implements ITamModel {

	private final String name;

	protected TamBlockContainer(CreativeTabs tab, Material material, String n) {
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

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
