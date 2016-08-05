package Tamaized.TamModized.particles;

import io.netty.buffer.ByteBufInputStream;

import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.Vec3d;
import Tamaized.TamModized.particles.ParticleHelper.IParticlePacketData;

public abstract class ParticlePacketBase {

	public abstract void encode(DataOutputStream packet, IParticlePacketData data) throws IOException;

	public abstract TamParticle decode(ByteBufInputStream packet, WorldClient world, Vec3d pos);

}
