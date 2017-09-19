package tamaized.tammodized.common.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import tamaized.tammodized.TamModized;
import tamaized.tammodized.client.FloatyTextOverlay;
import tamaized.tammodized.network.client.ClientPacketHandlerFloatyText;

public class FloatyTextHelper {

	public static void sendText(EntityPlayer player, String text) {
		if (player instanceof EntityPlayerMP)
			TamModized.network.sendTo(new ClientPacketHandlerFloatyText.Packet(text), (EntityPlayerMP) player);
		else
			FloatyTextOverlay.addFloatyText(text);
	}

}
