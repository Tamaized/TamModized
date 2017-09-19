package tamaized.tammodized.common.capabilities.dimTracker;

import net.minecraft.entity.player.EntityPlayer;

public interface IDimensionCapability {

	void update(EntityPlayer player);

	int getTick();

	int getLastDimension();

	void setLastDimension(int dim);

	boolean hasTeleported();

	void copyFrom(IDimensionCapability cap);

}
