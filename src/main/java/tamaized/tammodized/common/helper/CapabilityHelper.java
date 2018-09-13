package tamaized.tammodized.common.helper;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class CapabilityHelper {

	private CapabilityHelper() {

	}

	public static <T> T getCap(ICapabilityProvider provider, Capability<T> cap, EnumFacing face) {
		return provider != null && provider.hasCapability(cap, face) ? provider.getCapability(cap, face) : null;
	}

}
