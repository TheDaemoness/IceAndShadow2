package iceandshadow2.nyx.blocks;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.entities.ai.IIaSBlockPathDesirability;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockBrickPale extends IaSBaseBlockSingle implements IIaSBlockPathDesirability {

	public NyxBlockBrickPale(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(NyxBlockStone.RESISTANCE);
		setHardness(NyxBlockStone.HARDNESS);
		this.setHarvestLevel("pickaxe", 0);
		setLuminescence(0.3F);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
		world.setBlock(x, y, z, NyxBlocks.brickPaleCracked);
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
