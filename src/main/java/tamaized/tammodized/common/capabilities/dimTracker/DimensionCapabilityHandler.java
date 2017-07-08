package tamaized.tammodized.common.capabilities.dimTracker;

import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.common.helper.PacketHelper;
import tamaized.tammodized.network.ClientPacketHandler;
import tamaized.tammodized.registry.PortalHandlerRegistry;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DimensionCapabilityHandler implements IDimensionCapability {

	public static final ResourceLocation ID = new ResourceLocation(TamModized.modid, "DimensionCapabilityHandler");
	private static final int teleportTick = 20 * 5;
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
				if (!hasTeleported) {
					tick++;
					if (tick % teleportTick == 0) {
						hasTeleported = true;
						try {
							PortalHandlerRegistry.doTeleport(this, (EntityPlayerMP) player, PortalHandlerRegistry.getTeleporter(player.world.getBlockState(player.getPosition())));
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				if (tick > 0)
					tick -= 2;
				else
					hasTeleported = false;
			}
		}
		if (markDirty && player instanceof EntityPlayerMP) {
			try {
				PacketHelper.PacketWrapper packet = PacketHelper.createPacket(TamModized.channel, TamModized.networkChannelName, ClientPacketHandler.DimensionCap);
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
	public boolean hasTeleported() {
		return hasTeleported;
	}

	@Override
	public void copyFrom(IDimensionCapability cap) {
		lastDim = cap.getLastDimension();
		tick = cap.getTick();
		hasTeleported = cap.hasTeleported();
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
