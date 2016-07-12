package Tamaized.TamModized.api.voidcraft.power;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidicPowerHandler {

	private VoidicPowerHandler() {
	}

	public static int recievePower(IVoidicPower input, int a) {
		int sendBack = 0;
		if (a > input.maxPowerTransfer()) {
			sendBack = a - input.maxPowerTransfer();
			a = input.maxPowerTransfer();
		}
		if (input.getPowerAmount() + a > input.getMaxPower()) {
			sendBack += a = input.getMaxPower() - input.getPowerAmount();
		}
		input.setPowerAmount(input.getPowerAmount() + a);
		return sendBack;
	}

	public static int sendPower(IVoidicPower input, int limit) {
		int amount = input.maxPowerTransfer();
		if (limit != -1 && input.maxPowerTransfer() > limit) amount = limit;
		if (input.getPowerAmount() - amount < 0) amount = input.getPowerAmount();
		input.setPowerAmount(input.getPowerAmount() - amount);
		return amount;
	}

	public static void sendToSurrounding(IVoidicPower source, World worldObj, BlockPos pos) {
		source.clearBlockFace();
		for (EnumFacing face : EnumFacing.VALUES) {
			TileEntity te = null;
			switch (face) {
				case UP: // block below but its face is up; Y-
					te = worldObj.getTileEntity(pos.add(0, -1, 0));
					break;
				case DOWN: // Y+
					te = worldObj.getTileEntity(pos.add(0, 1, 0));
					break;
				case NORTH: // Z+
					te = worldObj.getTileEntity(pos.add(0, 0, 1));
					break;
				case EAST: // X-
					te = worldObj.getTileEntity(pos.add(-1, 0, 0));
					break;
				case SOUTH: // Z-
					te = worldObj.getTileEntity(pos.add(0, 0, -1));
					break;
				case WEST: // X+
					te = worldObj.getTileEntity(pos.add(1, 0, 0));
					break;
				default:
					break;
			}
			if (te instanceof IVoidicPower) {
				IVoidicPower vp = (IVoidicPower) te;
				if (source.getPowerAmount() > 0 && vp.canInputPower(face)) {
					if (vp.getPowerAmount() < vp.getMaxPower() && vp.canInputPower(face)) {
						source.recievePower(vp.recievePower(source.sendPower(vp.maxPowerTransfer())));
						source.addBlockFace(face.getOpposite());
					}
				}
			}
		}
	}

}
