package Tamaized.TamModized.registry;

import Tamaized.TamModized.particles.FX.network.ParticleFluffPacketHandler;
import Tamaized.TamModized.particles.ParticlePacketHandlerRegistry;

public class TamModizedParticles {

	public static int fluff;

	public static void register() {
		fluff = ParticlePacketHandlerRegistry.register(new ParticleFluffPacketHandler());
	}

}
