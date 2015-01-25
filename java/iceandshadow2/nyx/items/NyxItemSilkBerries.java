package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class NyxItemSilkBerries extends IaSItemFood {

	public NyxItemSilkBerries(String id) {
		super(EnumIaSModule.NYX, id, 1, 1.6F, false);
		setAlwaysEdible();
		this.setMaxStackSize(16);
		this.setEatTime(16);
		this.setXpAltarMinimumValue(2);
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.heal(5.0F);
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		final MovingObjectPosition movingobjectposition = this
				.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
						true);

		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				final int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				final int s = movingobjectposition.sideHit;

				if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)) {
				} else if (!par3EntityPlayer.canPlayerEdit(i, j, k,
						movingobjectposition.sideHit, par1ItemStack)) {
				} else {
					final Block i1 = par2World.getBlock(i, j, k);
					par2World.getBlockMetadata(i, j, k);
					if (i1 == NyxBlocks.infestLog) {
						if (par2World.getBiomeGenForCoords(i, k).biomeID != NyxBiomes.nyxInfested.biomeID
								&& !par3EntityPlayer.capabilities.isCreativeMode) {
							IaSPlayerHelper
							.messagePlayer(par3EntityPlayer,
									"It doesn't seem like it'll grow outside of an infested forest.");
							return par1ItemStack;
						}
						if (s == 0)
							return par1ItemStack;
						if (s == 1)
							return par1ItemStack;
						if (s == 2)
							--k;
						if (s == 3)
							++k;
						if (s == 4)
							--i;
						if (s == 5)
							++i;
						if (par2World.isAirBlock(i, j, k)) {
							final int k1 = NyxBlocks.silkBerryPod
									.onBlockPlaced(par2World, i, j, k, s, 0.5F,
											0.5F, 0.5F, 0);
							par2World.setBlock(i, j, k, NyxBlocks.silkBerryPod,
									k1, 3);

							if (!par3EntityPlayer.capabilities.isCreativeMode)
								--par1ItemStack.stackSize;
						}

						return par1ItemStack;
					}
				}
			}
		}
		if (par3EntityPlayer.canEat(true)) {
			par3EntityPlayer.setItemInUse(par1ItemStack,
					this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

}
