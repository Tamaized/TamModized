package tamaized.tammodized.common.blocks.slab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import tamaized.tammodized.registry.ITamRegistry;

import java.util.Random;

@SuppressWarnings("unused")
public abstract class TamBlockSlab extends BlockSlab implements ITamRegistry {

	private static final PropertyEnum<TamBlockSlab.Variant> VARIANT = PropertyEnum.create("variant", TamBlockSlab.Variant.class);
	private static final int HALF_META_BIT = 8;

	public TamBlockSlab(CreativeTabs tab, Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		ModContainer container = Loader.instance().activeModContainer();
		setTranslationKey(container == null ? name : (container.getModId().toLowerCase() + "." + name));
		IBlockState blockState = this.blockState.getBaseState();
		if (!isDouble()) {
			blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
			setCreativeTab(tab);
		}
		setDefaultState(blockState);
		useNeighborBrightness = !isDouble();
	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {

	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerModel(ModelRegistryEvent e) {
		if(getRegistryName() != null)
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ResourceLocation(getRegistryName().getNamespace(), getModelDir() + "/" + getRegistryName().getPath()), "inventory"));
	}

	public String getModelDir() {
		return "blocks";
	}

	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}

	@Override
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
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
	@SuppressWarnings("deprecation")
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
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
	}

	public enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}
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
}
