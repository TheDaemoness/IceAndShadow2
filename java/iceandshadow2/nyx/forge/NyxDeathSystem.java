package iceandshadow2.nyx.forge;

import java.util.HashMap;

import iceandshadow2.IaSFlags;
import iceandshadow2.ias.interfaces.IIaSKeepOnDeath;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class NyxDeathSystem {

	public static InventoryPlayer plai_inv;
	public static HashMap<Integer, InventoryPlayer> death_inv;

	public NyxDeathSystem() {
		death_inv = new HashMap<Integer, InventoryPlayer>();
	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent e) {
		InventoryPlayer inv = new InventoryPlayer(e.player);
		if (e.player.isDead)
			inv.copyInventory(determineRespawnInventory(e.player.inventory,
					false));
		else
			inv.copyInventory(e.player.inventory);
		death_inv.put(e.player.getEntityId(), inv);

	}

	@SubscribeEvent
	public void onLogout(PlayerLoggedOutEvent e) {
		death_inv.remove(e.player.getEntityId());
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent e) {
		if (e.entity instanceof EntityPlayerMP) {
			EntityPlayerMP playNoMore = (EntityPlayerMP)e.entityLiving;
			plai_inv = new InventoryPlayer(playNoMore);
			plai_inv.copyInventory(playNoMore.inventory);
		}
	}

	@SubscribeEvent
	public void onDrop(PlayerDropsEvent e) {
		if (!e.entityPlayer.worldObj.isRemote && e.entityPlayer.dimension == IaSFlags.dim_nyx_id) {
			System.out.println(e.entityPlayer.getEntityId());
			e.setCanceled(true);
			boolean drop_main = true;
			plai_inv = determineRespawnInventory(plai_inv, true);
			e.entityPlayer.inventory.copyInventory(plai_inv);
			death_inv.put(e.entityPlayer.getEntityId(), e.entityPlayer.inventory);
		}
	}

	@SubscribeEvent
	public void onRespawn(PlayerEvent.Clone e) {
		if (!e.entityPlayer.worldObj.isRemote && e.entityPlayer.dimension == IaSFlags.dim_nyx_id) {
			if(!e.original.isDead)
				return;
			e.entityPlayer.inventory.copyInventory((InventoryPlayer)death_inv
					.get(e.original.getEntityId()));
			//Raise madness.
		}
	}

	public static InventoryPlayer determineRespawnInventory(
			InventoryPlayer plai_inv, boolean do_drop) {
		boolean drop_main = true;
		for (int i = 0; i < plai_inv.mainInventory.length; ++i) {
			if (plai_inv.mainInventory[i] != null) {
				ItemStack is = plai_inv.mainInventory[i];
				/*if (is.getItem() == NyxItems.charm;
						&& is.isItemDamaged())
					drop_main = false;*/
			}
		}
		if (drop_main) {
			for (int i = 0; i < plai_inv.mainInventory.length; ++i) {
				if (plai_inv.mainInventory[i] != null) {
					if(plai_inv.mainInventory[i].getItem() instanceof IIaSKeepOnDeath)
						continue;
					if (do_drop)
						plai_inv.player.dropPlayerItemWithRandomChoice(
								plai_inv.mainInventory[i], true);
					plai_inv.mainInventory[i] = null;
				}
			}
		}
		return plai_inv;
	}
}
