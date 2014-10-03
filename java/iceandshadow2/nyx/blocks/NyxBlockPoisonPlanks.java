package iceandshadow2.nyx.blocks;

import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.blocks.IaSBlockDirectional;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.util.EnumIaSModule;

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

public class NyxBlockPoisonPlanks extends IaSBaseBlockSingle implements IIaSNoInfest {

	public NyxBlockPoisonPlanks(String par1) {
		super(EnumIaSModule.NYX, par1, Material.wood);
		this.setLuminescence(0.2F);
		this.setLightColor(0.5F, 1.0F, 0.5F);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setStepSound(soundTypeWood);
	}

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if(par5Entity instanceof EntityMob) {
			((EntityLivingBase)par5Entity).attackEntityFrom(IaSDamageSources.dmgPoisonwood, 1);
			if(!(((EntityLivingBase)par5Entity).isPotionActive(Potion.poison)))
				((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 35*(par1World.difficultySetting.getDifficultyId()+1), 1));
		}
	}
}
