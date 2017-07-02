package tamaized.tammodized.common.capabilities;

import tamaized.tammodized.common.capabilities.dimTracker.IDimensionCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityList {
	
	@CapabilityInject(IDimensionCapability.class)
	public static final Capability<IDimensionCapability> DIMENSION = null;

}
