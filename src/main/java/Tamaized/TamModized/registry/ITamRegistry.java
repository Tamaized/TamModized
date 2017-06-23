package Tamaized.TamModized.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITamRegistry {

	void registerBlock(RegistryEvent.Register<Block> e);

	void registerItem(RegistryEvent.Register<Item> e);

	void registerModel(ModelRegistryEvent e);

}
