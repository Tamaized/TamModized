package Tamaized.TamModized.api.voidcraft.power;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import Tamaized.TamModized.TamModized;

public class VoidicPowerItemHandler {

	public static void setItemValues(ItemStack stack, int power, int maxPower) {
		NBTTagCompound ct = stack.getSubCompound(TamModized.modid, true);
		ct.setInteger("voidicPower", power);
		ct.setInteger("maxVoidicPower", maxPower);
	}

	public static int fillItemVoidicPower(ItemStack stack, int amount) {
		if (getItemVoidicPower(stack) + amount > getItemMaxVoidicPower(stack)) {
			setItemVoidicPower(stack, getItemMaxVoidicPower(stack));
			return (getItemVoidicPower(stack) + amount) - getItemMaxVoidicPower(stack);
		} else {
			setItemVoidicPower(stack, getItemVoidicPower(stack) + amount);
			return 0;
		}
	}

	public static void setItemVoidicPower(ItemStack stack, int power) {
		stack.getSubCompound(TamModized.modid, true).setInteger("voidicPower", power);
	}

	public static int getItemVoidicPower(ItemStack stack) {
		return stack.getSubCompound(TamModized.modid, true).getInteger("voidicPower");
	}

	public static int getItemMaxVoidicPower(ItemStack stack) {
		return stack.getSubCompound(TamModized.modid, true).getInteger("maxVoidicPower");
	}

	public static float getItemVoidicPowerPerc(ItemStack stack) {
		return ((float) getItemVoidicPower(stack)) / ((float) getItemMaxVoidicPower(stack));
	}
}
