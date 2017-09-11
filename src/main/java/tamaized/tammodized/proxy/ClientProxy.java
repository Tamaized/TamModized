package tamaized.tammodized.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.client.entity.render.RenderDragonOld;
import tamaized.tammodized.common.entity.EntityDragonOld;
import tamaized.tammodized.network.ClientPacketHandler;

public class ClientProxy extends AbstractProxy {

	public ClientProxy() {
		super(Side.CLIENT);
	}

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonOld.class, RenderDragonOld::new);
	}

	@Override
	public void init() {

	}

	@Override
	public void postInit() {
		TamModized.channel.register(new ClientPacketHandler());
	}

}
