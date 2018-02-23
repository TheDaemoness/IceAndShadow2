package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.api.IIaSPassiveEffectItem;
import iceandshadow2.ias.util.ArmorMaterialInstance;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.NyxItemBoneCursed;
import iceandshadow2.nyx.items.NyxItemBoneSanctified;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import java.util.Map;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class NyxEquipmentHandler {

	public void doDrainEnchantments(EntityPlayer victim, float amount) {
		if (victim.dimension != IaSFlags.dim_nyx_id)
			return;
		int severity = ((int) amount - victim.worldObj.rand.nextInt(2 + victim.experienceLevel)) / 2;
		if (severity <= 0)
			return;
		final Map[] enchmaps = new Map[5];
		for (int i = 0; i < 5; ++i)
			if (victim.getEquipmentInSlot(i) != null) {
				enchmaps[i] = EnchantmentHelper.getEnchantments(victim.getEquipmentInSlot(i));
			}
		while (severity > 0) {
			for (int i = 0; i < 5; ++i) {
				if (enchmaps[i] == null) {
					continue;
				}
				if (i != 0 && victim.worldObj.rand.nextInt(2 + severity) >= severity - 1) {
					continue;
				}
				final Object[] enchkeys = enchmaps[i].keySet().toArray();
				if (enchkeys.length == 0) {
					continue;
				}
				final Integer selected = (Integer) enchkeys[victim.worldObj.rand.nextInt(enchkeys.length)];
				final Integer strength = (Integer) enchmaps[i].get(selected);
				if (strength.intValue() <= 1) {
					enchmaps[i].remove(selected);
				} else {
					enchmaps[i].put(selected, Integer.valueOf(strength.intValue() - 1));
				}
			}
			--severity;
		}
		for (int i = 0; i < 5; ++i)
			if (enchmaps[i] != null) {
				EnchantmentHelper.setEnchantments(enchmaps[i], victim.getEquipmentInSlot(i));
			}
	}
	
	public boolean hasActiveCursedBone(InventoryPlayer plai_inv) {
		for (int i = 0; i < plai_inv.mainInventory.length; ++i)
			if (plai_inv.mainInventory[i] != null) {
				final ItemStack is = plai_inv.mainInventory[i];
				if (is.getItem() instanceof NyxItemBoneCursed && is.isItemDamaged()) {
					return true;
				}
			}
		return false;
	}

	@SubscribeEvent
	public void handleArmor(LivingHurtEvent e) {
		if ((e.entityLiving instanceof EntityPlayer)) {
			final EntityPlayer victim = (EntityPlayer) e.entityLiving;
			final boolean isDrain = e.source == IaSDamageSources.dmgDrain;
			if (!isDrain)
				if (victim.inventory.hasItem(NyxItems.bloodstone)) {
					IaSPlayerHelper.drainXP(victim, (int) (1 + e.ammount), null, true);
				}
			if (e.source.isMagicDamage()) {
				doDrainEnchantments(victim, e.ammount*(isDrain?4:0));
			} else if(!e.source.isDamageAbsolute() && hasActiveCursedBone(victim.inventory)) {
				e.ammount = 0;
				e.setResult(Result.DENY);
				return;
			}
		}
		final EntityLivingBase elb = e.entityLiving;
		if (elb == null)
			return;

		final ArmorMaterialInstance[] materialMap = ArmorMaterialInstance.getEquipmentData(elb);
		boolean major = true;
		for (final ArmorMaterialInstance mat : materialMap) {
			if (mat.material == null) {
				break;
			}
			final float neo = mat.material.onHurt(elb, e.source, e.ammount, mat.coverage,
					major && mat.coverage >= 4.125);
			if (!e.source.isDamageAbsolute()) {
				e.ammount = neo;
			}
			major = false;
		}
		if (e.ammount <= 0) {
			e.ammount = 0;
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void handleFrostSword(LivingHurtEvent e) {
		if (e.entityLiving.worldObj.isRemote)
			return;
		if (e.entityLiving instanceof EntityPlayer) {
			final EntityPlayer pwai = (EntityPlayer) e.entityLiving;
			final ItemStack is = pwai.getEquipmentInSlot(0);
			if (is != null && is.getItem() == NyxItems.frostSword && pwai.isUsingItem())
				// redundant.
				if (e.source.isProjectile()) {
				if (!e.source.isDamageAbsolute())
				if (e.source.isFireDamage() && !e.source.isDamageAbsolute()) {
				pwai.extinguish();
				e.setCanceled(true);
				} else {
					e.ammount = e.ammount / 2;
				}
				is.damageItem((int) (e.ammount + 1), pwai);
				} else if (e.source.isMagicDamage() || e.source.isExplosion())
				return;
				else if (e.source instanceof EntityDamageSource) {
				if (!e.source.isDamageAbsolute())
				if (e.source.isFireDamage()) {
				pwai.extinguish();
				e.setCanceled(true);
				} else {
					e.ammount = e.ammount / 2;
				}
				final Entity attacker = ((EntityDamageSource) e.source).getEntity();
				if (attacker instanceof EntityLivingBase) {
				final int ulevel = ((NyxItemSwordFrost) NyxItems.frostSword).getUpgradeLevel(is);
				((EntityLivingBase) attacker).addPotionEffect(new PotionEffect(Potion.resistance.id, 15, -(ulevel + 1)));
				}
				pwai.attackTargetEntityWithCurrentItem(attacker);
				is.damageItem((int) (e.ammount + 1), pwai);
				}
		}
	}

	@SubscribeEvent
	public void handleItemTick(LivingUpdateEvent e) {
		if (e.entityLiving instanceof EntityPlayer) {
			final EntityPlayer owner = (EntityPlayer) e.entityLiving;
			for (int i = 0; i < owner.inventory.mainInventory.length; ++i) {
				final ItemStack is = owner.inventory.mainInventory[i];
				if (is != null && is.getItem() instanceof IIaSPassiveEffectItem) {
					((IIaSPassiveEffectItem) is.getItem()).onItemUpdateTick(e.entityLiving, is,
							i == owner.inventory.currentItem);
				}
			}
		} else {
			final ItemStack held = e.entityLiving.getEquipmentInSlot(0);
			if (held != null && held.getItem() instanceof IIaSPassiveEffectItem) {
				((IIaSPassiveEffectItem) held.getItem()).onItemUpdateTick(e.entityLiving, held, true);
			}
		}
		final ArmorMaterialInstance[] materialMap = ArmorMaterialInstance.getEquipmentData(e.entityLiving);
		boolean major = true;
		for (final ArmorMaterialInstance mat : materialMap) {
			if (mat.material == null) {
				break;
			}
			mat.material.onTick(e.entityLiving, mat.coverage, major && mat.coverage >= 4.125);
			major = false;
		}
	}
}
