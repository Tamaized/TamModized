package tamaized.tammodized.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TamTileEntity extends TileEntity implements ITickable {

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeNBT(nbt);
		return nbt;
	}
	
	protected abstract void readNBT(NBTTagCompound nbt);
	
	protected abstract NBTTagCompound writeNBT(NBTTagCompound nbt);

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(pos, 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void update() {
		onUpdate();
		if (!world.isRemote) {
			world.markAndNotifyBlock(getPos(), world.getChunk(getPos()), world.getBlockState(getPos()), world.getBlockState(getPos()), 3);
		}
	}
	
	protected abstract void onUpdate();
	
}
