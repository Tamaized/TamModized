package Tamaized.TamModized.proxy;

import Tamaized.TamModized.TamModized;
import Tamaized.TamModized.client.RenderContributors;
import Tamaized.TamModized.network.ClientPacketHandler;
import Tamaized.TamModized.registry.TamModelResourceHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends AbstractProxy {

	public ClientProxy() {
		super(Side.CLIENT);
	}

	@Override
	public void preRegisters() {

	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
		TamModelResourceHandler.instance.compile();
		MinecraftForge.EVENT_BUS.register(new RenderContributors());
	}

	@Override
	public void postInit() {
		TamModized.channel.register(new ClientPacketHandler());
	}

}
