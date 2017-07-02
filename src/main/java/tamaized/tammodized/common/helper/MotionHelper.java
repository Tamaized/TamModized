package tamaized.tammodized.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.network.ClientPacketHandler;

import java.io.DataOutputStream;
import java.io.IOException;

public class MotionHelper {

	public static void addMotion(Entity e, double x, double y, double z) {
		if (e.world.isRemote) return;
		if (e instanceof EntityPlayerMP) sendPacketToPlayer((EntityPlayerMP) e, x, y, z);
		else e.addVelocity(x, y, z);
	}

	private static void sendPacketToPlayer(EntityPlayerMP e, double x, double y, double z) {
		try {
			PacketHelper.PacketWrapper packet = PacketHelper.createPacket(TamModized.channel, TamModized.networkChannelName, ClientPacketHandler.PLAYER_MOTION);
			DataOutputStream stream = packet.getStream();
			stream.writeDouble(x);
			stream.writeDouble(y);
			stream.writeDouble(z);
			packet.sendPacket(e);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void updatePlayerMotion(double x, double y, double z) {
		net.minecraft.client.Minecraft.getMinecraft().player.addVelocity(x, y, z);
	}

}