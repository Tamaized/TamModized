package tamaized.tammodized.common.helper;

import java.util.List;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosionDamageHelper {

	public static void explode(Entity exploder, World world, Explosion explosion, float explosionSize, double explosionX, double explosionY, double explosionZ) {
		float f3 = explosionSize * 2.0F;
		int k1 = MathHelper.floor(explosionX - (double) f3 - 1.0D);
		int l1 = MathHelper.floor(explosionX + (double) f3 + 1.0D);
		int i2 = MathHelper.floor(explosionY - (double) f3 - 1.0D);
		int i1 = MathHelper.floor(explosionY + (double) f3 + 1.0D);
		int j2 = MathHelper.floor(explosionZ - (double) f3 - 1.0D);
		int j1 = MathHelper.floor(explosionZ + (double) f3 + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB((double) k1, (double) i2, (double) j2, (double) l1, (double) i1, (double) j1));
		// net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(world, explosion, list, f3);
		Vec3d vec3d = new Vec3d(explosionX, explosionY, explosionZ);

		for (Entity entity : list) {
			if (!entity.isImmuneToExplosions()) {
				double d12 = Math.max(1, entity.getDistance(explosionX, explosionY, explosionZ)) / (double) f3;

				if (d12 <= 1.0D) {
					double d5 = entity.posX - explosionX;
					double d7 = entity.posY + (double) entity.getEyeHeight() - explosionY;
					double d9 = entity.posZ - explosionZ;
					double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

					if (d13 != 0.0D) {
						d5 = d5 / d13;
						d7 = d7 / d13;
						d9 = d9 / d13;
						double d14 = (double) world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
						double d10 = (1.0D - d12) * d14;
						entity.attackEntityFrom(DamageSource.causeExplosionDamage(explosion), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f3 + 1.0D)));
						double d11 = d10;

						if (entity instanceof EntityLivingBase) {
							d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d10);
						}

						entity.motionX += d5 * d11;
						entity.motionY += d7 * d11;
						entity.motionZ += d9 * d11;

						if (entity instanceof EntityPlayer) {
							EntityPlayer entityplayer = (EntityPlayer) entity;

							if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying)) {
								explosion.getPlayerKnockbackMap().put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
							}
						}
					}
				}
			}
		}
	}

}
