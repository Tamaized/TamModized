package Tamaized.TamModized.blocks;

import Tamaized.TamModized.registry.ITamRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TamBlockFence extends BlockFence implements ITamRegistry {

	private final String name;

	public TamBlockFence(CreativeTabs tab, Material materialIn, MapColor mapColor, String n, SoundType sound) {
		super(materialIn, mapColor);
		name = n;
		setUnlocalizedName(name);
		setRegistryName(getModelDir() + "/" + name);
		setCreativeTab(tab);
		setSoundType(sound);
	}
	public String getModelDir() {
		return "blocks";
	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(new ItemBlock(this).setRegistryName(getModelDir() + "/" + name));
	}

	@Override
	public void registerModel(ModelRegistryEvent e) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ModelResourceLocation(getRegistryName(), "inventory"), "inventory"));
	}

}
