package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemLorePage extends IaSBaseItemMulti {

	@SideOnly(Side.CLIENT)
	IIcon[] pages;

	public NyxItemLorePage(String texName) {
		super(EnumIaSModule.NYX, texName, 1);
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg >= this.pages.length)
			return this.pages[0];
		return this.pages[dmg];
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.pages = new IIcon[getSubtypeCount()];
		for (int i = 0; i < this.pages.length; ++i)
			this.pages[i] = reg.registerIcon(getTexName() + i);
	}
}
