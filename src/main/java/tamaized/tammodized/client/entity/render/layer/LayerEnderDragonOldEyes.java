package tamaized.tammodized.client.entity.render.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.client.entity.render.RenderDragonOld;
import tamaized.tammodized.common.entity.EntityDragonOld;

public class LayerEnderDragonOldEyes implements LayerRenderer<EntityDragonOld> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(TamModized.modid, "textures/entity/dragon/dragon_eyes.png");
	private final RenderDragonOld dragonRenderer;

	public LayerEnderDragonOldEyes(RenderDragonOld dragonRendererIn) {
		this.dragonRenderer = dragonRendererIn;
	}

	@Override
	public void doRenderLayer(EntityDragonOld entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.dragonRenderer.bindTexture(TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
		GlStateManager.disableLighting();
		GlStateManager.depthFunc(514);
		int i = 61680;
		int j = 61680;
		int k = 0;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.dragonRenderer.getMainModel().render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.dragonRenderer.setLightmap(entitylivingbaseIn);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthFunc(515);
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}