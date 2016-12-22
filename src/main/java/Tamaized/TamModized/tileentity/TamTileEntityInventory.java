package Tamaized.TamModized.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public abstract class TamTileEntityInventory extends TamTileEntity implements ISidedInventory {

	protected ItemStack[] slots;

	public TamTileEntityInventory(int amountOfSlots) {
		slots = new ItemStack[amountOfSlots];
		for (int index = 0; index < slots.length; index++)
			slots[index] = ItemStack.EMPTY;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = (NBTTagList) nbt.getTag("Items");
		slots = new ItemStack[getSizeInventory()];
		for (int index = 0; index < slots.length; index++)
			slots[index] = ItemStack.EMPTY;
		if (list != null) {
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound nbtc = (NBTTagCompound) list.getCompoundTagAt(i);
				byte b = nbtc.getByte("Slot");
				if (b >= 0 && b < slots.length) {
					slots[b] = new ItemStack(nbtc);
				}
			}
		}
		readNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < slots.length; i++) {
			if (!slots[i].isEmpty()) {
				NBTTagCompound nbtc = new NBTTagCompound();
				nbtc.setByte("Slot", (byte) i);
				slots[i].writeToNBT(nbtc);
				list.appendTag(nbtc);
			}
		}
		nbt.setTag("Items", list);
		writeNBT(nbt);
		return nbt;
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return (slot < getSizeInventory() && slot >= 0) ? slots[slot] : ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (!slots[i].isEmpty()) {
			ItemStack itemstack;
			if (slots[i].getCount() <= j) {
				itemstack = slots[i];
				slots[i] = ItemStack.EMPTY;
				return itemstack;
			} else {
				itemstack = slots[i].splitStack(j);
				if (slots[i].getCount() == 0) {
					slots[i] = ItemStack.EMPTY;
				}
				return itemstack;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot) {
		if (slot < getSizeInventory() && slot >= 0) {
			ItemStack itemstack = getStackInSlot(slot);
			setInventorySlotContents(slot, ItemStack.EMPTY);
			return itemstack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (slot < getSizeInventory() && slot >= 0) slots[slot] = stack;
	}

	@Override
	public abstract int getInventoryStackLimit();

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) != this ? false : player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public int getField(int id) {
		switch (id) {
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			default:
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < slots.length; i++)
			slots[i] = null;
	}

	@Override
	public abstract String getName();

	@Override
	public abstract boolean hasCustomName();

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public abstract int[] getSlotsForFace(EnumFacing side);

	@Override
	public final boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		int[] array = getSlotsForFace(direction);
		for (int i = 0; i < array.length; i++) {
			if (array[i] == index) {
				return isItemValidForSlot(index, itemStackIn);
			}
		}
		return false;
	}

	@Override
	public final boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		int[] array = getSlotsForFace(direction);
		for (int i = 0; i < array.length; i++) {
			if (array[i] == index) {
				return canExtractSlot(index, stack);
			}
		}
		return false;
	}

	@Override
	public abstract boolean isItemValidForSlot(int i, ItemStack stack);

	protected abstract boolean canExtractSlot(int i, ItemStack stack);

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : slots)
			if (!stack.isEmpty()) return false;
		return true;
	}

}