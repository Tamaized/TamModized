package tamaized.tammodized.common.particles;

import tamaized.tammodized.TamModized;

import java.util.ArrayList;


public class ParticlePacketHandlerRegistry {

	private static ArrayList<ParticlePacketBase> registry = new ArrayList<ParticlePacketBase>();

	public static int register(ParticlePacketBase handler) {
		if (registry.contains(handler)) {
			TamModized.instance.logger.info("Attempted to register an already registered ParticlePacketHandler: " + handler);
		} else {
			registry.add(handler);
		}
		return getID(handler);
	}

	public static int getID(ParticlePacketBase handler) {
		if (registry.contains(handler)) return registry.indexOf(handler);
		else return -1;
	}

	public static ParticlePacketBase getHandler(int id) {
		return registry.get(id);
	}

}
