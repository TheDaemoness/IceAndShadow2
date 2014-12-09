package iceandshadow2.nyx.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemLorePage extends IaSBaseItemMulti {

	@SideOnly(Side.CLIENT) IIcon[] pages;
	
	public NyxItemLorePage(String texName) {
		super(EnumIaSModule.NYX, texName, 1);
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if(dmg >= pages.length)
			return pages[0];
		return pages[dmg];
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		pages = new IIcon[this.getSubtypeCount()];
		for(int i = 0; i < pages.length; ++i)
			pages[i] = reg.registerIcon(this.getTexName()+i);
	}
}
