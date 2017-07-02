package tamaized.tammodized.proxy;

import Tamaized.TamModized.TamModized;
import tamaized.tammodized.client.FloatyTextOverlay;
import tamaized.tammodized.client.RenderContributors;
import tamaized.tammodized.network.ClientPacketHandler;
import tamaized.tammodized.client.RenderPortalOverlay;
import tamaized.tammodized.common.entity.EntityDragonOld;
import tamaized.tammodized.client.entity.render.RenderDragonOld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

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
		MinecraftForge.EVENT_BUS.register(new RenderContributors());
		MinecraftForge.EVENT_BUS.register(new FloatyTextOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderPortalOverlay());
	}

	@Override
	public void postInit() {
		TamModized.channel.register(new ClientPacketHandler());
	}

}
