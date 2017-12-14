package iceandshadow2.nyx.forge;

import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSBlockClimbable;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.ias.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class NyxBlockHandler {
	@SubscribeEvent
	public void onTryToReplaceNoReplace(PlayerInteractEvent e) {
		final ItemStack is = e.entityPlayer.getEquipmentInSlot(0);
		if (e.entity.dimension != IaSFlags.dim_nyx_id || is == null || !(is.getItem() instanceof ItemBlock))
			return;
		switch (e.action) {
		case RIGHT_CLICK_BLOCK:
			final ForgeDirection dir = ForgeDirection.getOrientation(e.face);
			final Block bl = e.world.getBlock(e.x + dir.offsetX, e.y + dir.offsetY, e.z + dir.offsetZ);
			if (!bl.isReplaceable(e.world, e.x + dir.offsetX, e.y + dir.offsetY, e.z + dir.offsetZ)) {
				IaSPlayerHelper.messagePlayer(e.entityPlayer, "Something stops you from placing a block there.");
				e.setCanceled(true);
			}
		default:
			break;
		}
	}

	@SubscribeEvent
	public void onTryToStartClimbingOrSomething(LivingJumpEvent e) {
		if (e.entityLiving instanceof EntityPlayer && e.entityLiving.isSneaking()) {
			final World w = e.entityLiving.worldObj;
			final int x = (int) (e.entityLiving.posX - (e.entityLiving.posX < 0 ? 1 : 0));
			final int y = (int) (e.entityLiving.posY);
			final int z = (int) (e.entityLiving.posZ - (e.entityLiving.posZ < 0 ? 1 : 0));
			for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				if (w.getBlock(x + dir.offsetX, y, z + dir.offsetZ) instanceof IIaSBlockClimbable) {
					IaSBaseBlockAirlike.makeClimbable(w, x, y, z);
					IaSBaseBlockAirlike.makeClimbable(w, x, y + 1, z);
					break;
				}
		}
	}
}
