package Tamaized.TamModized.api.voidcraft.power;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Tamaized.TamModized.items.TamItem;

import com.mojang.realmsclient.gui.ChatFormatting;

public abstract class VoidicPowerItem extends TamItem {

	public VoidicPowerItem(CreativeTabs tab, String n, int maxStackSize) {
		super(tab, n, maxStackSize);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (VoidicPowerItemHandler.getItemMaxVoidicPower(stack) <= 0) VoidicPowerItemHandler.setItemValues(stack, getDefaultVoidicPower(), getDefaultMaxVoidicPower());
		if (VoidicPowerItemHandler.getItemVoidicPower(stack) > VoidicPowerItemHandler.getItemMaxVoidicPower(stack)) VoidicPowerItemHandler.setItemVoidicPower(stack, VoidicPowerItemHandler.getItemMaxVoidicPower(stack));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return VoidicPowerItemHandler.getItemVoidicPowerPerc(stack) < 1.0f;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1 - VoidicPowerItemHandler.getItemVoidicPowerPerc(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(ChatFormatting.DARK_RED + "Power: " + VoidicPowerItemHandler.getItemVoidicPower(stack) + "/" + VoidicPowerItemHandler.getItemMaxVoidicPower(stack));
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	protected abstract int getDefaultVoidicPower();

	protected abstract int getDefaultMaxVoidicPower();

}
