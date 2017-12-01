package iceandshadow2.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class IaSPlayerHelper {
	private static boolean dochat = true;

	public static void alertPlayer(EntityPlayer plai, String str) {
		if (IaSPlayerHelper.dochat && plai.worldObj.isRemote) {
			final ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setBold(true).setColor(EnumChatFormatting.RED));
			plai.addChatMessage(txt);
			IaSPlayerHelper.dochat = false;
		} else
			IaSPlayerHelper.dochat = true;
	}

	public static boolean giveItem(EntityPlayer plai, ItemStack is) {
		final boolean added = plai.inventory.addItemStackToInventory(is);
		if (!added && !plai.worldObj.isRemote) {
			final EntityItem item = new EntityItem(plai.worldObj, plai.posX, plai.posY + plai.getEyeHeight() / 2.0,
					plai.posZ, is);
			plai.worldObj.spawnEntityInWorld(item);
		}
		return added;
	}

	public static void messagePlayer(EntityPlayer plai, String str) {
		if (IaSPlayerHelper.dochat && plai.worldObj.isRemote) {
			final ChatComponentText txt = new ChatComponentText(str);
			txt.setChatStyle(new ChatStyle().setItalic(true).setColor(EnumChatFormatting.GRAY));
			plai.addChatMessage(txt);
			IaSPlayerHelper.dochat = false;
		} else
			IaSPlayerHelper.dochat = true;
	}
}
