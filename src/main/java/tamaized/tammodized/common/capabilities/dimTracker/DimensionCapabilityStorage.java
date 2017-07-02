package tamaized.tammodized.common.capabilities.dimTracker;

import Tamaized.TamModized.TamModized;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class DimensionCapabilityStorage implements IStorage<IDimensionCapability> {

	public DimensionCapabilityStorage() {
		TamModized.instance.logger.info("DimensionCapabilityStorage Registered");
	}

	@Override
	public NBTBase writeNBT(Capability<IDimensionCapability> capability, IDimensionCapability instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("lastDim", instance.getLastDimension());
		return compound;
	}

	@Override
	public void readNBT(Capability<IDimensionCapability> capability, IDimensionCapability instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		instance.setLastDimension(compound.getInteger("lastDim"));
	}
}
