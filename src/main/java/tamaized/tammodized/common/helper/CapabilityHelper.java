package tamaized.tammodized.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class CapabilityHelper {

	private CapabilityHelper() {

	}

	public static <T> T getCap(Entity entity, Capability<T> cap, EnumFacing face) {
		return entity != null && entity.hasCapability(cap, face) ? entity.getCapability(cap, face) : null;
	}

}
