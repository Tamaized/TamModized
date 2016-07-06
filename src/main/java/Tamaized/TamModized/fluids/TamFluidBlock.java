package Tamaized.TamModized.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.TamModized.registry.ITamModel;

public class TamFluidBlock extends BlockFluidClassic implements ITamModel{
	
	private final String name;

	public TamFluidBlock(CreativeTabs tab, Fluid fluid, Material material, String name) {
		super(fluid, material);
		this.name = name;
		setUnlocalizedName(name);
		GameRegistry.register(this.setRegistryName(name));
		this.setCreativeTab(tab);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getModelDir() {
		return "fluids";
	} 

	@Override
	public Item getAsItem() {
		return Item.getItemFromBlock(this);
	}
}
