package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.ias.util.IaSPlayerHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBoneSanctified extends NyxBaseItemBone {

	public NyxItemBoneSanctified(String texName) {
		super(texName);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.PURE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(getTextureName());
		glow = itemIcon;
	}

	@Override
	public void onBoneDone(ItemStack is) {
		is.setItemDamage(15);
		is.func_150996_a(Items.dye);
	}

	@Override
	public void doEffect(Entity ent) {
		//Obligatory no-op.
	}
}
