package tamaized.tammodized.common.capabilities.dimTracker;

import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;

import java.io.DataOutputStream;
import java.io.IOException;

public interface IDimensionCapability {

	public void markDirty();

	void update(EntityPlayer player);

	int getTick();

	int getLastDimension();

	void setLastDimension(int dim);

	boolean hasTeleported();

	void copyFrom(IDimensionCapability cap);

	void decodePacket(ByteBufInputStream stream) throws IOException;

	void encodePacket(DataOutputStream stream) throws IOException;

}
