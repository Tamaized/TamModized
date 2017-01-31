package Tamaized.TamModized.items;

import java.util.ArrayList;

import Tamaized.TamModized.blocks.TamBlockCrops;
import Tamaized.TamModized.blocks.TamBlockFarmland;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TamItemSeed extends TamItem implements net.minecraftforge.common.IPlantable {

	private final TamBlockCrops crop;
	private final ArrayList<TamBlockFarmland> soil;

	public TamItemSeed(CreativeTabs tab, String n, int maxStackSize, TamBlockCrops crop, ArrayList<TamBlockFarmland> soil) {
		super(tab, n, maxStackSize);
		this.crop = crop;
		this.soil = soil;
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
		if (facing == EnumFacing.UP && playerIn.canPlayerEdit(pos.offset(facing), facing, stack) && soil.contains(state.getBlock()) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, crop) && worldIn.isAirBlock(pos.up())) {
			worldIn.setBlockState(pos.up(), this.crop.getDefaultState());
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return null;
	}

	@Override
	public net.minecraft.block.state.IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
		return this.crop.getDefaultState();
	}

}
