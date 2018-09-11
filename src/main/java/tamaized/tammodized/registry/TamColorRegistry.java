package tamaized.tammodized.registry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
public class TamColorRegistry {

	@SideOnly(Side.CLIENT)
	public static void registerBlockColors(Block block, IBlockColor color) {
		net.minecraft.client.renderer.color.BlockColors blockColors = net.minecraft.client.Minecraft.getMinecraft().getBlockColors();
		net.minecraft.client.renderer.color.ItemColors itemColors = net.minecraft.client.Minecraft.getMinecraft().getItemColors();

		final net.minecraft.client.renderer.color.IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			@SuppressWarnings("deprecation") IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
		};

		blockColors.registerBlockColorHandler(color, block);
		itemColors.registerItemColorHandler(itemBlockColourHandler, block);
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemColors(Item item, IItemColor color) {
		net.minecraft.client.renderer.color.ItemColors itemColors = net.minecraft.client.Minecraft.getMinecraft().getItemColors();
		itemColors.registerItemColorHandler(color, item);
	}

}
