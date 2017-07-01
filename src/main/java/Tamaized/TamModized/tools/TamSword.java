package Tamaized.TamModized.tools;

import Tamaized.TamModized.registry.ITamRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class TamSword extends ItemSword implements ITamRegistry {

	private final String name;

	public TamSword(CreativeTabs tab, ToolMaterial material, String n) {
		super(material);
		name = n;
		setUnlocalizedName(name);
		setRegistryName(n);
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
