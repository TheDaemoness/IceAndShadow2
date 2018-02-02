package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.ias.util.BlockPos3;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.gen.Sculptor;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxGrenadeIceWall extends IaSGrenadeLogic {
	
	//TODO: Need a better system for block freezing.
	private void replace(World w, int x, int y, int z) {
		final Block bl = w.getBlock(x, y, z);
		if(IaSBlockHelper.isAir(bl) || bl.isReplaceable(w, x, y, z)) {
			w.setBlock(x, y, z, NyxBlocks.unstableIce);
		}
	}
	
	@Override
	public void onDetonate(EntityGrenade ent) {
		final int
			xzLim = 2,
			x = (int)Math.floor(ent.posX),
			y = (int)ent.posY,
			z = (int)Math.floor(ent.posZ);
		if(!ent.worldObj.isRemote) {
			for(int yi = -1; yi <= xzLim; ++yi) {
				for(int xzi = -xzLim; xzi <= xzLim; ++xzi) {
					replace(ent.worldObj, x-xzLim-1, y+yi, z+xzi);
					replace(ent.worldObj, x+xzLim+1, y+yi, z+xzi);
					replace(ent.worldObj, x+xzi, y+yi, z-xzLim-1);
					replace(ent.worldObj, x+xzi, y+yi, z+xzLim+1);
				}
				if(yi != xzLim) {
					replace(ent.worldObj, x-xzLim, y+yi, z-xzLim);
					replace(ent.worldObj, x+xzLim, y+yi, z-xzLim);
					replace(ent.worldObj, x-xzLim, y+yi, z+xzLim);
					replace(ent.worldObj, x+xzLim, y+yi, z+xzLim);
				}
			}
		}
	}
	
	@Override
	public String getName() {
		return "nyxIceWall";
	}

	@Override
	public void onSpawnParticle(World w, double x, double y, double z) {
		IaSFxManager.spawnParticle(w, "cloudLarge", x, y, z,
				0.5-w.rand.nextDouble(),
				w.rand.nextDouble()/4,
				0.5-w.rand.nextDouble(),
				true, false);
		IaSFxManager.spawnParticle(w, "cloudLarge", x, y, z,
				0.5-w.rand.nextDouble(),
				w.rand.nextDouble()/4,
				0.5-w.rand.nextDouble(),
				true, true);
	}
	
	@Override
	public ItemStack getCraftingStack(boolean second) {
		if(second)
			return new ItemStack(NyxItems.nifelhiumPowder, 1, 1);
		else
			return new ItemStack(NyxBlocks.unstableIce);
	}
}
