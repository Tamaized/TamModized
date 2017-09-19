package tamaized.tammodized.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.network.client.ClientPacketHandlerPlayerMotion;

public class MotionHelper {

	public static void addMotion(Entity e, Vec3d vel) {
		if (e instanceof EntityPlayerMP)
			sendPacketToPlayer((EntityPlayerMP) e, vel);
		else
			e.addVelocity(vel.x, vel.y, vel.z);
	}

	private static void sendPacketToPlayer(EntityPlayerMP e, Vec3d vel) {
		TamModized.network.sendTo(new ClientPacketHandlerPlayerMotion.Packet(e, vel), e);
	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	public static void updatePlayerMotion(double x, double y, double z) {
		net.minecraft.client.Minecraft.getMinecraft().player.addVelocity(x, y, z);
	}

}