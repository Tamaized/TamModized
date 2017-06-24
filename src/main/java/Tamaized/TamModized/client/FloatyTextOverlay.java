package Tamaized.TamModized.client;

import java.util.ArrayList;

import Tamaized.TamModized.helper.TranslateHelper;
import Tamaized.TamModized.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FloatyTextOverlay extends Gui {

	private Minecraft mc = Minecraft.getMinecraft();

	private static ArrayList<String> textSpooler = new ArrayList<String>();
	private static volatile FloatyText[] floatyText = new FloatyText[11];

	public static void addFloatyText(String s) {
		textSpooler.add(TranslateHelper.translate(s));
	}

	@SubscribeEvent
	public void update(ClientTickEvent e) {
		if (!mc.isGamePaused()) {
			for (int i = 5; i >= 0; i--) {
				if (floatyText[i] == null) continue;
				floatyText[i].pos += 1;
				if (floatyText[i].pos % 8 == 0) {
					FloatyText newText = floatyText[i];
					floatyText[i] = null;
					if (i != 5) floatyText[i + 1] = newText;
				}
			}
			if (!textSpooler.isEmpty() && floatyText[0] == null) {
				floatyText[0] = new FloatyText(textSpooler.get(0));
				textSpooler.remove(0);
			}
		}
	}

	@SubscribeEvent
	public void render(RenderGameOverlayEvent e) {
		if (e.isCancelable() || e.getType() != e.getType().EXPERIENCE) return;
		FontRenderer fontRender = mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc);
		int sW = sr.getScaledWidth() / 2;

		GlStateManager.pushMatrix();
		{
			GlStateManager.scale(0.5f, 0.5f, 0f);

			for (int i = 0; i <= 5; i++) {
				FloatyText ft = floatyText[i];
				if (ft == null) continue;
				float perc = 255 - ((float) i / 10) * 255;
				fontRender.drawStringWithShadow(ft.text, (sW * 4) - 230, -5 + ft.pos, 0xFFFF00);
			}

		}
		GlStateManager.popMatrix();

	}

	private class FloatyText {

		public final String text;
		public int pos = 0;

		public FloatyText(String s) {
			text = s;
		}
	}

}
