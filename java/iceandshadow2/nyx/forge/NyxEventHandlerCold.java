package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.util.IaSPlayerHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class NyxEventHandlerCold {
	
	public NyxEventHandlerCold() {
	}
	
	@SubscribeEvent
	public void onPlayerTriesToStartFires(PlayerInteractEvent e) {
		if(e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.entityPlayer.dimension == IaSFlags.dim_nyx_id && 
				!e.entityPlayer.capabilities.isCreativeMode) {
			boolean flaque = false;
			if(e.entityPlayer.getEquipmentInSlot(0) == null)
				return;
			
			Object id = e.entityPlayer.getEquipmentInSlot(0).getItem();
			if(id == Items.flint_and_steel)
				flaque = true;
			else if(id == Items.fire_charge)
				flaque = true;
			else if(id instanceof ItemBlock) {
				id = ((ItemBlock)id).field_150939_a;
				if(id == Blocks.fire)
					flaque = true;
				else if(id == Blocks.torch)
					flaque = true;
			}
			
			//DO NOT SIMPLIFY!
			if(flaque && !e.isCanceled()) {
				e.setCanceled(true);
				if(id != Blocks.torch)
					IaSPlayerHelper.messagePlayer(e.entityPlayer,
							"It's far too cold to start a fire that way in Nyx.");
				else
					IaSPlayerHelper.messagePlayer(e.entityPlayer,
							"It's far too cold to light a torch in Nyx.");
			}
		}
			
	}
	@SubscribeEvent
	public void onPlayerTriesToPlaceBucket(PlayerInteractEvent e) {
		if(e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.entityPlayer.dimension == IaSFlags.dim_nyx_id && 
				!e.entityPlayer.capabilities.isCreativeMode) {
			if(e.entityPlayer.getEquipmentInSlot(0) == null)
				return;
			Item itid = e.entityPlayer.getEquipmentInSlot(0).getItem();
			if(itid == Items.lava_bucket || itid == 
				Items.water_bucket) {
				e.getResult();
				e.useItem = Result.DENY;
				if(e.action != Action.RIGHT_CLICK_BLOCK)
					return;
				int x = e.x;
				int y = e.y;
				int z = e.z;
				switch(e.face) {
				case 0:
					--y;
					break;
				case 1:
					++y;
					break;
				case 2:
					--z;
					break;
				case 3:
					++z;
					break;
				case 4:
					--x;
					break;
				case 5:
					++x;
					break;
				default:
					break;
				}
				e.entityPlayer.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
				if(itid == Items.lava_bucket) {
					e.entityPlayer.worldObj.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F + (e.entityPlayer.worldObj.rand.nextFloat() - e.entityPlayer.worldObj.rand.nextFloat()) * 0.8F);
					e.entityPlayer.worldObj.setBlock(x, y, z, Blocks.obsidian,0,0x2);
				}
				else if(itid == Items.water_bucket)
					e.entityPlayer.worldObj.setBlock(x, y, z, Blocks.ice,0,0x2);
			}
		}
	}
	@SubscribeEvent
	public void onTryToPotion(PlayerInteractEvent e) {
		if(e.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR &&
			e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.entity.dimension == IaSFlags.dim_nyx_id && 
				!e.entityPlayer.capabilities.isCreativeMode) {
			ItemStack ite = e.entityPlayer.getEquipmentInSlot(0);
			if(ite == null)
				return;
			else if(ite.getItem() == Items.potionitem) {
				if(!e.isCanceled()) {
					e.setCanceled(true);
					IaSPlayerHelper.messagePlayer(e.entityPlayer,
						"The contents of the bottle have become thoroughly frozen.");
				}
			}
			else if(ite.getItem() == Items.milk_bucket) {
				if(!e.isCanceled()) {
					e.setCanceled(true);
					IaSPlayerHelper.messagePlayer(e.entityPlayer,
						"The milk has turned rather solid.");
				}
			}
		}
	}
	@SubscribeEvent
	public void onTryToPlant(PlayerInteractEvent e) {
		if(e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.entity.dimension == IaSFlags.dim_nyx_id && 
				!e.entityPlayer.capabilities.isCreativeMode) {
			boolean isplant = false;
			ItemStack ite = e.entityPlayer.getEquipmentInSlot(0);
			if(ite == null)
				return;
			if(ite.getItem() instanceof IPlantable)
				isplant = true;
			else if(ite.getItem() instanceof ItemBlock) {
				if(((ItemBlock)ite.getItem()).field_150939_a instanceof IPlantable)
					isplant = true;
			}
			
			//DO NOT SIMPLIFY!
			if(isplant && !e.isCanceled()) {
				e.setCanceled(true);
				IaSPlayerHelper.messagePlayer(e.entityPlayer,
						"There's no way that this will grow in this frigid climate.");
			}
		}
	}
	@SubscribeEvent
	public void onFireball(EntityJoinWorldEvent e) {
		if(e.entity == null)
			return;
		if(e.entity.dimension == IaSFlags.dim_nyx_id) {
			if(e.entity instanceof EntitySmallFireball || 
					e.entity instanceof EntityFireball || 
					e.entity instanceof EntityLargeFireball)
				e.setCanceled(true);
		}
	}
}
