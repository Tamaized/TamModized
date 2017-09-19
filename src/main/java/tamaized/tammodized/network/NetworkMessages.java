package tamaized.tammodized.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.tammodized.network.client.ClientPacketHandlerFloatyText;
import tamaized.tammodized.network.client.ClientPacketHandlerParticle;
import tamaized.tammodized.network.client.ClientPacketHandlerPlayerMotion;

public class NetworkMessages {

	private static int index = 0;

	public static void register(SimpleNetworkWrapper network) {
		registerMessage(network, ClientPacketHandlerParticle.class, ClientPacketHandlerParticle.Packet.class, Side.CLIENT);
		registerMessage(network, ClientPacketHandlerPlayerMotion.class, ClientPacketHandlerPlayerMotion.Packet.class, Side.CLIENT);
		registerMessage(network, ClientPacketHandlerFloatyText.class, ClientPacketHandlerFloatyText.Packet.class, Side.CLIENT);
	}

	private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(SimpleNetworkWrapper network, Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
		network.registerMessage(messageHandler, requestMessageType, index, side);
		index++;
	}
}
