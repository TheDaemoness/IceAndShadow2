package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSDescriptive;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class NyxItemClockworkSmall extends IaSBaseItemSingleGlow implements IIaSDescriptive {
	
	static {
		IaSRegistry.blacklistUncraft(NyxItemClockworkSmall.class);
	}
	
	public NyxItemClockworkSmall(String id) {
		super(EnumIaSModule.NYX, id);
		setMaxStackSize(16);
		this.setHasSubtypes(false); //Defensive.
	}
	
	@Override
	public String getUnlocalizedDescription(EntityPlayer entityPlayer, ItemStack is) {
		return getModName()+(is.getItemDamage()>0?"Killed":"");
	}

	@Override
	public boolean isHintWarning(EntityPlayer entityPlayer, ItemStack itemStack) {
		return false;
	}

	@Override
	public String getUnlocalizedHint(EntityPlayer entityPlayer, ItemStack itemStack) {
		return "clockworkSmall";
	}
}
