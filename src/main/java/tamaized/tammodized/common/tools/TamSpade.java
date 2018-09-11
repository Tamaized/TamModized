package tamaized.tammodized.common.tools;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import tamaized.tammodized.registry.ITamRegistry;

@SuppressWarnings("unused")
public class TamSpade extends ItemSpade implements ITamRegistry {

	public TamSpade(CreativeTabs tab, ToolMaterial material, String name) {
		super(material);
		ModContainer container = Loader.instance().activeModContainer();
		setTranslationKey(container == null ? name : (container.getModId().toLowerCase() + "." + name));
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
		if (getRegistryName() != null)
			ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(getRegistryName().getNamespace(), getModelDir() + "/" + getRegistryName().getPath()), "inventory"));
	}

}
