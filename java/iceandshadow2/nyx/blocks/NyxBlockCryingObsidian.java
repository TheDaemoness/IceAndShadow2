package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockCryingObsidian extends IaSBaseBlockSingle {
	public NyxBlockCryingObsidian(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setLuminescence(1.0F);
		this.setLightColor(0.9F, 0.8F, 1.0F);
		this.setBlockUnbreakable();
		this.setResistance(2000.0F);
	}

    @Override
    public int damageDropped(int par1) {
        return 0;
    }

    @Override
    public int getMobilityFlag() {
		return 2;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		float var5 = 0.0125F;
		return AxisAlignedBB.getBoundingBox(
				par2 + var5, par3,
				par4 + var5,
				par2 + 1 - var5,
				par3 + 1 - var5,
				par4 + 1 - var5);
	}

    @Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {
		if (par5Entity instanceof EntityLivingBase
				&& !(par5Entity instanceof EntityMob)) {
			if (!(((EntityLivingBase) par5Entity)
					.isPotionActive(Potion.regeneration)))
				((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(
						Potion.regeneration.id, 59, 1));
			if (!(((EntityLivingBase) par5Entity)
					.isPotionActive(Potion.resistance)))
				((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(
						Potion.resistance.id, 59, 3));
		}
	}

    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
    	return false;
    }

    /*
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if(par1World.getBlockMetadata(par2, par3, par4) == 0)
			return;
		
		double var9 = (double) ((float) par3 + par5Random.nextFloat());
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;
		int var19 = par5Random.nextInt(2) * 2 - 1;
		int var20 = par5Random.nextInt(2) * 2 - 1;
		var13 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
		var15 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
		var17 = ((double) par5Random.nextFloat() - 0.5D) * 0.125D;
		double var11 = (double) par4 + 0.5D + 0.25D * (double) var20;
		var17 = (double) (par5Random.nextFloat() * 1.0F * (float) var20);
		double var7 = (double) par2 + 0.5D + 0.25D * (double) var19;
		var13 = (double) (par5Random.nextFloat() * 1.0F * (float) var19);
		IaSFxManager.spawnParticle(par1World, "portal", var7, var9, var11, var13, var15,
				var17, false, true);
	}
	*/

    
}
