package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockBrickPale extends IaSBaseBlockSingle {

	public NyxBlockBrickPale(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setResistance(9.0F);
		this.setHardness(2.0F);
		this.setHarvestLevel("pickaxe", 0);
		this.setLuminescence(0.3F);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		final float var5 = 0.0125F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3 + var5, par4
				+ var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z,
			Explosion e) {
		world.setBlock(x, y, z, NyxBlocks.brickPaleCracked);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z,
			Entity ent) {
		if (ent instanceof EntityLivingBase) {
			final EntityLivingBase lb = (EntityLivingBase) ent;
			if (lb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				return;
			if (lb.getEquipmentInSlot(1) != null)
				return;
			if (!lb.isPotionActive(Potion.wither.id))
				lb.addPotionEffect(new PotionEffect(Potion.wither.id, 39, 0));
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}
}
