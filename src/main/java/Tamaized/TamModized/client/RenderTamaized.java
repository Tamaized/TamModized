package Tamaized.TamModized.client;

import java.util.Random;

import Tamaized.TamModized.particles.FX.ParticleFluff;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderTamaized {

	private static final Random rand = new Random();

	@SubscribeEvent
	public void render(RenderPlayerEvent.Post e) {
		EntityPlayer player = e.getEntityPlayer();
		if (!Minecraft.getMinecraft().isGamePaused() && player != null && player.getName().equals("Tamaized") && rand.nextInt(20 * 3) == 0) {
			double dx = player.posX + ((rand.nextDouble() * 1) - 0.5D);
			double dz = player.posZ + ((rand.nextDouble() * 1) - 0.5D);
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFluff(player.world, new Vec3d(dx, player.posY + player.getEyeHeight(), dz), new Vec3d(0, 0, 0), 20 * 2, 0.05f, (rand.nextFloat()) * 2.0F, 0xFFCCF6FA));//0xFFA4EAFF
		}
	}

}
