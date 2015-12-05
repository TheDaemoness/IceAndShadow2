package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBlockAltar;
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
		float xpgain = IaSRegistry.getSacrificeXpYield(is)*is.stackSize;
		while ((int)xpgain > 0) {
			int i1 = EntityXPOrb.getXPSplit((int)xpgain);
			xpgain -= i1;
			wd.spawnEntityInWorld(new EntityXPOrb(
					wd, (double) x + 0.5D, (double) y + 0.8D,
					(double) z + 0.5D, i1));
		}
		xpgain += (wd.rand.nextFloat()<xpgain)?1:0;
		return xpgain != 0;
	}

	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z,
			EntityPlayer par5EntityPlayer, int par6, float par7, float par8,
			float par9) {
		final boolean f = burnItem(par1World, x, y, z,
				par5EntityPlayer.getCurrentEquippedItem());
		if (f)
			par5EntityPlayer.setCurrentItemOrArmor(0, null);
		return f;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int x, int y, int z,
			Entity theEnt) {
		if (theEnt instanceof EntityItem && !par1World.isRemote) {
			final ItemStack staque = ((EntityItem) theEnt).getEntityItem();
			burnItem(par1World, x, y, z, staque);
			theEnt.setDead();
		} else
			theEnt.attackEntityFrom(IaSDamageSources.dmgXpAltar, 1);
	}
}
