package Tamaized.TamModized.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamBlock extends Block implements ITamModel {

	private final String name;

	public TamBlock(CreativeTabs tab, Material material, String n, float hardness, SoundType sound) {
		super(material);
		name = n;
		setUnlocalizedName(name);
		setHardness(hardness);
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
