package iceandshadow2.nyx.forge;

import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Map;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class NyxEquipmentHandler {
	
	@SubscribeEvent
	public void drainEnchantments(LivingHurtEvent e) {
		if(!(e.entityLiving instanceof EntityPlayer))
			return;
		if(!e.source.isMagicDamage())
			return;
		EntityPlayer victim = (EntityPlayer)e.entityLiving;
		int severity = ((int)e.ammount-victim.worldObj.rand.nextInt(2+victim.experienceLevel))/2;
		if(severity <= 0)
			return;
		Map[] enchmaps = new Map[5];
		for(int i = 0; i < 5; ++i) {
			if(victim.getEquipmentInSlot(i) != null)
				enchmaps[i] = EnchantmentHelper.getEnchantments(victim.getEquipmentInSlot(i));
		}
		while(severity > 0) {
			for(int i = 0; i < 5; ++i) {
				if(enchmaps[i] == null)
					continue;
				if(i != 0 && victim.worldObj.rand.nextInt(2+severity) >= severity-1)
					continue;
				Object[] enchkeys = enchmaps[i].keySet().toArray();
				if(enchkeys.length == 0)
					continue;
				Integer selected = (Integer)enchkeys[victim.worldObj.rand.nextInt(enchkeys.length)];
				Integer strength = (Integer)enchmaps[i].get(selected);
				if(strength.intValue() <= 1) { //More paranoia.
					enchmaps[i].remove(selected);
				} else
					enchmaps[i].put(selected, Integer.valueOf(strength.intValue()-1));
			}
			--severity;
		}
		for(int i = 0; i < 5; ++i) {
			if(enchmaps[i] != null)
				EnchantmentHelper.setEnchantments(enchmaps[i], victim.getEquipmentInSlot(i));
		}
	}

	@SubscribeEvent
	public void handleGeneral(LivingHurtEvent e) {
		if (e.entity.worldObj.isRemote)
			return;
		final EntityLivingBase elb = e.entityLiving;
		if (elb == null)
			return;
		if (elb instanceof EntityPlayer && e.source != IaSDamageSources.dmgDrain) {
			if (((EntityPlayer) elb).inventory.hasItem(NyxItems.bloodstone))
				IaSPlayerHelper.drainXP(((EntityPlayer) elb), (int) (1 + e.ammount), null, true);
		}
		if (e.source.isDamageAbsolute())
			return;
		if (e.source.getSourceOfDamage() == null && !e.source.isMagicDamage())
			return;
		int protection = 0;
		if (elb.getEquipmentInSlot(1) != null && elb.getEquipmentInSlot(1).getItem() == IaSTools.armorSpiderSilk[3]) {
			protection += 1;
			elb.getEquipmentInSlot(1).damageItem(1, elb);
		}
		if (elb.getEquipmentInSlot(2) != null && elb.getEquipmentInSlot(2).getItem() == IaSTools.armorSpiderSilk[2]) {
			protection += 3;
			elb.getEquipmentInSlot(2).damageItem(3, elb);
		}
		if (elb.getEquipmentInSlot(3) != null && elb.getEquipmentInSlot(3).getItem() == IaSTools.armorSpiderSilk[1]) {
			protection += 2;
			elb.getEquipmentInSlot(3).damageItem(2, elb);
		}
		if (elb.getEquipmentInSlot(4) != null && elb.getEquipmentInSlot(4).getItem() == IaSTools.armorSpiderSilk[0]) {
			protection += 2;
			elb.getEquipmentInSlot(4).damageItem(1, elb);
		}
		if (e.source.isMagicDamage())
			e.ammount = Math.max(1, e.ammount - protection);
		if (protection > 0)
			elb.addPotionEffect(new PotionEffect(Potion.invisibility.id, 5 + protection * 10));
	}
	
	@SubscribeEvent
	public void handleFrostSword(LivingHurtEvent e) {
		if (e.entityLiving.worldObj.isRemote)
			return;
		if (e.entityLiving instanceof EntityPlayer) {
			final EntityPlayer pwai = (EntityPlayer) e.entityLiving;
			final ItemStack is = pwai.getEquipmentInSlot(0);
			if (is != null && is.getItem() == NyxItems.frostSword && pwai.isUsingItem()) { // Probably
																							// redundant.
				if (e.source.isProjectile()) {
					if (!e.source.isDamageAbsolute()) {
						if (e.source.isFireDamage() && !e.source.isDamageAbsolute()) {
							pwai.extinguish();
							e.setCanceled(true);
						} else
							e.ammount = e.ammount / 2;
					}
					is.damageItem((int) (e.ammount + 1), pwai);
				} else if (e.source.isMagicDamage() || e.source.isExplosion())
					return;
				else if (e.source instanceof EntityDamageSource) {
					if (!e.source.isDamageAbsolute()) {
						if (e.source.isFireDamage()) {
							pwai.extinguish();
							e.setCanceled(true);
						} else
							e.ammount = e.ammount / 2;
					}
					final Entity attacker = ((EntityDamageSource) e.source).getEntity();
					if (attacker instanceof EntityLivingBase) {
						final int ulevel = ((NyxItemSwordFrost) NyxItems.frostSword).getUpgradeLevel(is);
						((EntityLivingBase) attacker)
								.addPotionEffect(new PotionEffect(Potion.resistance.id, 15, -(ulevel + 1)));
					}
					pwai.attackTargetEntityWithCurrentItem(attacker);
					is.damageItem((int) (e.ammount + 1), pwai);
				}
			}
		}
	}
}
