package iceandshadow2.nyx.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemCrystalVial extends IaSBaseItemMulti {
	
	@SideOnly(Side.CLIENT)
	protected IIcon extractor;
	
	public NyxItemCrystalVial(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return extractor;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getTexName());
		this.extractor = reg.registerIcon(this.getTexName() + "Extractor");
	}
}
