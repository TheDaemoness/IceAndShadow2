package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBlockAltar;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxBlockAltarExperience extends IaSBlockAltar {

	public NyxBlockAltarExperience(String id) {
		super(EnumIaSModule.NYX, id);
		setLightLevel(0.75F);
		setResistance(9001.0F);
		setBlockUnbreakable();
		setStepSound(Block.soundTypeStone);
	}

	public boolean burnItem(World wd, int x, int y, int z, ItemStack is) {
		final float xpgain = IaSRegistry.getSacrificeXpYield(is) * is.stackSize;
		if ((int) xpgain <= 0) // Note: may make altar use seem a bit awkward
								// for smaller stacks.
			return false;
		int xperience /* Do not sue */ = (int) xpgain;
		xperience += wd.rand.nextFloat() < (xpgain - Math.floor(xpgain)) ? 1 : 0;
		while (xperience > 0) {
			final int i1 = EntityXPOrb.getXPSplit(xperience);
			xperience -= i1;
			wd.spawnEntityInWorld(new EntityXPOrb(wd, x + 0.5D, y + 0.8D, z + 0.5D, i1));
		}
		return true;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.ANCIENT;
	}

	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6,
			float par7, float par8, float par9) {
		if (!par1World.isRemote) {
			final boolean f = burnItem(par1World, x, y, z, par5EntityPlayer.getCurrentEquippedItem());
			if (f) {
				par5EntityPlayer.setCurrentItemOrArmor(0, null);
			}
		}
		return super.onBlockActivated(par1World, x, y, z, par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int x, int y, int z, Entity theEnt) {
		if (theEnt instanceof EntityItem) {
			final ItemStack staque = ((EntityItem) theEnt).getEntityItem();
			if (!par1World.isRemote) {
				burnItem(par1World, x, y, z, staque);
				theEnt.setDead();
			} else {
				final int e = (int) Math.sqrt(staque.stackSize);
				for (int i = 0; i < e; ++i) {
					IaSFxManager.spawnParticle(par1World, "vanilla_lava", x + 0.5, y + 0.5, z + 0.5, 0, 0.1, 0, false,
							true);
				}
			}
		} else {
			theEnt.attackEntityFrom(IaSDamageSources.dmgXpAltar, 1);
		}
	}
}
