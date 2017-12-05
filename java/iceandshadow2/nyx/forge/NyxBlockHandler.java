package iceandshadow2.nyx.forge;

import iceandshadow2.api.IIaSBlockClimbable;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class NyxBlockHandler {
	@SubscribeEvent
	public void onTryToStartClimbingOrSomething(LivingJumpEvent e) {
		if(e.entityLiving instanceof EntityPlayer) {
			final World w = e.entityLiving.worldObj;
			final int x = (int) (e.entityLiving.posX - (e.entityLiving.posX<0?1:0));
			final int y = (int) (e.entityLiving.posY);
			final int z = (int) (e.entityLiving.posZ - (e.entityLiving.posZ<0?1:0));
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(w.getBlock(x+dir.offsetX, y, z+dir.offsetZ) instanceof IIaSBlockClimbable) {
					w.setBlock(x, y, z, NyxBlocks.virtualLadder);
					break;
				}
			}
		}
	}
}
