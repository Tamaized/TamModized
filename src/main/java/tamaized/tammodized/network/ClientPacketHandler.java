package tamaized.tammodized.network;

import java.io.IOException;

import tamaized.tammodized.common.capabilities.CapabilityList;
import tamaized.tammodized.common.capabilities.dimTracker.IDimensionCapability;
import tamaized.tammodized.common.helper.FloatyTextHelper;
import tamaized.tammodized.common.helper.MotionHelper;
import tamaized.tammodized.common.particles.ParticleHelper;
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
	public static final int DimensionCap = 4;

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
					case DimensionCap:
						IDimensionCapability cap = Minecraft.getMinecraft().player.getCapability(CapabilityList.DIMENSION, null);
						if(cap != null) cap.decodePacket(bbis);
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
