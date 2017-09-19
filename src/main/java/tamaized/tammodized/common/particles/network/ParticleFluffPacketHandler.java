package tamaized.tammodized.common.particles.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.client.particles.ParticleFluff;
import tamaized.tammodized.common.particles.ParticleHelper;
import tamaized.tammodized.common.particles.ParticlePacketBase;
import tamaized.tammodized.common.particles.ParticlePacketHandlerRegistry;
import tamaized.tammodized.common.particles.TamParticle;
import tamaized.tammodized.registry.TamModizedParticles;

import java.io.IOException;

public class ParticleFluffPacketHandler extends ParticlePacketBase {

	public static void spawnOnServer(World world, Vec3d pos, Vec3d target, int life, float gravity, float scale, int color) {
		ParticleFluffData data = ((ParticleFluffPacketHandler) ParticlePacketHandlerRegistry.getHandler(TamModizedParticles.fluff)).new ParticleFluffData(target == null ? Vec3d.ZERO : target, life, gravity, scale, color);
		ParticleHelper.sendPacketToClients(world, TamModizedParticles.fluff, pos, 64, new ParticleHelper.ParticlePacketHelper(TamModizedParticles.fluff, data));
	}

	@Override
	public void encode(ByteBuf packet, ParticleHelper.IParticlePacketData data) {
		try {
			if (!(data instanceof ParticleFluffData))
				throw new IOException("Incorrect IParticlePacketData type: " + data);
			packet.writeBoolean(true);
			ParticleFluffData dat = (ParticleFluffData) data;
			packet.writeDouble(dat.target.x);
			packet.writeDouble(dat.target.y);
			packet.writeDouble(dat.target.z);
			packet.writeInt(dat.life);
			packet.writeFloat(dat.gravity);
			packet.writeFloat(dat.scale);
			packet.writeInt(dat.color);
		} catch (IOException e) {
			packet.writeBoolean(false);
			e.printStackTrace();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TamParticle decode(ByteBuf packet, WorldClient world, Vec3d pos) {
		if (packet.readBoolean()) {
			return new ParticleFluff(world, pos, new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble()), packet.readInt(), packet.readFloat(), packet.readFloat(), packet.readInt());
		} else {
			TamModized.instance.logger.error("Something went wrong, sent out a dummy particle");
			return new ParticleFluff(world, pos, new Vec3d(0, 0, 0), 0, 0, 0, 0);
		}
	}

	public class ParticleFluffData implements ParticleHelper.IParticlePacketData {

		public final Vec3d target;
		public final int life;
		public final float gravity;
		public final float scale;
		public final int color;

		public ParticleFluffData(Vec3d t, int life, float gravity, float scale, int color) {
			target = t;
			this.life = life;
			this.gravity = gravity;
			this.scale = scale;
			this.color = color;
		}

	}

}
