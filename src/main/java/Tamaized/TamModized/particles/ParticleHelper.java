package Tamaized.TamModized.particles;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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

	public static void sendPacketToClients(World world, int id, Vec3d pos, Vec3d target, int range) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ClientPacketHandler.TYPE_PARTICLE);
			outputStream.writeInt(id);
			outputStream.writeDouble(pos.xCoord);
			outputStream.writeDouble(pos.yCoord);
			outputStream.writeDouble(pos.zCoord);
			if (target != null) {
				outputStream.writeBoolean(true);
				outputStream.writeDouble(target.xCoord);
				outputStream.writeDouble(target.yCoord);
				outputStream.writeDouble(target.zCoord);
			} else {
				outputStream.writeBoolean(false);
			}
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
			int id = packet.readInt();
			Vec3d pos = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
			Vec3d target = null;
			if (packet.readBoolean()) target = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
			spawnParticle(new ParticleContructor(ParticleRegistry.getParticle(id), Minecraft.getMinecraft().theWorld, pos, target));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void spawnParticle(ParticleContructor particle) {
		try {
			Minecraft.getMinecraft().effectRenderer.addEffect(particle.constructParticle());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static class ParticleContructor {

		private final Class<? extends TamParticle> c;
		private final World worldObj;
		private final Vec3d pos;
		private final Vec3d target;

		public ParticleContructor(Class<? extends TamParticle> p, World world, Vec3d pos, Vec3d target) {
			c = p;
			worldObj = world;
			this.pos = pos;
			this.target = target;
		}

		public TamParticle constructParticle() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
			return c.getDeclaredConstructor(World.class, Vec3d.class, Vec3d.class).newInstance(worldObj, pos, target);
		}

	}

}
