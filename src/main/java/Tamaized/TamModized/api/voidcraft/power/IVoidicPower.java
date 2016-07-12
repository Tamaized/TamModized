package Tamaized.TamModized.api.voidcraft.power;

import net.minecraft.util.EnumFacing;

public interface IVoidicPower {

	public int getPowerAmount();

	public int getMaxPower();

	public int maxPowerTransfer();

	public int recievePower(int a);

	public int sendPower(int limit);

	public void setPowerAmount(int amount);

	public boolean canOutputPower(EnumFacing face);

	public boolean canInputPower(EnumFacing face);

	public void addBlockFace(EnumFacing face);

	public void removeBlockFace(EnumFacing face);

	public void clearBlockFace();

}
