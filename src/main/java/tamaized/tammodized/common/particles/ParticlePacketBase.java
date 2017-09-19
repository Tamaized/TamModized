package tamaized.tammodized.common.particles;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.tammodized.common.particles.ParticleHelper.IParticlePacketData;

public abstract class ParticlePacketBase {

	public abstract void encode(ByteBuf packet, IParticlePacketData data);

	@SideOnly(Side.CLIENT)
	public abstract TamParticle decode(ByteBuf packet, WorldClient world, Vec3d pos);

}
