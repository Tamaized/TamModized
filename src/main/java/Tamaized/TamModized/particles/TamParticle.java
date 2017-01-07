package Tamaized.TamModized.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class TamParticle extends Particle {

	private Vec3d location;

	public TamParticle(World world, Vec3d pos) {
		super(world, pos.xCoord, pos.yCoord, pos.zCoord);
	}

	public TamParticle(World world, Vec3d pos, Vec3d target) {
		this(world, pos);
		location = target;
	}

	protected boolean hasLocation() {
		return location != null;
	}

	protected Vec3d getLocation() {
		return location;
	}
	
	@Override
	public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		if (render(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ)) {
			super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
		}
	}

	/**
	 * Return true if you wish to super call {@link Particle#renderParticle(VertexBuffer, Entity, float, float, float, float, float, float)}
	 */
	public abstract boolean render(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ);

}
