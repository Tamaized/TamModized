package Tamaized.TamModized.helper;

import java.util.HashSet;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RayTraceHelper {

	private RayTraceHelper() {

	}

	public static Vec3d[] getPlayerTraceVec(EntityPlayer player, int distance) {
		Vec3d vec3d = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
		Vec3d vec3d1 = player.getLook(1.0f);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		return new Vec3d[] { vec3d, vec3d2 };
	}

	/**
	 * 
	 * @param world
	 * @param player
	 * @param distance
	 * @param borderSize
	 * @param excluded if null, doesn't check for entities, only blocks
	 * @return
	 */
	public static RayTraceResult tracePath(World world, EntityPlayer player, int distance, float borderSize, HashSet<Entity> excluded) {
		Vec3d[] vecs = getPlayerTraceVec(player, distance);
		return tracePath(world, vecs[0], vecs[1], borderSize, excluded);
	}

	/**
	 * 
	 * @param world
	 * @param vec1
	 * @param vec2
	 * @param borderSize
	 * @param excluded if null, doesn't check for entities, only blocks
	 * @return
	 */
	public static RayTraceResult tracePath(World world, Vec3d vec1, Vec3d vec2, float borderSize, HashSet<Entity> excluded) {
		return tracePath(world, (float) vec1.x, (float) vec1.y, (float) vec1.z, (float) vec2.x, (float) vec2.y, (float) vec2.z, borderSize, excluded);
	}

	/**
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param tx
	 * @param ty
	 * @param tz
	 * @param borderSize
	 * @param excluded if null, doesn't check for entities, only blocks
	 * @return
	 */
	private static RayTraceResult tracePath(World world, float x, float y, float z, float tx, float ty, float tz, float borderSize, HashSet<Entity> excluded) {
		Vec3d startVec = new Vec3d(x, y, z);
		Vec3d lookVec = new Vec3d(tx - x, ty - y, tz - z);
		Vec3d endVec = new Vec3d(tx, ty, tz);
		float minX = x < tx ? x : tx;
		float minY = y < ty ? y : ty;
		float minZ = z < tz ? z : tz;
		float maxX = x > tx ? x : tx;
		float maxY = y > ty ? y : ty;
		float maxZ = z > tz ? z : tz;
		AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ).expand(borderSize, borderSize, borderSize);
		List<Entity> allEntities = excluded == null ? Lists.<Entity>newArrayList() : world.getEntitiesWithinAABBExcludingEntity(null, bb);
		RayTraceResult blockHit = world.rayTraceBlocks(startVec, endVec);
		startVec = new Vec3d(x, y, z);
		endVec = new Vec3d(tx, ty, tz);
		float maxDistance = (float) endVec.distanceTo(startVec);
		if (blockHit != null) {
			maxDistance = (float) blockHit.hitVec.distanceTo(startVec);
		}
		Entity closestHitEntity = null;
		float closestHit = Float.POSITIVE_INFINITY;
		float currentHit = 0.f;
		AxisAlignedBB entityBb;// = ent.getBoundingBox();
		RayTraceResult intercept;
		for (Entity ent : allEntities) {
			if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
				float entBorder = ent.getCollisionBorderSize();
				entityBb = ent.getEntityBoundingBox();
				if (entityBb != null) {
					entityBb = entityBb.expand(entBorder, entBorder, entBorder);
					intercept = entityBb.calculateIntercept(startVec, endVec);
					if (intercept != null) {
						currentHit = (float) intercept.hitVec.distanceTo(startVec);
						if (currentHit < closestHit || currentHit == 0) {
							closestHit = currentHit;
							closestHitEntity = ent;
						}
					}
				}
			}
		}
		if (closestHitEntity != null) {
			blockHit = new RayTraceResult(closestHitEntity);
		}
		return blockHit;
	}

}
