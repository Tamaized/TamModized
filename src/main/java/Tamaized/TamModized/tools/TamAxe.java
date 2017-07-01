package Tamaized.TamModized.tools;

import Tamaized.TamModized.registry.ITamRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TamAxe extends ItemAxe implements ITamRegistry {

	private final String name;

	public TamAxe(CreativeTabs tab, ToolMaterial material, String n) {
		super(material, material.getDamageVsEntity(), -3.0f);
		name = n;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
	}

	public String getModelDir() {
		return "tools";
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
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().getResourceDomain() + ":" + getModelDir() + "/" + getRegistryName().getResourcePath(), "inventory"));
	}

}
