package iceandshadow2.ias.items;

import iceandshadow2.EnumIaSModule;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IaSBaseItemMultiTextured extends IaSBaseItemMulti {

	@SideOnly(Side.CLIENT)
	protected IIcon[] altIcons;

	public IaSBaseItemMultiTextured(EnumIaSModule mod, String id, int subtypes) {
		super(mod, id, subtypes);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 0 || dmg >= getSubtypeCount())
			return itemIcon;
		return altIcons[dmg-1];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName()+"0");
		if(this.getSubtypeCount() > 1) {
		altIcons = new IIcon[this.getSubtypeCount()-1];
			for(int i = 1; i < getSubtypeCount(); ++i) {
				altIcons[i-1] = reg.registerIcon(getTextureName()+Integer.toString(i));
			}
		}
	}
}
