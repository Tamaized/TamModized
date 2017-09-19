package tamaized.tammodized.common.particles;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.network.client.ClientPacketHandlerParticle;

public class ParticleHelper {

	public static void sendPacketToClients(World world, int handlerID, Vec3d pos, @SuppressWarnings("SameParameterValue") int range, ParticlePacketHelper packetHelper) {
		TamModized.network.sendToAllAround(new ClientPacketHandlerParticle.Packet(handlerID, pos, packetHelper), new TargetPoint(world.provider.getDimension(), pos.x, pos.y, pos.z, range));
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticle(TamParticle particle) {
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	@SuppressWarnings("unused")
	public static void spawnVanillaParticleOnServer(World world, EnumParticleTypes particle, double x, double y, double z, double xS, double yS, double zS) {
		TamModized.network.sendToAllAround(new ClientPacketHandlerParticle.Packet(particle.getParticleID(), new Vec3d(x, y, z), new Vec3d(xS, yS, zS)), new TargetPoint(world.provider.getDimension(), x, y, z, 64));
	}

	public static void spawnParticle(World world, EnumParticleTypes particle, Vec3d pos, Vec3d vel) {
		world.spawnParticle(particle, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
	}

	public interface IParticlePacketData {

	}

	public static class ParticlePacketHelper {

		private final ParticlePacketBase packetHandler;
		private final IParticlePacketData data;

		public ParticlePacketHelper(ParticlePacketBase packetBase, IParticlePacketData dat) {
			packetHandler = packetBase;
			data = dat;
		}

		public ParticlePacketHelper(int packetBase, IParticlePacketData dat) {
			this(ParticlePacketHandlerRegistry.getHandler(packetBase), dat);
		}

		public final void encode(ByteBuf stream) {
			packetHandler.encode(stream, data);
		}

	}

}
