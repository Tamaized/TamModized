package Tamaized.TamModized.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TamTileEntityInventory extends TamTileEntity {

	private final ItemStackHandler[] inventory;

	public TamTileEntityInventory() {
		inventory = register();
	}

	public int getInventorySize() {
		return inventory.length;
	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	public void dropInventoryItems(World worldIn, BlockPos pos) {
		dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ());
	}

	public void dropInventoryItems(World worldIn, double x, double y, double z) {
		for (ItemStackHandler inv : inventory)
			for (int i = 0; i < inv.getSlots(); ++i) {
				ItemStack itemstack = inv.getStackInSlot(i);
				if (!itemstack.isEmpty())
					InventoryHelper.spawnItemStack(worldIn, x, y, z, itemstack);
			}
	}

	protected abstract ItemStackHandler[] register();

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		int index = 0;
		for (ItemStackHandler slot : inventory) {
			String id = "itemslot_" + index;
			if (nbt.hasKey(id))
				slot.deserializeNBT((NBTTagCompound) nbt.getTag(id));
			index++;
		}
		readNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		int index = 0;
		for (ItemStackHandler slot : inventory) {
			String id = "itemslot_" + index;
			nbt.setTag(id, slot.serializeNBT());
			index++;
		}
		writeNBT(nbt);
		return nbt;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return getCap(facing) != null || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing enumFacing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? getCap(enumFacing) : super.getCapability(capability, enumFacing);
	}

	@Nullable
	protected abstract <T extends IItemHandler> T getCap(EnumFacing face);

	public static class ItemStackFilterHandler extends ItemStackHandler {

		private final List<ItemStack> inputFilter;
		private final List<ItemStack> outputFilter;
		private final boolean insert;
		private final boolean extract;
		private List<Class<?>> inputClassFilter = new ArrayList<>();
		private List<Class<?>> outputClassFilter = new ArrayList<>();

		private int stackLimit = 64;

		public ItemStackFilterHandler(ItemStack[] input, boolean canInsert, ItemStack[] output, boolean canExtract) {
			super(1);
			inputFilter = Arrays.asList(input);
			outputFilter = Arrays.asList(output);
			insert = canInsert;
			extract = canExtract;
		}

		public ItemStackFilterHandler(Class<?>[] input, boolean canInsert, Class<?>[] output, boolean canExtract) {
			this(new ItemStack[0], canInsert, new ItemStack[0], canExtract);
			inputClassFilter = Arrays.asList(input);
			outputClassFilter = Arrays.asList(output);
		}

		public void setStackLimit(int limit) {
			stackLimit = limit;
		}

		@Override
		public int getSlotLimit(int slot) {
			return stackLimit;
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			if (!insert)
				return stack;
			if (!inputFilter.isEmpty()) {
				for (ItemStack s : inputFilter)
					if (ItemStack.areItemsEqual(stack, s))
						return super.insertItem(slot, stack, simulate);
			} else if (!inputClassFilter.isEmpty()) {
				for (Class<?> s : inputClassFilter)
					if (s.isInstance(stack.getItem()))
						return super.insertItem(slot, stack, simulate);
			} else {
				return super.insertItem(slot, stack, simulate);
			}
			return stack;
		}

		public ItemStack extractBypass(int slot, int amount, boolean simulate) {
			return super.extractItem(slot, amount, simulate);
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (!extract)
				return ItemStack.EMPTY;
			if (!outputFilter.isEmpty()) {
				ItemStack stack = super.extractItem(slot, amount, true);
				for (ItemStack s : outputFilter)
					if (ItemStack.areItemsEqual(stack, s))
						return super.extractItem(slot, amount, simulate);
			} else if (!outputClassFilter.isEmpty()) {
				ItemStack stack = super.extractItem(slot, amount, true);
				for (Class<?> s : outputClassFilter)
					if (s.isInstance(stack.getItem()))
						return super.extractItem(slot, amount, simulate);
			} else {
				return super.extractItem(slot, amount, simulate);
			}
			return ItemStack.EMPTY;
		}
	}

}