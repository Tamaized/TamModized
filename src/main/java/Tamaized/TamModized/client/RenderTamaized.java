package Tamaized.TamModized.client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderTamaized {

	private static final Random rand = new Random();

	@SubscribeEvent
	public void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		if (!Minecraft.getMinecraft().isGamePaused() && player != null && player.getName().equals("Tamaized") && rand.nextInt(4) == 0) {
			double dx = player.posX + ((rand.nextDouble() * 1) - 0.5D);
			double dz = player.posZ + ((rand.nextDouble() * 1) - 0.5D);
			Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(EnumParticleTypes.PORTAL.getParticleID(), dx, player.posY, dz, 0, 1, 0, null);
		}
	}

}
