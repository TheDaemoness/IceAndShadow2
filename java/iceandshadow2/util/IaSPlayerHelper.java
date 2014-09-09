package iceandshadow2.util;

import iceandshadow2.IaSFlags;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class IaSPlayerHelper {
	private static boolean dochat = true;
	
	public static void messagePlayer(EntityPlayer plai, String str) {
		if(dochat && plai.worldObj.isRemote && IaSFlags.flag_player_messages) {
			plai.addChatMessage(new ChatComponentText(str));
			dochat = false;
		}
		else
			dochat = true;
	}
	
	public static boolean giveItem(EntityPlayer plai, ItemStack is) {
		boolean added = plai.inventory.addItemStackToInventory(is);
		if(!added)
			plai.dropPlayerItemWithRandomChoice(is, false);
		return added;
	}
}
