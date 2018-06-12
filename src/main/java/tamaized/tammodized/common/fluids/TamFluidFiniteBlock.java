package tamaized.tammodized.common.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.registry.ITamRegistry;
import tamaized.tammodized.registry.RegistryHelper;

import java.util.Random;

public class TamFluidFiniteBlock extends BlockFluidFinite implements ITamRegistry {

	private final String name;
	private final DamageSource damageSource;
	private final int damage;

	public TamFluidFiniteBlock(CreativeTabs tab, Fluid fluid, Material material, String name, DamageSource source, int dmg) {
		super(fluid, material);
		this.name = name;
		damageSource = source;
		damage = dmg;
		ModContainer container = Loader.instance().activeModContainer();
		setUnlocalizedName(container == null ? name : (container.getModId().toLowerCase() + "." + name));
		setRegistryName(name);
		this.setCreativeTab(tab);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
	}

	public String getModelDir() {
		return "fluids";
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
		entityIn.attackEntityFrom(damageSource, damage);
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