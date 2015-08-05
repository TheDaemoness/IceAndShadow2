package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSOnDeathDestroy;
import iceandshadow2.api.IIaSOnDeathKeep;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.NyxItemBoneSanctified;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class NyxFrostSwordHandler {

	@SubscribeEvent
	public void dmg(LivingHurtEvent e) {
		if(e.entityLiving.worldObj.isRemote)
			return;
		if(e.entityLiving instanceof EntityPlayer) {
			EntityPlayer pwai = (EntityPlayer)e.entityLiving;
			ItemStack is = pwai.getEquipmentInSlot(0);
			if(is != null && is.getItem() == NyxItems.frostSword && pwai.isUsingItem()) { //Probably redundant.
				if(e.source.isProjectile()) {
					if(!e.source.isDamageAbsolute()) {
						if(e.source.isFireDamage() && !e.source.isDamageAbsolute()) {
							pwai.extinguish();
							e.setCanceled(true);
						} else
							e.ammount=e.ammount/2;
					}
					pwai.getEquipmentInSlot(0).damageItem((int)(e.ammount+1), pwai);
				} else if (e.source.isMagicDamage() || e.source.isExplosion())
					return;
				else if (e.source instanceof EntityDamageSource) {
					if(!e.source.isDamageAbsolute()) {
						if(e.source.isFireDamage()) {
							pwai.extinguish();
							e.setCanceled(true);
						} else
							e.ammount=e.ammount/2;
					}
					Entity attacker = ((EntityDamageSource)e.source).getEntity();
					if(attacker instanceof EntityLivingBase) {
						int ulevel = ((NyxItemSwordFrost)NyxItems.frostSword).getUpgradeLevel(is);
						((EntityLivingBase)attacker).addPotionEffect(
								new PotionEffect(Potion.resistance.id,15,-(ulevel+1)));
					}
					pwai.attackTargetEntityWithCurrentItem(attacker);
					pwai.getEquipmentInSlot(0).damageItem((int)(e.ammount+1), pwai);
				}
			}
		}
	}
}
