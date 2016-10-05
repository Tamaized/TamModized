package Tamaized.TamModized.blocks;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import Tamaized.TamModized.registry.ITamModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TamBlockCrops extends BlockBush implements ITamModel, IGrowable {

	private final String name;

	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

	public TamBlockCrops(CreativeTabs tab, Material material, String n, float hardness) {
		super();
		name = n;
		setUnlocalizedName(name);
		setHardness(hardness);
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + getName()));
		GameRegistry.register(new ItemBlock(this).setRegistryName(getModelDir() + "/" + getName()));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "crops";
	}

	@Override
	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBounds()[((Integer) state.getValue(this.getAgeProperty())).intValue()];
	}

	protected abstract AxisAlignedBB[] getBounds();

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return super.canPlaceBlockAt(worldIn, pos) && canSustainBush(soil) && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
	}

	/**
	 * Return true if the block can sustain a Bush
	 */
	@Override
	protected boolean canSustainBush(IBlockState state) {
		return plantableBlocks().contains(state.getBlock());
	}

	protected abstract ArrayList<Block> plantableBlocks();

	protected PropertyInteger getAgeProperty() {
		return AGE;
	}

	public int getMaxAge() {
		return 7;
	}

	protected int getAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue();
	}

	public IBlockState withAge(int age) {
		return this.getDefaultState().withProperty(this.getAgeProperty(), Integer.valueOf(age));
	}

	public boolean isMaxAge(IBlockState state) {
		return ((Integer) state.getValue(this.getAgeProperty())).intValue() >= this.getMaxAge();
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (isCorrectLightLevel(worldIn, pos)) {
			int i = this.getAge(state);

			if (i < this.getMaxAge()) {
				float f = getGrowthChance(this, worldIn, pos);

				if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
					worldIn.setBlockState(pos, this.withAge(i + 1), 2);
				}
			}
		}
	}

	private boolean isCorrectLightLevel(World world, BlockPos pos) {
		int light = 0;
		int a = 0;
		a = world.getLight(pos.down());
		light = a > light ? a : light;
		a = world.getLight(pos.up());
		light = a > light ? a : light;
		a = world.getLight(pos.north());
		light = a > light ? a : light;
		a = world.getLight(pos.south());
		light = a > light ? a : light;
		a = world.getLight(pos.east());
		light = a > light ? a : light;
		a = world.getLight(pos.west());
		light = a > light ? a : light;
		return isCorrectLightLevel(light);
	}

	protected abstract boolean isCorrectLightLevel(int currentLight);

	public void grow(World worldIn, BlockPos pos, IBlockState state) {
		int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn, pos, state);
		int j = this.getMaxAge();

		if (i > j) {
			i = j;
		}

		worldIn.setBlockState(pos, this.withAge(i), 2);
	}

	protected int getBonemealAgeIncrease(World worldIn, BlockPos pos, IBlockState state) {
		return isCorrectLightLevel(worldIn, pos) ? MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5) : 0;
	}

	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
		float f = 1.0F;
		BlockPos blockpos = pos.down();

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				float f1 = 0.0F;
				IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));

				if (iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, blockpos.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
					f1 = 1.0F;

					if (iblockstate.getBlock().isFertile(worldIn, blockpos.add(i, 0, j))) {
						f1 = 3.0F;
					}
				}

				if (i != 0 || j != 0) {
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos blockpos1 = pos.north();
		BlockPos blockpos2 = pos.south();
		BlockPos blockpos3 = pos.west();
		BlockPos blockpos4 = pos.east();
		boolean flag = blockIn == worldIn.getBlockState(blockpos3).getBlock() || blockIn == worldIn.getBlockState(blockpos4).getBlock();
		boolean flag1 = blockIn == worldIn.getBlockState(blockpos1).getBlock() || blockIn == worldIn.getBlockState(blockpos2).getBlock();

		if (flag && flag1) {
			f /= 2.0F;
		} else {
			boolean flag2 = blockIn == worldIn.getBlockState(blockpos3.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos3.south()).getBlock();

			if (flag2) {
				f /= 2.0F;
			}
		}

		return f;
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return canSustainBush(soil) ? soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) : false;
	}

	protected abstract Item getSeed();

	protected abstract Item getCrop();

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Nullable
	@Override
	public abstract Item getItemDropped(IBlockState state, Random rand, int fortune);

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this.getSeed());
	}

	/**
	 * Whether this IGrowable can grow
	 */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return !this.isMaxAge(state);
	}

	@Override
	public abstract boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state);

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.grow(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		return this.withAge(meta);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		return this.getAge(state);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

}
