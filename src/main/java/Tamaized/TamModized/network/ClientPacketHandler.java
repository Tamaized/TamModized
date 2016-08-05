package Tamaized.TamModized.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import Tamaized.TamModized.particles.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketHandler {

	public static final int TYPE_PARTICLE = 0;

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
