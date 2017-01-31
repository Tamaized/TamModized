package Tamaized.TamModized.particles.FX;

import org.lwjgl.opengl.GL11;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.particles.TamParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleFluff extends TamParticle {

	private static final ResourceLocation TEXTURE = new ResourceLocation(TamModized.modid, "textures/particles/fluff.png");

	public ParticleFluff(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		super(world, pos, target);
		motionX = target.xCoord;
		motionY = target.yCoord;
		motionZ = target.zCoord;
		particleMaxAge = life;
		particleGravity = gravity;
		particleScale = scale;
		particleRed = ((color >> 24) & 0xFF) / 255F;
		particleGreen = ((color >> 16) & 0xFF) / 255F;
		particleBlue = ((color >> 8) & 0xFF) / 255F;
		particleAlpha = ((color) & 0xFF) / 255F;
	}

	@Override
	public boolean render(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexbuffer = tessellator.getBuffer();
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GlStateManager.enableBlend();
//		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		float scale = 0.1F * particleScale;
		float x = (float) (prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
		float y = (float) (prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
		float z = (float) (prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
		int i = getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		vertexbuffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		vertexbuffer.pos(x - rotationX * scale - rotationXY * scale, y - rotationZ * scale, z - rotationYZ * scale - rotationXZ * scale).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		vertexbuffer.pos(x - rotationX * scale + rotationXY * scale, y + rotationZ * scale, z - rotationYZ * scale + rotationXZ * scale).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		vertexbuffer.pos(x + rotationX * scale + rotationXY * scale, y + rotationZ * scale, z + rotationYZ * scale + rotationXZ * scale).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		vertexbuffer.pos(x + rotationX * scale - rotationXY * scale, y - rotationZ * scale, z + rotationYZ * scale - rotationXZ * scale).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
		tessellator.draw();
		GlStateManager.disableBlend();
		return false;
	}

	@Override
	public int getBrightnessForRender(float p_70070_1_) {
		float f1 = ((float) this.particleAge + p_70070_1_) / (float) this.particleMaxAge;

		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f1 > 1.0F) {
			f1 = 1.0F;
		}
		f1 = 1.0f;

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
		return 3;
	}

}
