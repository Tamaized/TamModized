package Tamaized.TamModized.items;

import Tamaized.TamModized.registry.ITamModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class TamItemFood extends ItemFood implements ITamModel {

	private final String name;

	public TamItemFood(CreativeTabs tab, String n, int maxStackSize, int hungerAmount, boolean isWolfFood) {
		super(hungerAmount, isWolfFood);
		name = n;
		setUnlocalizedName(name);
		setMaxStackSize(maxStackSize);
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + n));
		this.setCreativeTab(tab);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		doneEating(stack, worldIn, entityLiving);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	protected abstract void doneEating(ItemStack stack, World worldIn, EntityLivingBase entityLiving);
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "items";
	}

	@Override
	public Item getAsItem() {
		return this;
	}

}
