package Tamaized.TamModized.blocks;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import Tamaized.TamModized.registry.ITamRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TamBlockFarmland extends TamBlockContainer {

	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
	protected static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	public TamBlockFarmland(CreativeTabs tab, Material material, String n, float hardness, SoundType sound) {
		super(tab, material, n, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, Integer.valueOf(0)));
		this.setTickRandomly(true);
		this.setLightOpacity(255);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FARMLAND_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	protected abstract IBlockState getParentBlockState();

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		int i = ((Integer) state.getValue(MOISTURE)).intValue();

		if (!this.hasWater(worldIn, pos) && (getWaterBlock() != Blocks.WATER || !worldIn.isRainingAt(pos.up()))) {
			if (i > 0) {
				updateState(worldIn, pos, state.withProperty(MOISTURE, Integer.valueOf(i - 1)), 2);
			} else if (!this.hasCrops(worldIn, pos)) {
				updateState(worldIn, pos, getParentBlockState());
			}
		} else if (i < 7) {
			updateState(worldIn, pos, state.withProperty(MOISTURE, Integer.valueOf(7)), 2);
		}
	}

	private void updateState(World world, BlockPos pos, IBlockState newState, int flag) {
		TileEntity te1 = world.getTileEntity(pos);
		world.setBlockState(pos, newState, flag);
		TileEntity te2 = world.getTileEntity(pos);
		updateTiles(te1, te2);
	}

	private void updateState(World world, BlockPos pos, IBlockState newState) {
		TileEntity te1 = world.getTileEntity(pos);
		world.setBlockState(pos, newState);
		TileEntity te2 = world.getTileEntity(pos);
		updateTiles(te1, te2);
	}

	protected abstract void updateTiles(TileEntity oldTile, TileEntity newTile);

	/**
	 * Block's chance to react to a living entity falling on it.
	 */
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F && entityIn instanceof EntityLivingBase && (entityIn instanceof EntityPlayer || worldIn.getGameRules().getBoolean("mobGriefing")) && entityIn.width * entityIn.width * entityIn.height > 0.512F) {
			updateState(worldIn, pos, getParentBlockState());
		}

		super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
	}

	private boolean hasCrops(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof net.minecraftforge.common.IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) block);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		return getPlantList().contains(plantable);
	}

	protected abstract ArrayList<IPlantable> getPlantList();

	@Override
	public boolean isFertile(World world, BlockPos pos) {
		return ((Integer) world.getBlockState(pos).getValue(MOISTURE)) > 0;
	}

	private boolean hasWater(World worldIn, BlockPos pos) {
		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
			if (worldIn.getBlockState(blockpos$mutableblockpos).getBlock() == getWaterBlock()) {
				return true;
			}
		}

		return false;
	}

	protected abstract Block getWaterBlock();

	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid block, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
		super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);

		if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
			updateState(worldIn, pos, getParentBlockState());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
			case UP:
				return true;
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
				Block block = iblockstate.getBlock();
				return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH;
			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return getParentBlockState().getBlock().getItemDropped(getParentBlockState(), rand, fortune);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(getParentBlockState().getBlock());
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(MOISTURE, Integer.valueOf(meta & 7));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Integer) state.getValue(MOISTURE)).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { MOISTURE });
	}

}
