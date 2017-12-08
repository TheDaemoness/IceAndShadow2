package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class NyxArmorMaterialSpiderSilk extends IaSArmorMaterial {
	
	public NyxArmorMaterialSpiderSilk() {
		super("NyxSpiderSilk", NyxItems.toughGossamer, 22, 2, 6, 4, 2, 12);
	}

	@Override
	public void onTick(EntityLivingBase wearer, double coverage, boolean major) {
		if(!major || wearer.getHealth()<wearer.getMaxHealth()-0.5 || wearer.isSprinting())
			return;
		wearer.addPotionEffect(new PotionEffect(Potion.invisibility.id,3,0));
	}

	@Override
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		if(dmg.getSourceOfDamage() != null)
			wearer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, (int)(50+18*coverage/2), 2));
		return super.onHurt(wearer, dmg, amount, coverage, major);
	}
	
	
}
