package tamaized.tammodized.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.registry.PortalHandlerRegistry;

@Mod.EventBusSubscriber(modid = TamModized.modid, value = Side.CLIENT)
public class RenderPortalOverlay {

	private static float tick = 0;
	private static TextureAtlasSprite texture = null;

	@SubscribeEvent
	public static void tick(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.player;
			if (mc.isGamePaused() || player == null || player.world == null)
				return;
			IBlockState state = player.world.getBlockState(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY - 0.2D - player.getYOffset()), MathHelper.floor(player.posZ)));
			if (PortalHandlerRegistry.contains(state)) {
				texture = mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
				if (tick < 100)
					tick += 1.5F;
				else
					tick = 100;
			} else {
				if (tick > 0)
					tick -= Math.min(2, tick);
				if (texture != null && tick <= 0)
					texture = null;
			}
		}
	}

	@SubscribeEvent
	public static void render(RenderGameOverlayEvent e) {
		if (e.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.player;
			if (player == null || player.world == null)
				return;
			if (texture == null)
				return;
			ScaledResolution scaledRes = new ScaledResolution(mc);
			GlStateManager.disableAlpha();
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(1.0F, 1.0F, 1.0F, tick / 100F);
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			float f = texture.getMinU();
			float f1 = texture.getMinV();
			float f2 = texture.getMaxU();
			float f3 = texture.getMaxV();

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder worldrenderer = tessellator.getBuffer();
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos(0.0D, (double) scaledRes.getScaledHeight(), -90.0D).tex((double) f, (double) f3).endVertex();
			worldrenderer.pos((double) scaledRes.getScaledWidth(), (double) scaledRes.getScaledHeight(), -90.0D).tex((double) f2, (double) f3).endVertex();
			worldrenderer.pos((double) scaledRes.getScaledWidth(), 0.0D, -90.0D).tex((double) f2, (double) f1).endVertex();
			worldrenderer.pos(0.0D, 0.0D, -90.0D).tex((double) f, (double) f1).endVertex();
			tessellator.draw();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.enableAlpha();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

}
