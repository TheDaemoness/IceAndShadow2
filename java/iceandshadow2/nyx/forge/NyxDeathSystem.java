package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSOnDeathRuin;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.items.NyxItemBoneSanctified;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class NyxDeathSystem {

	public static InventoryPlayer plai_inv;
	public static HashMap<Integer, InventoryPlayer> death_inv;

	public static InventoryPlayer determineRespawnInventory(InventoryPlayer plai_inv, boolean do_drop) {
		boolean drop_main = true;
		for (int i = 0; i < plai_inv.mainInventory.length; ++i)
			if (plai_inv.mainInventory[i] != null) {
				final ItemStack is = plai_inv.mainInventory[i];
				if (is.getItem() instanceof NyxItemBoneSanctified && is.isItemDamaged()) {
					drop_main = false;
				}
			}
		for (int i = 0; i < plai_inv.mainInventory.length; ++i)
			if (plai_inv.mainInventory[i] != null) {
				final Item it = plai_inv.mainInventory[i].getItem();
				if (it instanceof IIaSOnDeathRuin) {
					plai_inv.mainInventory[i] = null;
					continue;
				}
				final EnumRarity rarity = it.getRarity(plai_inv.mainInventory[i]);
				if (rarity == EnumRarity.uncommon || rarity == EnumRarity.epic) {
					plai_inv.player.dropPlayerItemWithRandomChoice(plai_inv.mainInventory[i], true);
					plai_inv.mainInventory[i] = null;
					continue;
				}
				if (rarity == EnumRarity.rare || it instanceof ItemBook) {
					if (i < 9) {
						continue;
					}
					plai_inv.player.dropPlayerItemWithRandomChoice(plai_inv.mainInventory[i], true);
					plai_inv.mainInventory[i] = null;
				}
				if (!drop_main) {
					continue;
				}
				if (i < 9)
					if (it == Items.arrow || it instanceof ItemSword || it instanceof ItemBow || it instanceof ItemTool) {
						continue;
					}
				plai_inv.player.dropPlayerItemWithRandomChoice(plai_inv.mainInventory[i], true);
				plai_inv.mainInventory[i] = null;
			}
		return plai_inv;
	}

	public NyxDeathSystem() {
		NyxDeathSystem.death_inv = new HashMap<Integer, InventoryPlayer>();
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		if (e.entity instanceof EntityPlayerMP) {
			final EntityPlayerMP playNoMore = (EntityPlayerMP) e.entityLiving;
			if (e.source != IaSDamageSources.dmgDrain) {
				IaSPlayerHelper.drainXP(playNoMore, playNoMore.experienceTotal / 2, null, true);
			}
			IaSEntityHelper.spawnNourishment(playNoMore, playNoMore.getFoodStats().getFoodLevel());
			NyxDeathSystem.plai_inv = new InventoryPlayer(playNoMore);
			NyxDeathSystem.plai_inv.copyInventory(playNoMore.inventory);
		}
	}

	@SubscribeEvent
	public void onDrop(PlayerDropsEvent e) {
		final boolean gr = e.entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory");
		if (!e.entityPlayer.worldObj.isRemote && e.entityPlayer.dimension == IaSFlags.dim_nyx_id && !gr) {
			e.setCanceled(true);
			NyxDeathSystem.plai_inv = NyxDeathSystem.determineRespawnInventory(NyxDeathSystem.plai_inv, true);
			e.entityPlayer.inventory.copyInventory(NyxDeathSystem.plai_inv);
			NyxDeathSystem.death_inv.put(e.entityPlayer.getEntityId(), e.entityPlayer.inventory);
		}
	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent e) {
		final InventoryPlayer inv = new InventoryPlayer(e.player);
		if (e.player.isDead) {
			inv.copyInventory(NyxDeathSystem.determineRespawnInventory(e.player.inventory, false));
		} else {
			inv.copyInventory(e.player.inventory);
		}
		NyxDeathSystem.death_inv.put(e.player.getEntityId(), inv);

	}

	@SubscribeEvent
	public void onLogout(PlayerLoggedOutEvent e) {
		NyxDeathSystem.death_inv.remove(e.player.getEntityId());
	}

	@SubscribeEvent
	public void onRespawn(PlayerEvent.Clone e) {
		final boolean gr = e.entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory");
		if (!e.entityPlayer.worldObj.isRemote && e.entityPlayer.dimension == IaSFlags.dim_nyx_id && !gr) {
			if (!e.original.isDead)
				return;
			if (NyxDeathSystem.death_inv.get(e.original.getEntityId()) != null)
			 {
				e.entityPlayer.inventory.copyInventory(NyxDeathSystem.death_inv.get(e.original.getEntityId()));
			// Raise madness.
			}
		}
	}
}
