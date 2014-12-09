package iceandshadow2.nyx.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemCortraDust extends IaSBaseItemSingle {

	public NyxItemCortraDust(String texName) {
		super(EnumIaSModule.NYX, texName);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addInformation(ItemStack s, EntityPlayer p,
			List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString()+
					EnumChatFormatting.ITALIC.toString()+
					"It crackles with energy when you shake it.");
	}
}
