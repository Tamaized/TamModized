package Tamaized.TamModized.registry;

import java.util.ArrayList;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.particles.ParticlePacketHandlerRegistry;
import Tamaized.TamModized.particles.FX.network.ParticleFluffPacketHandler;

public class TamModizedParticles implements ITamRegistry {

	public static int fluff;

	@Override
	public void preInit() {
		fluff = ParticlePacketHandlerRegistry.register(new ParticleFluffPacketHandler());
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {

	}

	@Override
	public ArrayList<ITamModel> getModelList() {
		return new ArrayList<ITamModel>();
	}

	@Override
	public String getModID() {
		return TamModized.modid;
	}

	@Override
	public void clientPreInit() {

	}

	@Override
	public void clientInit() {

	}

	@Override
	public void clientPostInit() {

	}

}
