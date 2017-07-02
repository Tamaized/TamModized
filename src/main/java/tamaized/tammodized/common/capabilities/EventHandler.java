package tamaized.tammodized.common.capabilities;

import tamaized.tammodized.common.capabilities.dimTracker.DimensionCapabilityHandler;
import tamaized.tammodized.common.capabilities.dimTracker.IDimensionCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(DimensionCapabilityHandler.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IDimensionCapability inst = CapabilityList.DIMENSION.getDefaultInstance();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.DIMENSION;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.DIMENSION ? CapabilityList.DIMENSION.<T> cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.DIMENSION.getStorage().writeNBT(CapabilityList.DIMENSION, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.DIMENSION.getStorage().readNBT(CapabilityList.DIMENSION, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public void updateClone(PlayerEvent.Clone e) {
		EntityPlayer oldPlayer = e.getOriginal();
		EntityPlayer newPlayer = e.getEntityPlayer();
		newPlayer.getCapability(CapabilityList.DIMENSION, null).copyFrom(oldPlayer.getCapability(CapabilityList.DIMENSION, null));
	}

}
