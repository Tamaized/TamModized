package tamaized.tammodized.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.common.particles.ParticleHelper;
import tamaized.tammodized.common.particles.ParticlePacketHandlerRegistry;
import tamaized.tammodized.common.particles.TamParticle;

public class ClientPacketHandlerParticle implements IMessageHandler<ClientPacketHandlerParticle.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(Packet message, EntityPlayer player, World world) {
		if (message.custom)
			ParticleHelper.spawnParticle(message.particle);
		else
			ParticleHelper.spawnParticle(world, EnumParticleTypes.getParticleFromId(message.handlerID), message.vec, message.vel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().player, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private TamParticle particle;

		private boolean custom;
		private int handlerID;
		private Vec3d vec;
		private Vec3d vel;
		private ParticleHelper.ParticlePacketHelper helper;

		public Packet() {

		}

		public Packet(int handler, Vec3d pos, ParticleHelper.ParticlePacketHelper helper) {
			custom = true;
			handlerID = handler;
			vec = pos;
			this.helper = helper;
		}

		public Packet(int particle, Vec3d pos, Vec3d vel) {
			custom = false;
			handlerID = particle;
			vec = pos;
			this.vel = vel;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			custom = buf.readBoolean();
			handlerID = buf.readInt();
			vec = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			if (custom)
				particle = ParticlePacketHandlerRegistry.getHandler(handlerID).decode(buf, Minecraft.getMinecraft().world, vec);
			else
				vel = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeBoolean(custom);
			buf.writeInt(handlerID);
			buf.writeDouble(vec.x);
			buf.writeDouble(vec.y);
			buf.writeDouble(vec.z);
			if (custom)
				helper.encode(buf);
			else {
				buf.writeDouble(vel.x);
				buf.writeDouble(vel.y);
				buf.writeDouble(vel.z);
			}

		}
	}
}
