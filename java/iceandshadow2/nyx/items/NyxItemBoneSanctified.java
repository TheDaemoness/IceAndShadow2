package iceandshadow2.nyx.items;

import iceandshadow2.ias.api.EnumIaSAspect;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBoneSanctified extends NyxBaseItemBone {

	public NyxItemBoneSanctified(String texName) {
		super(texName);
	}

	@Override
	public void doEffect(Entity ent) {
		// Obligatory no-op.
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.PURE;
	}

	@Override
	public void onBoneDone(ItemStack is) {
		is.setItemDamage(15);
		is.func_150996_a(Items.dye);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		glow = itemIcon;
	}
}
