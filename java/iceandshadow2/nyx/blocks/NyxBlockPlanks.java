package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.blocks.IaSBlockDirectional;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.nyx.NyxBlocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class NyxBlockPlanks extends IaSBaseBlockMulti implements IIaSNoInfest {
	
	public NyxBlockPlanks(String par1) {
		super(EnumIaSModule.NYX, par1, Material.wood, (byte)2);
		this.setLuminescence(0.2F);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
	}
	
	@Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float var5 = 0.0125F;
        return AxisAlignedBB.getBoundingBox((double)((float)par2 + var5), (double)((float)par3 + var5), (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		int dmg = par1World.getBlockMetadata(par2, par3, par4);
		if(dmg == 1) {
			NyxBlocks.infestLog.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
		} else if(par5Entity instanceof EntityLivingBase) {
			((EntityLivingBase)par5Entity).attackEntityFrom(IaSDamageSources.dmgPoisonwood, 1);
			if(!(((EntityLivingBase)par5Entity).isPotionActive(Potion.poison)))
				((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 35*(par1World.difficultySetting.getDifficultyId()+1), 1));
		}
	}
	
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		int dmg = par1World.getBlockMetadata(par2, par3, par4);
		if(dmg == 1)
			NyxBlocks.infestLog.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
		else
			NyxBlocks.poisonLog.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}
}
