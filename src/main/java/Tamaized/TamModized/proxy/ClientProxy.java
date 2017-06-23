package Tamaized.TamModized.proxy;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.client.FloatyTextOverlay;
import Tamaized.TamModized.client.RenderContributors;
import Tamaized.TamModized.client.RenderPortalOverlay;
import Tamaized.TamModized.entity.dragon.EntityDragonOld;
import Tamaized.TamModized.entity.dragon.render.RenderDragonOld;
import Tamaized.TamModized.network.ClientPacketHandler;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
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
