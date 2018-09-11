package tamaized.tammodized.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenClose extends GuiScreen {

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		if (keyCode == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			if (mc.player == null)
				Minecraft.getMinecraft().displayGuiScreen(null);
			else
				this.mc.player.closeScreen();
		}
	}

}