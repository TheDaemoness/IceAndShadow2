package iceandshadow2.util;

import iceandshadow2.IaSFlags;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

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
}
