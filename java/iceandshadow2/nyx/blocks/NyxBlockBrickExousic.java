package iceandshadow2.nyx.blocks;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.ai.IIaSBlockPathDesirability;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockBrickExousic extends IaSBaseBlockSingle implements IIaSBlockPathDesirability {

	public NyxBlockBrickExousic(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(NyxBlockStone.RESISTANCE);
		setHardness(NyxBlockStone.HARDNESS);
		this.setHarvestLevel("pickaxe", 0);
		setLuminescence(0.3F);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.EXOUSIUM;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public float getBlockPathWeight(IBlockAccess w, int x, int y, int z) {
		return -32;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - 0.0125F, z + 1);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_,
			Explosion p_149723_5_) {
		super.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
		p_149723_1_.setBlock(p_149723_2_, p_149723_3_, p_149723_4_,
				p_149723_1_.rand.nextBoolean() ? Blocks.air : NyxBlocks.brickExousicCracked);
	}

	@Override
	public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_,
			int p_149664_5_) {
		super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
		p_149664_1_.setBlock(p_149664_2_, p_149664_3_, p_149664_4_, NyxBlocks.brickExousicCracked);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase) {
			final EntityLivingBase lb = (EntityLivingBase) ent;
			if (IaSEntityHelper.isTouchingGround(lb)) {
				lb.attackEntityFrom(DamageSource.wither, 0.5F);
				lb.addPotionEffect(new PotionEffect(Potion.wither.id, 41, 0));
			}
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}
}
