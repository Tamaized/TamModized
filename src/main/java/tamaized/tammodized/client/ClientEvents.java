package tamaized.tammodized.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.tammodized.TamModized;

@Mod.EventBusSubscriber(modid = TamModized.modid, value = Side.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	public static void textureStitch(TextureStitchEvent e){
		AtlasSpriteList.fluff = e.getMap().registerSprite(new ResourceLocation(TamModized.modid, "particles/fluff"));
	}

}
