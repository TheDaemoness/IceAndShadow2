package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.items.tools.IaSArmorMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class NyxArmorMaterialNavistra extends IaSArmorMaterial {
	
	public NyxArmorMaterialNavistra() {
		super("Navistra", NyxItems.navistraShard, 11, 4, 9, 7, 3, 8);
	}
	
	@Override
	public void onTick(EntityLivingBase wearer, double coverage, boolean major) {
		wearer.addPotionEffect(new PotionEffect(Potion.jump.id, 3, major?-2:-1));
		wearer.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 3, major?-2:-1));
		super.onTick(wearer, coverage, major);
	}

	@Override
	public float onHurt(EntityLivingBase wearer, DamageSource dmg, float amount, double coverage, boolean major) {
		if(dmg.isProjectile())
			amount = (float)Math.max(0, amount-(coverage/(major?1:2)));
		return amount;
	}

	@Override
	public boolean isBreakable() {
		return false;
	}
}
