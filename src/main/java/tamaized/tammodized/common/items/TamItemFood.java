package tamaized.tammodized.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import tamaized.tammodized.registry.ITamRegistry;

@SuppressWarnings("unused")
public abstract class TamItemFood extends ItemFood implements ITamRegistry {


	public TamItemFood(CreativeTabs tab, String name, int maxStackSize, int hungerAmount, boolean isWolfFood) {
		super(hungerAmount, isWolfFood);
		ModContainer container = Loader.instance().activeModContainer();
		setTranslationKey(container == null ? name : (container.getModId().toLowerCase() + "." + name));
		setMaxStackSize(maxStackSize);
		setRegistryName(name);
		this.setCreativeTab(tab);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		doneEating(stack, worldIn, entityLiving);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	protected abstract void doneEating(ItemStack stack, World worldIn, EntityLivingBase entityLiving);

	public String getModelDir() {
		return "items";
	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {

	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerModel(ModelRegistryEvent e) {
		if (getRegistryName() != null)
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(getRegistryName().getNamespace(), getModelDir() + "/" + getRegistryName().getPath()), "inventory"));
	}

}
