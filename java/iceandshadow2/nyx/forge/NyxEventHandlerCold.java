package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.IaSPlayerHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class NyxEventHandlerCold {

	public NyxEventHandlerCold() {
	}

	@SubscribeEvent
	public void onBlockPlaced(PlaceEvent e) {
		if (e.player.dimension == IaSFlags.dim_nyx_id)
			if (e.block == Blocks.furnace || e.block == Blocks.lit_furnace) {
				e.setCanceled(true);
				IaSPlayerHelper.messagePlayer(e.player, "There's no point in placing one of those here.");
			}
	}

	@SubscribeEvent
	public void onFireball(EntityJoinWorldEvent e) {
		if (e.entity == null)
			return;
		if (e.entity.dimension == IaSFlags.dim_nyx_id)
			if (e.entity instanceof EntitySmallFireball || e.entity instanceof EntityFireball
					|| e.entity instanceof EntityLargeFireball)
				e.setCanceled(true);
	}

	@SubscribeEvent
	public void onPlayerTriesToPlaceBucket(PlayerInteractEvent e) {
		if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.entityPlayer.dimension != IaSFlags.dim_nyx_id || !e.entityPlayer.capabilities.isCreativeMode)
			return;
		if (e.entityPlayer.getEquipmentInSlot(0) == null)
			return;
		final Item itid = e.entityPlayer.getEquipmentInSlot(0).getItem();
		if (itid == Items.lava_bucket || itid == Items.water_bucket) {
			e.getResult();
			if (e.action != Action.RIGHT_CLICK_BLOCK)
				return;
			int x = e.x;
			int y = e.y;
			int z = e.z;
			switch (e.face) {
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
			e.useItem = Result.DENY;
			e.entityPlayer.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
			if (itid == Items.lava_bucket) {
				e.entityPlayer.worldObj.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, "random.fizz", 0.5F, 2.6F
						+ (e.entityPlayer.worldObj.rand.nextFloat() - e.entityPlayer.worldObj.rand.nextFloat()) * 0.8F);
				e.entityPlayer.worldObj.setBlock(x, y, z, Blocks.obsidian, 0, 0x2);
			} else if (itid == Items.water_bucket)
				e.entityPlayer.worldObj.setBlock(x, y, z, Blocks.ice, 0, 0x2);
		}
	}

	@SubscribeEvent
	public void onPlayerTriesToStartFires(PlayerInteractEvent e) {
		if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.entityPlayer.dimension == IaSFlags.dim_nyx_id && !e.entityPlayer.capabilities.isCreativeMode) {

			boolean flaque = false;
			if (e.entityPlayer.getEquipmentInSlot(0) == null)
				return;

			Object id = e.entityPlayer.getEquipmentInSlot(0).getItem();
			if (id == Items.flint_and_steel)
				flaque = true;
			else if (id == Items.fire_charge)
				flaque = true;
			else if (id instanceof ItemBlock) {
				id = ((ItemBlock) id).field_150939_a;
				if (id == Blocks.fire)
					flaque = true;
				else if (id == Blocks.torch)
					flaque = true;
			}

			// DO NOT SIMPLIFY!
			if (flaque && !e.isCanceled()) {
				e.setCanceled(true);
				if (id == Blocks.torch)
					IaSPlayerHelper.messagePlayer(e.entityPlayer, "It's far too cold to light a torch in Nyx.");
				else
					IaSPlayerHelper.messagePlayer(e.entityPlayer, "It's far too cold to start a fire that way in Nyx.");
			}
		}

	}

	@SubscribeEvent
	public void onPlayerTriesToUseFurnace(PlayerInteractEvent e) {
		if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.entityPlayer.dimension == IaSFlags.dim_nyx_id && !e.entityPlayer.capabilities.isCreativeMode) {
			final Block bl = e.world.getBlock(e.x, e.y, e.z);
			if (bl instanceof BlockFurnace || e.world.getTileEntity(e.x, e.y, e.x) instanceof TileEntityFurnace) {
				e.setCanceled(true);
				IaSPlayerHelper.messagePlayer(e.entityPlayer,
						"It's too cold to light that furnace here. Find another way to smelt.");
			}
		}
	}

	@SubscribeEvent
	public void onTryToPlant(PlayerInteractEvent e) {
		if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.entity.dimension == IaSFlags.dim_nyx_id && !e.entityPlayer.capabilities.isCreativeMode) {
			boolean isplant = false;
			final ItemStack ite = e.entityPlayer.getEquipmentInSlot(0);
			if (ite == null)
				return;
			if (ite.getItem() instanceof IPlantable)
				isplant = true;
			else if (ite.getItem() instanceof ItemBlock)
				if (((ItemBlock) ite.getItem()).field_150939_a instanceof IPlantable)
					isplant = true;

			// DO NOT SIMPLIFY!
			if (isplant && !e.isCanceled()) {
				final boolean dirt = e.world.getBlock(e.x, e.y, e.z) == NyxBlocks.dirt;
				ForgeDirection.getOrientation(e.face);
				e.setCanceled(!dirt);
				if (!dirt)
					IaSPlayerHelper.messagePlayer(e.entityPlayer, "There's no way that this will grow in this realm.");
			}
		}
	}

	@SubscribeEvent
	public void onTryToPotion(PlayerInteractEvent e) {
		if (e.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR
				&& e.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;
		if (e.entityPlayer.isInsideOfMaterial(Material.fire))
			return;
		if (e.entity.dimension == IaSFlags.dim_nyx_id && !e.entityPlayer.capabilities.isCreativeMode) {
			final ItemStack ite = e.entityPlayer.getEquipmentInSlot(0);
			if (ite == null)
				return;
			else if (ite.getItem() instanceof ItemPotion) {
				if (!e.isCanceled()) {
					e.setCanceled(true);
					IaSPlayerHelper.messagePlayer(e.entityPlayer, "The contents of the bottle have frozen solid.");
				}
			} else if (ite.getItem() == Items.milk_bucket) {
				if (!e.isCanceled()) {
					e.setCanceled(true);
					IaSPlayerHelper.messagePlayer(e.entityPlayer, "The milk has frozen solid.");
				}
			} else if (ite.getItem() instanceof ItemFood && !(ite.getItem() instanceof IaSItemFood))
				if (!e.isCanceled()) {
					e.setCanceled(true);
					IaSPlayerHelper.messagePlayer(e.entityPlayer,
							"It's been frozen solid. Eating it would be dangerous.");
				}
		}
	}
}
