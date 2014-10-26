package iceandshadow2.ias.items.tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.util.IaSPlayerHelper;

public class IaSItemEchirArmorActive extends IaSBaseItemSingleGlow {
 
	protected int slot;
	
	public IaSItemEchirArmorActive(String texName, int slut) { //Wat?
		super(EnumIaSModule.IAS, texName);
		slot = slut; //Oh...
		this.setMaxStackSize(1);
	}

	@Override
	public int getMaxDamage() {
		return IaSTools.armorEchir[slot].getMaxDamage();
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p,
			List l, boolean b) {
		l.add(	EnumChatFormatting.GRAY.toString()+
				EnumChatFormatting.ITALIC.toString()+
				"Sneak and Use Item to finalize.");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer hulk) {
		if(hulk.isSneaking()) //Does not always evaluate to false, see that one jumpscare in The Avengers.
			par1 = new ItemStack(IaSTools.armorEchir[slot],1,par1.getItemDamage());
		else
			IaSPlayerHelper.messagePlayer(hulk, "It's probably not safe to wear this while it's primed.");
		return par1;
	}
}
