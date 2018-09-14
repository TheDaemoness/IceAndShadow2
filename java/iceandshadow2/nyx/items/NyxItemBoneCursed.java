package iceandshadow2.nyx.items;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NyxItemBoneCursed extends NyxBaseItemBone {

	public NyxItemBoneCursed(String texName) {
		super(texName);
		setMaxDamage(180);
	}

	@Override
	public void doEffect(Entity ent) {
		if (ent instanceof EntityLivingBase) {
			final EntityLivingBase elb = (EntityLivingBase) ent;
			elb.addPotionEffect(new PotionEffect(Potion.blindness.id, 50, 0));
			elb.addPotionEffect(new PotionEffect(Potion.invisibility.id, 30, 0));
			if (elb instanceof EntityPlayer) {
				final EntityPlayer ep = (EntityPlayer) elb;
				ep.getFoodStats().addExhaustion(0.25f);
			}
		}
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}

	@Override
	public void onBoneDone(ItemStack is) {
		is.setItemDamage(0);
		is.func_150996_a(NyxItems.cursedPowder);
	}
}
