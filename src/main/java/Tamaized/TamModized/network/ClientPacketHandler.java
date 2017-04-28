package Tamaized.TamModized.network;

import java.io.IOException;

import Tamaized.TamModized.helper.FloatyTextHelper;
import Tamaized.TamModized.helper.MotionHelper;
import Tamaized.TamModized.particles.ParticleHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketHandler {

	public static final int TYPE_PARTICLE = 0;
	public static final int TYPE_PARTICLE_VANILLA = 1;
	public static final int PLAYER_MOTION = 2;
	public static final int FloatyText = 3;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientPacket(ClientCustomPacketEvent event) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			public void run() {
				processPacket(event.getPacket().payload());
			}
		});
	}

	@SideOnly(Side.CLIENT)
	private void processPacket(ByteBuf packet) {
		try {
			ByteBufInputStream bbis = new ByteBufInputStream(packet);
			{
				int pktType = bbis.readInt();
				switch (pktType) {
					case TYPE_PARTICLE:
						ParticleHelper.decodePacket(bbis);
						break;
					case TYPE_PARTICLE_VANILLA:
						ParticleHelper.decodePacketVanillaParticle(bbis);
						break;
					case PLAYER_MOTION:
						MotionHelper.updatePlayerMotion(bbis.readDouble(), bbis.readDouble(), bbis.readDouble());
						break;
					case FloatyText:
						FloatyTextHelper.decode(bbis);
						break;
					default:
						break;
				}
			}
			bbis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
