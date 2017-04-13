package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public abstract class TamBlockContainer extends BlockContainer implements ITamModel {

	private final String name;

	public TamBlockContainer(CreativeTabs tab, Material material, String n, float hardness, SoundType sound) {
		super(material);
		name = n;
		setUnlocalizedName(name);
		setHardness(hardness);
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + getName()));
		GameRegistry.register(new ItemBlock(this).setRegistryName(getModelDir() + "/" + getName()));
		setCreativeTab(tab);
		setSoundType(sound);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public String getModelDir() {
		return "blocks";
	}

	@Override
	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);

}
