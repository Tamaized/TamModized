package tamaized.tammodized.common.blocks;

import tamaized.tammodized.registry.ITamRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

import java.util.Random;

public abstract class TamBlockFire extends BlockFire implements ITamRegistry {

	private final String name;

	public TamBlockFire(CreativeTabs tab, String n, SoundType sound) {
		name = n;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		setSoundType(sound);
	}

	public String getModelDir() {
		return "blocks";
	}

	@Override
	public void registerModel(ModelRegistryEvent e) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName().getResourceDomain() + ":" + getModelDir() + "/" + getRegistryName().getResourcePath(), "inventory"));
	}

	@Override
	public void registerBlock(RegistryEvent.Register<Block> e) {
		e.getRegistry().register(this);
	}

	@Override
	public void registerItem(RegistryEvent.Register<Item> e) {
		e.getRegistry().register(new ItemBlock(this).setRegistryName(name));
	}

	protected boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite())) {
				return true;
			}
		}
		return false;
	}

	protected abstract boolean canBeOnBlock(IBlockState state);

	protected abstract boolean isFireSource(IBlockState state);

	protected abstract boolean isVanillaSource();

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.getGameRules().getBoolean("doFireTick")) {
			if (!this.canPlaceBlockAt(worldIn, pos)) {
				worldIn.setBlockToAir(pos);
			}

			Block block = worldIn.getBlockState(pos.down()).getBlock();
			boolean flag = (isVanillaSource() && block.isFireSource(worldIn, pos.down(), EnumFacing.UP)) || isFireSource(worldIn.getBlockState(pos.down()));

			int i = state.getValue(AGE);

			if (!flag && worldIn.isRaining() && this.canDie(worldIn, pos) && rand.nextFloat() < 0.2F + (float) i * 0.03F) {
				worldIn.setBlockToAir(pos);
			} else {
				if (i < 15) {
					state = state.withProperty(AGE, i + rand.nextInt(3) / 2);
					worldIn.setBlockState(pos, state, 4);
				}

				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));

				if (!flag) {
					if (!this.canNeighborCatchFire(worldIn, pos)) {
						if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3 && !canBeOnBlock(worldIn.getBlockState(pos.down()))) {
							worldIn.setBlockToAir(pos);
						}

						return;
					}

					if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0) {
						worldIn.setBlockToAir(pos);
						return;
					}
				}

				boolean flag1 = worldIn.isBlockinHighHumidity(pos);
				int j = 0;

				if (flag1) {
					j = -50;
				}

				this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
				this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
				this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
				this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
				this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
				this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

				for (int k = -1; k <= 1; ++k) {
					for (int l = -1; l <= 1; ++l) {
						for (int i1 = -1; i1 <= 4; ++i1) {
							if (k != 0 || i1 != 0 || l != 0) {
								int j1 = 100;

								if (i1 > 1) {
									j1 += (i1 - 1) * 100;
								}

								BlockPos blockpos = pos.add(k, i1, l);
								int k1 = this.getNeighborEncouragement(worldIn, blockpos);

								if (k1 > 0) {
									int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);

									if (flag1) {
										l1 /= 2;
									}

									if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !this.canDie(worldIn, blockpos))) {
										int i2 = i + rand.nextInt(5) / 4;

										if (i2 > 15) {
											i2 = 15;
										}

										worldIn.setBlockState(blockpos, state.withProperty(AGE, i2), 3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face) {
		int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);

		if (random.nextInt(chance) < i) {
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
				int j = age + random.nextInt(5) / 4;

				if (j > 15) {
					j = 15;
				}

				worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, j), 3);
			} else {
				worldIn.setBlockToAir(pos);
			}

			if (iblockstate.getBlock() == Blocks.TNT) {
				Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, true));
			}
		}
	}

	private int getNeighborEncouragement(World worldIn, BlockPos pos) {
		if (!worldIn.isAirBlock(pos)) {
			return 0;
		} else {
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.values()) {
				i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()), i);
			}

			return i;
		}
	}
}