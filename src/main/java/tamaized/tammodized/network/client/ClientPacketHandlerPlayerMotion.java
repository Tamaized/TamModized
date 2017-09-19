package tamaized.tammodized.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.common.helper.MotionHelper;

public class ClientPacketHandlerPlayerMotion implements IMessageHandler<ClientPacketHandlerPlayerMotion.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerPlayerMotion.Packet message, @SuppressWarnings("unused") EntityPlayer player, World world) {
		MotionHelper.addMotion(world.getEntityByID(message.e), message.vel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerPlayerMotion.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().player, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int e;
		private Vec3d vel;

		public Packet() {

		}

		public Packet(Entity entity, Vec3d velocity) {
			e = entity.getEntityId();
			vel = velocity;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			e = buf.readInt();
			vel = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(e);
			buf.writeDouble(vel.x);
			buf.writeDouble(vel.y);
			buf.writeDouble(vel.z);
		}
	}
}
