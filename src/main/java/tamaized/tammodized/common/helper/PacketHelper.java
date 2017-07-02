package tamaized.tammodized.common.helper;

import java.io.DataOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public final class PacketHelper {

	private PacketHelper() {

	}

	public static PacketWrapper createPacket(FMLEventChannel channel, String networkChannelName, int packetID) throws IOException {
		if (channel == null || networkChannelName == null) return null;
		return new PacketWrapper(networkChannelName, channel, packetID);
	}

	public static class PacketWrapper {

		private final ByteBufOutputStream buff = new ByteBufOutputStream(Unpooled.buffer());
		private final DataOutputStream stream;
		private final String channelName;
		private final FMLEventChannel channel;

		public PacketWrapper(String channelName, FMLEventChannel channel, int packetID) throws IOException {
			stream = new DataOutputStream(buff);
			this.channelName = channelName;
			this.channel = channel;
			stream.writeInt(packetID);
		}

		public DataOutputStream getStream() {
			return stream;
		}

		public void sendPacket(TargetPoint point) throws IOException {
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buff.buffer()), channelName);
			channel.sendToAllAround(packet, point);
			buff.close();
		}

		public void sendPacket(EntityPlayerMP player) throws IOException {
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buff.buffer()), channelName);
			channel.sendTo(packet, player);
			buff.close();
		}

		public void sendPacketToServer() throws IOException {
			FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buff.buffer()), channelName);
			channel.sendToServer(packet);
			buff.close();
		}

	}

}
