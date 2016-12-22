package Tamaized.TamModized.particles;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.network.ClientPacketHandler;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleHelper {

	public static void sendPacketToClients(World world, int handlerID, Vec3d pos, int range, ParticlePacketHelper packetHelper) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_PARTICLE);
			outputStream.writeInt(handlerID);
			outputStream.writeDouble(pos.xCoord);
			outputStream.writeDouble(pos.yCoord);
			outputStream.writeDouble(pos.zCoord);
			packetHelper.encode(outputStream);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), TamModized.networkChannelName);
			if (pkt != null) TamModized.channel.sendToAllAround(pkt, new TargetPoint(world.provider.getDimension(), pos.xCoord, pos.yCoord, pos.zCoord, range));
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void decodePacket(ByteBufInputStream packet) {
		try {
			int handlerID = packet.readInt();
			Vec3d pos = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
			spawnParticle(ParticlePacketHandlerRegistry.getHandler(handlerID).decode(packet, Minecraft.getMinecraft().world, pos));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticle(TamParticle particle) {
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

	public static void spawnVanillaParticleOnServer(World world, EnumParticleTypes particle, double x, double y, double z, double xS, double yS, double zS) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_PARTICLE_VANILLA);
			outputStream.writeInt(particle.getParticleID());
			outputStream.writeDouble(x);
			outputStream.writeDouble(y);
			outputStream.writeDouble(z);
			outputStream.writeDouble(xS);
			outputStream.writeDouble(yS);
			outputStream.writeDouble(zS);
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), TamModized.networkChannelName);
			if (TamModized.channel != null && packet != null) TamModized.channel.sendToAllAround(packet, new TargetPoint(world.provider.getDimension(), x, y, z, 64));
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void decodePacketVanillaParticle(ByteBufInputStream stream) throws IOException {
		spawnVanillaParticleOnClient(net.minecraft.client.Minecraft.getMinecraft().world, EnumParticleTypes.getParticleFromId(stream.readInt()), stream.readDouble(), stream.readDouble(), stream.readDouble(), stream.readDouble(), stream.readDouble(), stream.readDouble());
	}

	@SideOnly(Side.CLIENT)
	public static void spawnVanillaParticleOnClient(World world, EnumParticleTypes particle, double x, double y, double z, double xS, double yS, double zS) {
		world.spawnParticle(particle, x, y, z, xS, yS, zS);
	}

	public static interface IParticlePacketData {

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

		public final void encode(DataOutputStream stream) throws IOException {
			packetHandler.encode(stream, data);
		}

	}

}
