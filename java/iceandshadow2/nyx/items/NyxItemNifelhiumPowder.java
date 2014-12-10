package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class NyxItemNifelhiumPowder extends IaSBaseItemSingleGlow {

	public NyxItemNifelhiumPowder(String texName) {
		super(EnumIaSModule.NYX, texName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p,
			List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString()+
					EnumChatFormatting.ITALIC.toString()+
					"Cold air flows from it like an amplifying energy.");
	}
}
