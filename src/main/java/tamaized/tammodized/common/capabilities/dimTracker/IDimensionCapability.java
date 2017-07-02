package tamaized.tammodized.common.capabilities.dimTracker;

import java.io.DataOutputStream;
import java.io.IOException;

import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayer;

public interface IDimensionCapability {

	public void markDirty();

	void update(EntityPlayer player);

	int getTick();

	int getLastDimension();
	
	void setLastDimension(int dim);

	void copyFrom(IDimensionCapability cap);

	void decodePacket(ByteBufInputStream stream) throws IOException;

	void encodePacket(DataOutputStream stream) throws IOException;

}
