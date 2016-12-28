package Tamaized.TamModized.proxy;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.client.RenderTamaized;
import Tamaized.TamModized.network.ClientPacketHandler;
import Tamaized.TamModized.registry.TamModelResourceHandler;
import Tamaized.TamModized.registry.TamRegistryHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends AbstractProxy {

	@Override
	public void preInit() {
		super.preInit();
	}

	@Override
	public void init() {
		super.init();
		TamModelResourceHandler.instance.compile();
		MinecraftForge.EVENT_BUS.register(new RenderTamaized());
	}

	@Override
	public void postInit() {
		super.postInit();
		TamModized.channel.register(new ClientPacketHandler());
	}

}
