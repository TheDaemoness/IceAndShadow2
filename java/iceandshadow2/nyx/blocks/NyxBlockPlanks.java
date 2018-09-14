package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.api.IIaSNoInfest;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockPlanks extends IaSBaseBlockMulti implements IIaSNoInfest {

	public NyxBlockPlanks(String par1) {
		super(EnumIaSModule.NYX, par1, Material.wood, (byte) 2);
		setLuminescence(0.2F);
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		final float var5 = 0.0125F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3 + var5, par4 + var5, par2 + 1 - var5, par3 + 1 - var5,
				par4 + 1 - var5);
	}

	@Override
	public boolean isWood(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		final int dmg = par1World.getBlockMetadata(par2, par3, par4);
		if (dmg == 1)
			NyxBlocks.infestLog.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
		else
			NyxBlocks.poisonLog.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		final int dmg = par1World.getBlockMetadata(par2, par3, par4);
		if (dmg == 1)
			NyxBlocks.infestLog.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
		else if (par5Entity instanceof EntityLivingBase) {
			((EntityLivingBase) par5Entity).attackEntityFrom(IaSDamageSources.dmgPoisonwood, 1);
			if (!((EntityLivingBase) par5Entity).isPotionActive(Potion.poison))
				((EntityLivingBase) par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id,
						35 * (par1World.difficultySetting.getDifficultyId() + 1), 1));
		}
	}
}
