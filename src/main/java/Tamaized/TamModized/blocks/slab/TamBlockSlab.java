package Tamaized.TamModized.blocks.slab;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import Tamaized.TamModized.registry.ITamModel;

public abstract class TamBlockSlab extends BlockSlab implements ITamModel {

	private final String name;
	private static final PropertyEnum<TamBlockSlab.Variant> VARIANT = PropertyEnum.<TamBlockSlab.Variant> create("variant", TamBlockSlab.Variant.class);
	private static final int HALF_META_BIT = 8;

	public TamBlockSlab(CreativeTabs tab, Material materialIn, String n) {
		super(materialIn);
		name = n;
		setUnlocalizedName(name);
		IBlockState blockState = this.blockState.getBaseState();
		if (!isDouble()) {
			blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
			setCreativeTab(tab);
		}
		setDefaultState(blockState);
		useNeighborBrightness = !isDouble();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName();
	}

	@Override
	public abstract boolean isDouble();

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return TamBlockSlab.Variant.DEFAULT;
	}

	@Override
	public final IBlockState getStateFromMeta(final int meta) {
		IBlockState blockState = this.getDefaultState();
		blockState = blockState.withProperty(VARIANT, TamBlockSlab.Variant.DEFAULT);
		if (!isDouble()) {
			blockState = blockState.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}
		return blockState;
	}

	@Override
	public final int getMetaFromState(final IBlockState state) {
		int i = 0;

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	@Override
	public final int damageDropped(final IBlockState state) {
		return 0;
	}

	@Override
	public abstract Item getItemDropped(IBlockState state, Random rand, int fortune);

	@Override
	protected final BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] { VARIANT }) : new BlockStateContainer(this, new IProperty[] { HALF, VARIANT });
	}

	public static abstract class Double extends TamBlockSlab {
		public Double(CreativeTabs tab, Material materialIn, String n) {
			super(tab, materialIn, n);
		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static abstract class Half extends TamBlockSlab {
		public Half(CreativeTabs tab, Material materialIn, String n) {
			super(tab, materialIn, n);
		}

		@Override
		public boolean isDouble() {
			return false;
		}
	}

	public static enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}
	}
}