package Tamaized.TamModized.registry;

import java.util.ArrayList;

import Tamaized.TamModized.fluids.FluidModelHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class TamFluidRegistryBase implements ITamRegistry {

	private ArrayList<BlockFluidBase> fluids = new ArrayList<BlockFluidBase>();

	protected void register(BlockFluidBase fluid) {
		fluids.add(fluid);
	}

	@SideOnly(Side.CLIENT)
	public void clientPreInit() {
		for (BlockFluidBase f : fluids) {
			FluidModelHandler.registerFluidModel(f, getModID());
		}
	}

	protected Fluid createFluid(String name, String textureName, boolean hasFlowIcon) {
		ResourceLocation still = new ResourceLocation(getModID(), textureName + "_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(getModID(), textureName + "_flow") : still;
		Fluid fluid = new Fluid(name, still, flowing);
		if (!FluidRegistry.registerFluid(fluid)) fluid = FluidRegistry.getFluid(name);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	@Override
	public ArrayList<ITamModel> getModelList() {
		return new ArrayList<ITamModel>();
	}

}
