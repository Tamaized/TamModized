package Tamaized.TamModized.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamBlockFence extends BlockFence implements ITamModel {

	private final String name;

	public TamBlockFence(CreativeTabs tab, Material materialIn, MapColor mapColor, String n, SoundType sound) {
		super(materialIn, mapColor);
		name = n;
		setUnlocalizedName(name);
		GameRegistry.register(this.setRegistryName(getModelDir() + "/" + getName()));
		GameRegistry.register(new ItemBlock(this).setRegistryName(getModelDir() + "/" + getName()));
		setCreativeTab(tab);
		setSoundType(sound);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "blocks";
	}

	@Override
	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}

}
