package tamaized.tammodized.client.particles;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tamaized.tammodized.client.AtlasSpriteList;
import tamaized.tammodized.common.particles.TamParticle;

public class ParticleFluff extends TamParticle {

	public ParticleFluff(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		super(world, pos, target);
		motionX = target.x;
		motionY = target.y;
		motionZ = target.z;
		particleMaxAge = life;
		particleGravity = gravity;
		particleScale = scale;
		particleRed = ((color >> 24) & 0xFF) / 255F;
		particleGreen = ((color >> 16) & 0xFF) / 255F;
		particleBlue = ((color >> 8) & 0xFF) / 255F;
		particleAlpha = ((color) & 0xFF) / 255F;
		particleTexture = AtlasSpriteList.fluff;
	}

	@Override
	public boolean render(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		return true;
	}

	@Override
	public int getBrightnessForRender(float p_70070_1_) {
		float f1 = 1.0f;
		int i = super.getBrightnessForRender(p_70070_1_);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f1 * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}
		return j | k << 16;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

}
