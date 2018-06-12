package tamaized.tammodized.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.registry.ITamRegistry;

import java.util.Random;

public abstract class TamBlockPortal extends BlockBreakable implements ITamRegistry {

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[]{EnumFacing.Axis.X, EnumFacing.Axis.Z});
	private final String name;

	public TamBlockPortal(CreativeTabs tab, String n, boolean hasAxis, SoundType sound) {
		super(Material.PORTAL, false);
		if (hasAxis)
			this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
		this.setTickRandomly(true);
		this.setLightLevel(0.75F);
		name = n;
		ModContainer container = Loader.instance().activeModContainer();
		setUnlocalizedName(container == null ? name : (container.getModId().toLowerCase() + "." + name));
		setRegistryName(name);
		setCreativeTab(tab);
		setSoundType(sound);
	}

	public static int getMetaForAxis(EnumFacing.Axis axis) {
		return axis == EnumFacing.Axis.X ? 1 : (axis == EnumFacing.Axis.Z ? 2 : 0);
	}

	public String getModelDir() {
		return "blocks";
	}

	@Override
	public void registerModel(ModelRegistryEvent e) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(new ResourceLocation(getRegistryName().getResourceDomain(), getModelDir() + "/" + getRegistryName().getResourcePath()), "inventory"));
	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(new ItemBlock(this).setRegistryName(name));
	}

	/**
	 * Override this and return getDefaultState() if hasAxis is false
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	/**
	 * Override this and return 0 if hasAxis is false
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return getMetaForAxis((EnumFacing.Axis) state.getValue(AXIS));
	}

	/**
	 * Override this and return 'new BlockState(this, new IProperty[0])' if
	 * hasAxis is false
	 */
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{AXIS});
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		super.updateTick(par1World, pos, state, par5Random);
		if (par1World.provider.isSurfaceWorld()) {
			int l;
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			for (l = y; !par1World.isSideSolid(new BlockPos(x, l, z), EnumFacing.UP) && l > 0; --l) {
				;
			}
			if (l > 0 && !par1World.isBlockNormalCube(new BlockPos(x, l + 1, z), false)) {
			} // Prevent pigmen from spawning
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this
	 * box can change after the pool has been cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	/**
	 * Is this block (a) opaque and (B) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		// We handle this elsewhere
	}

	public abstract boolean tryToCreatePortal(World par1World, BlockPos pos);

	@Override
	public abstract void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos p_189540_5_);

	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public abstract void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand);

	/**
	 * Returns true if the given side of this block type should be rendered, if
	 * the adjacent block is at the given coordinates. Args: blockAccess, x, y,
	 * z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public abstract boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side);

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random par1Random) {
		return 0;
	}

}
