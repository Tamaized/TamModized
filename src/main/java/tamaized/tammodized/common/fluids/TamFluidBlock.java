package tamaized.tammodized.common.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.registry.ITamRegistry;
import tamaized.tammodized.registry.RegistryHelper;

@SuppressWarnings("unused")
public class TamFluidBlock extends BlockFluidClassic implements ITamRegistry {

	private final String name;

	public TamFluidBlock(CreativeTabs tab, Fluid fluid, Material material, String name) {
		super(fluid, material);
		this.name = name;
		ModContainer container = Loader.instance().activeModContainer();
		setTranslationKey(container == null ? name : (container.getModId().toLowerCase() + "." + name));
		setRegistryName(name);
		setCreativeTab(tab);
	}

	public String getModelDir() {
		return "fluids";
	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(ModelRegistryEvent e) {
		RegistryHelper.registerFluidModel(this);
	}
}
