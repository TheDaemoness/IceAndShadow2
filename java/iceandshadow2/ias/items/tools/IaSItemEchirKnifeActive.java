package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemEchirKnifeActive extends IaSBaseItemSingleGlow {

	public IaSItemEchirKnifeActive(String texName, int tab) {
		super(EnumIaSModule.IAS, texName);
		this.setMaxStackSize(32);
		this.setFull3D();
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString()
				+ EnumChatFormatting.ITALIC.toString()
				+ "Sneak and Use Item to finalize.");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer pirate) {
		if (pirate.isSneaking()) // Pirates can have subtlety too!
			par1 = new ItemStack(IaSTools.knife, par1.stackSize);
		return par1;
	}

}
