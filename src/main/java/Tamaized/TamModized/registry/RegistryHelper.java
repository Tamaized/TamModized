package Tamaized.TamModized.registry;

import Tamaized.TamModized.blocks.slab.TamBlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistryHelper {

	public static void registerBlockSlab(TamBlockSlab slab, TamBlockSlab doubleslab) {
		slab.setRegistryName(slab.getModelDir() + "/" + slab.getName());
		doubleslab.setRegistryName(doubleslab.getModelDir() + "/" + doubleslab.getName());
		GameRegistry.register(slab);
		GameRegistry.register(doubleslab);
		ItemSlab item = new ItemSlab(slab, slab, doubleslab);
		item.setRegistryName(slab.getRegistryName());
		GameRegistry.register(item);
	}

}
