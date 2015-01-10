package iceandshadow2.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class IaSPlayerHelper {
	private static boolean dochat = true;
	
	public static void messagePlayer(EntityPlayer plai, String str) {
		if(dochat && plai.worldObj.isRemote) {
			ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GRAY));
			plai.addChatMessage(txt);
			dochat = false;
		}
		else
			dochat = true;
	}
	
	public static void alertPlayer(EntityPlayer plai, String str) {
		if(dochat && plai.worldObj.isRemote) {
			ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setBold(true).setColor(EnumChatFormatting.RED));
			plai.addChatMessage(txt);
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
