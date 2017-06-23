package Tamaized.TamModized.registry;

import Tamaized.TamModized.blocks.slab.TamBlockSlab;
import Tamaized.TamModized.client.MeshDefinitionFix;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegistryHelper {

	@Deprecated
	@SideOnly(Side.CLIENT)
	public static void registerFluidModel(IFluidBlock fluidBlock, String modid) {
		final Item item = Item.getItemFromBlock((Block) fluidBlock);

		ModelBakery.registerItemVariants(item);

		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(modid + ":blocks/fluids", fluidBlock.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}

	public static Fluid createFluid(String modid, String name, String textureName, boolean hasFlowIcon, boolean hasBucket) {
		ResourceLocation still = new ResourceLocation(modid, textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(modid, textureName + "_flow") : still;
		Fluid fluid = new Fluid(name, still, flowing);
		if (!FluidRegistry.registerFluid(fluid))
			fluid = FluidRegistry.getFluid(name);
		if (hasBucket)
			FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

}
