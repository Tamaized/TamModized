package tamaized.tammodized.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.client.particles.ParticleFluff;
import tamaized.tammodized.common.handler.ContributorHandler;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TamModized.modid, value = Side.CLIENT)
public class ContributorParticles {

	private static final Random rand = new Random();

	@SubscribeEvent
	public static void tick(TickEvent.PlayerTickEvent e) {
		EntityPlayer player = e.player;
		if (!player.world.isRemote)
			return;
		if (!Minecraft.getMinecraft().isGamePaused() && rand.nextInt(20 * 3) == 0 && ContributorHandler.fluff.containsKey(player.getGameProfile().getId())) {
			double dx = player.posX + rand.nextDouble() - 0.5D;
			double dz = player.posZ + rand.nextDouble() - 0.5D;
			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleFluff(player.world, new Vec3d(dx, player.posY + player.getEyeHeight(), dz), new Vec3d(0, 0, 0), 20 * 2, 0.05f, rand.nextFloat() * 2.0F, ContributorHandler.fluff.get(player.getGameProfile().getId())));
		}
	}

}
