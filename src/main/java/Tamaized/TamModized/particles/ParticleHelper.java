package Tamaized.TamModized.particles;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import scala.tools.nsc.settings.Final;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.network.ClientPacketHandler;

public class ParticleHelper {

	public static void sendPacketToClients(World world, int particleID, int handlerID, Vec3d pos, int range, ParticlePacketHelper packetHelper) {
		System.out.println("sendPacketToClients");
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_PARTICLE);
			outputStream.writeInt(particleID);
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
		System.out.println("decode");
		try {
			int particleID = packet.readInt();
			int handlerID = packet.readInt();
			Vec3d pos = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
			spawnParticle(ParticlePacketHandlerRegistry.getHandler(handlerID).decode(packet, ParticleRegistry.getParticle(particleID), Minecraft.getMinecraft().theWorld, pos));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticle(ParticleContructor particle) {
		System.out.println("spawnParticle");
		try {
			Minecraft.getMinecraft().effectRenderer.addEffect(particle.constructParticle());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static class ParticleContructor {

		private final Class<? extends TamParticle> c;
		private final ArrayList<Class<?>> paramsClass;
		private final ArrayList<Object> params;

		public ParticleContructor(Class<? extends TamParticle> p, ArrayList<Class<?>> classes, ArrayList<Object> instances) {
			c = p;
			paramsClass = classes;
			params = instances;
		}

		public TamParticle constructParticle() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			return c.getDeclaredConstructor(paramsClass.toArray(new Class<?>[paramsClass.size()])).newInstance(params.toArray(new Object[params.size()]));
		}

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
