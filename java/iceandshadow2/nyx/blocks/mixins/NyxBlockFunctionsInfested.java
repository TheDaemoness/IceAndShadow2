package iceandshadow2.nyx.blocks.mixins;

import iceandshadow2.ias.api.IIaSNoInfest;
import iceandshadow2.nyx.NyxBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class NyxBlockFunctionsInfested {
	public static AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		final float var5 = 0.05F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3 + var5, par4 + var5, par2 + 1 - var5, par3 + 1 - var5,
				par4 + 1 - var5);
	}

	public static void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		par5EntityPlayer.setInWeb();
		par5EntityPlayer.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 35, 4));
	}

	public static void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (!(par5Entity instanceof EntityMob))
			par5Entity.setInWeb();
	}

	public static void updateTick(World w, int x, int y, int z, Random r) {
		for (int xit = -1; xit <= 1; ++xit)
			for (int yit = -1; yit <= 1; ++yit)
				for (int zit = -1; zit <= 1; ++zit) {
					if (r.nextBoolean())
						continue;
					final Block b = w.getBlock(x + xit, y + yit, z + zit);
					// TODO: Exclusion.
					if (b instanceof IIaSNoInfest)
						continue;
					if (b.isLeaves(w, x + xit, y + yit, z + zit))
						w.setBlock(x + xit, y + yit, z + zit, NyxBlocks.infestLeaves);
					else if (b.isWood(w, x + xit, y + yit, z + zit)) {
						final int meta = w.getBlockMetadata(x + xit, y + yit, z + zit) & 12;
						w.setBlock(x + xit, y + yit, z + zit, NyxBlocks.infestLog, meta, 0x2);
					} else if (b.isFoliage(w, x + xit, y + yit, z + zit))
						w.setBlockToAir(x + xit, y + yit, z + zit);
					else if (b.getMaterial() == Material.wood)
						w.setBlockToAir(x + xit, y + yit, z + zit); // TODO:
																	// Fix.
				}
	}
}
