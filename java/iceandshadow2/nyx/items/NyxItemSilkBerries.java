package iceandshadow2.nyx.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class NyxItemSilkBerries extends IaSItemFood {

	@SideOnly(Side.CLIENT)
	protected IIcon matureIcon;

	public NyxItemSilkBerries(String id) {
		super(EnumIaSModule.NYX, id, 1, 1.6F, false);
		setAlwaysEdible();
		setHasSubtypes(true);
		setMaxStackSize(16);
		setEatTime(16);
		setXpAltarMinimumValue(2);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		for (int meta = 0; meta <= 1; ++meta) {
			par3List.add(new ItemStack(par1, 1, meta));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return this.matureIcon;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.matureIcon = reg.registerIcon(getTexName() + "Mature");
	}

	@Override
	public String getUnlocalizedName(ItemStack is) {
		return this.getUnlocalizedName() + (is.getItemDamage()==1?"Mature":"");
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.removePotionEffect(Potion.poison.id);
		par3EntityPlayer.heal(1+par2World.rand.nextInt(2));
		if(par1ItemStack.getItemDamage() > 0) {
			par3EntityPlayer.heal(2);
			par3EntityPlayer.removePotionEffect(Potion.moveSlowdown.id);
		}
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		final MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
						true);

		if (movingobjectposition != null) {
			if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK && par1ItemStack.getItemDamage() == 1) {
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
		if (par3EntityPlayer.canEat(true))
			par3EntityPlayer.setItemInUse(par1ItemStack,
					getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

}
