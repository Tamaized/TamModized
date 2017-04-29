package Tamaized.TamModized.capabilities.dimTracker;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.helper.PacketHelper;
import Tamaized.TamModized.helper.PacketHelper.PacketWrapper;
import Tamaized.TamModized.network.ClientPacketHandler;
import Tamaized.TamModized.registry.PortalHandlerRegistry;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class DimensionCapabilityHandler implements IDimensionCapability {

	public static final ResourceLocation ID = new ResourceLocation(TamModized.modid, "DimensionCapabilityHandler");

	private boolean markDirty = false;

	private int lastDim = 0;
	private boolean hasTeleported = false;
	private int tick = 0;

	@Override
	public void markDirty() {
		markDirty = true;
	}

	@Override
	public void update(EntityPlayer player) {
		if (player != null && player.world != null && !player.world.isRemote && player instanceof EntityPlayerMP) {
			if (PortalHandlerRegistry.contains(player.world.getBlockState(player.getPosition()))) {
				tick++;
				if (!hasTeleported && tick % (20 * 5) == 0) {
					hasTeleported = true;
					try {
						PortalHandlerRegistry.doTeleport(this, (EntityPlayerMP) player, PortalHandlerRegistry.getTeleporter(player.world.getBlockState(player.getPosition())));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
				}
			} else {
				if (tick > 0) tick--;
				hasTeleported = false;
			}
		}
		if (markDirty && player instanceof EntityPlayerMP) {
			try {
				PacketWrapper packet = PacketHelper.createPacket(TamModized.channel, TamModized.networkChannelName, ClientPacketHandler.DimensionCap);
				encodePacket(packet.getStream());
				packet.sendPacket((EntityPlayerMP) player);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getTick() {
		return tick;
	}

	@Override
	public int getLastDimension() {
		return lastDim;
	}

	@Override
	public void setLastDimension(int dim) {
		lastDim = dim;
	}

	@Override
	public void copyFrom(IDimensionCapability cap) {
		lastDim = cap.getLastDimension();
	}

	@Override
	public void decodePacket(ByteBufInputStream stream) throws IOException {
		lastDim = stream.readInt();
	}

	@Override
	public void encodePacket(DataOutputStream stream) throws IOException {
		stream.writeInt(lastDim);
	}

}
