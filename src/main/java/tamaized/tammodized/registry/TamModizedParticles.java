package tamaized.tammodized.registry;

import tamaized.tammodized.common.particles.ParticlePacketHandlerRegistry;
import tamaized.tammodized.common.particles.network.ParticleFluffPacketHandler;

public class TamModizedParticles {

	public static int fluff;

	public static void register() {
		fluff = ParticlePacketHandlerRegistry.register(new ParticleFluffPacketHandler());
	}

}
