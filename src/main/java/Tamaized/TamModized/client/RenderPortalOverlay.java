package Tamaized.TamModized.client;

import Tamaized.TamModized.capabilities.CapabilityList;
import Tamaized.TamModized.capabilities.dimTracker.IDimensionCapability;
import Tamaized.TamModized.registry.PortalHandlerRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPortalOverlay {

	private float tick = 0;
	private TextureAtlasSprite texture = null;

	@SubscribeEvent
	public void render(RenderGameOverlayEvent e) {
		if (e.getType() == e.getType().PORTAL) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.player;
			if (player == null || player.world == null) return;
			IDimensionCapability cap = player.getCapability(CapabilityList.DIMENSION, null);
			if (cap == null) return;
			IBlockState state = player.world.getBlockState(new BlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY - 0.2D - (double) player.getYOffset()), MathHelper.floor(player.posZ)));
			if (state != null && PortalHandlerRegistry.contains(state)) {
				texture = mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
				if (tick < 0.8F) tick += 0.004F;
				else tick = 0.8F;
			} else {
				if (tick > 0) tick -= 0.005F;
				else {
					tick = 0;
				}
			}
			if (tick == 0) texture = null;
			if (texture == null) return;
			ScaledResolution scaledRes = new ScaledResolution(mc);
			GlStateManager.disableAlpha();
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(1.0F, 1.0F, 1.0F, tick);
			mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			float f = texture.getMinU();
			float f1 = texture.getMinV();
			float f2 = texture.getMaxU();
			float f3 = texture.getMaxV();

			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer worldrenderer = tessellator.getBuffer();
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
