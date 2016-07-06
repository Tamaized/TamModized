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
		slots  = new ItemStack[amountOfSlots];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagList list = (NBTTagList) nbt.getTag("Items");
		slots = new ItemStack[getSizeInventory()];
		if(list != null){
			for(int i = 0; i < list.tagCount(); i++){
				NBTTagCompound nbtc = (NBTTagCompound) list.getCompoundTagAt(i);
				byte b = nbtc.getByte("Slot");
				if(b >= 0 && b < slots.length){
					slots[b] = ItemStack.loadItemStackFromNBT(nbtc);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < slots.length; i++){
			if(slots[i] != null){
				NBTTagCompound nbtc = new NBTTagCompound();
				nbtc.setByte("Slot", (byte) i);
				slots[i].writeToNBT(nbtc);
				list.appendTag(nbtc);
			}
		}
		nbt.setTag("Items", list);
		return nbt;
	}@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null){
			ItemStack itemstack;
			if(slots[i].stackSize <= j){
				itemstack = slots[i];
				slots[i] = null;
				return itemstack;
			}else{
				itemstack = slots[i].splitStack(j);
				if(slots[i].stackSize == 0) {
					slots[i] = null;
				}
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int i) {
		if(slots[i] != null){
			ItemStack itemstack = slots[i];
			slots[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		slots[i] = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public abstract int getInventoryStackLimit();

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) != this ? false: player.getDistanceSq((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public abstract boolean isItemValidForSlot(int i, ItemStack stack);

	@Override
	public int getField(int id) {
		switch(id){
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch(id){
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
		for(int i=0; i<slots.length; i++) slots[i] = null;
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
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		for(int i : getSlotsForFace(direction)){
			if(index == i){
				return isItemValidForSlot(index, itemStackIn);
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		for(int i : getSlotsForFace(direction)){
			if(index == i){
				return canExtractSlot(index);
			}
		}
		return false;
	}
	
	protected abstract boolean canExtractSlot(int i);
	
	protected abstract boolean canInsertSlot(int i);

}