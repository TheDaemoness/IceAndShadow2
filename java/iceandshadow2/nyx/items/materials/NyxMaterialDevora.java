package iceandshadow2.nyx.items.materials;

import java.util.List;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSBlockHelper;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxMaterialDevora extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_devora.png");

	@Override
	public int getBaseLevel() {
		return 0;
	}

	@Override
	public float getBaseSpeed() {
		return 64;
	}

	@Override
	public float getBaseDamage() {
		return 9;
	}

	@Override
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return false;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 128;
	}

	@Override
	public float getKnifeDamage(IaSEntityKnifeBase knife,
			EntityLivingBase user, Entity target) {
		return 1;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.devora;
	}

	@Override
	public String getMaterialName() {
		return "Devora";
	}

	@Override
	public boolean glows(EnumIaSToolClass mat) {
		return true;
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.echirIngot && mat.getItemDamage() > 0;
	}

	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if(!target.worldObj.isRemote)
			user.worldObj.createExplosion(user, target.posX,
					target.posY + target.getEyeHeight() / 2, target.posZ, 0.1F,
					true);
		List ents = target.worldObj.getEntitiesWithinAABBExcludingEntity(user, 
				AxisAlignedBB.getBoundingBox(
						target.posX-2.5F, target.posY-3.0F, target.posZ-2.5F, 
						target.posX+2.5F, target.posY+2.0F, target.posZ+2.5F));
		for(Object o : ents) {
			if(o instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase)o;
				if(o instanceof EntityPlayer && !(target instanceof EntityPlayer))
					continue;
				if(o instanceof EntityMob && user instanceof EntityMob)
					continue;
				elb.attackEntityFrom(DamageSource.causeThrownDamage((Entity)o, user), this.getToolDamage(is, user, target)/elb.getDistanceToEntity(user));
			}
		}
		return super.onAttack(is, user, target);
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife,
			ChunkCoordinates block) {
		if(knife.worldObj.isRemote)
			return false;
		Block bl = knife.worldObj.getBlock(block.posX, block.posY, block.posZ);
		Explosion ex = knife.worldObj.createExplosion(user, knife.posX, knife.posY,
				knife.posZ, 0.3F, true);
		if(bl == NyxBlocks.oreDevora)
			bl.onBlockExploded(knife.worldObj, block.posX, block.posY, block.posZ, ex);
		List ents = knife.worldObj.getEntitiesWithinAABBExcludingEntity(knife, 
				AxisAlignedBB.getBoundingBox(
						knife.posX-1.5F, knife.posY-2.0F, knife.posZ-1.5F, 
						knife.posX+1.5F, knife.posY+1.0F, knife.posZ+1.5F));
		for(Object o : ents) {
			if(o instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase)o;
				if(o instanceof EntityPlayer && !(user instanceof EntityPlayer))
					continue;
				if(o instanceof EntityMob && user instanceof EntityMob)
					continue;
				elb.attackEntityFrom(DamageSource.causeThrownDamage((Entity)o, user), this.getBaseDamage());
			}
		}
		knife.setDead();
		return false;
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife,
			Entity target) {
		if (knife.worldObj.isRemote)
			return false;
		knife.worldObj.createExplosion(user, knife.posX, knife.posY,
				knife.posZ, 0.3F, true);
		List ents = target.worldObj.getEntitiesWithinAABBExcludingEntity(knife, 
				AxisAlignedBB.getBoundingBox(
						target.posX-1.5F, target.posY-2.0F, target.posZ-1.5F, 
						target.posX+1.5F, target.posY+1.0F, target.posZ+1.5F));
		for(Object o : ents) {
			if(o instanceof EntityLivingBase) {
				EntityLivingBase elb = (EntityLivingBase)o;
				if(o instanceof EntityPlayer && !(target instanceof EntityPlayer))
					continue;
				if(o instanceof EntityMob && user instanceof EntityMob)
					continue;
				elb.attackEntityFrom(DamageSource.causeThrownDamage((Entity)o, user), this.getBaseDamage());
			}
		}
		knife.setDead();
		return false;
	}
}
