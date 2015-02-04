package iceandshadow2.nyx.toolmats;

import java.util.List;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class NyxMaterialDevora extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_devora.png");

	@Override
	public int getBaseLevel() {
		return 1;
	}

	@Override
	public float getBaseSpeed() {
		return 32;
	}

	@Override
	public float getBaseDamage() {
		return 6;
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
	public int onHarvest(ItemStack is, EntityLivingBase user, World w, int x,
			int y, int z) {
		if(!(user instanceof EntityPlayer))
			return super.onHarvest(is, user, w, x, y, z);
		Block origin = w.getBlock(x, y, z);
		float hardness = origin.getBlockHardness(w, x, y, z);
		for(int i = 1; i < 9; i+=2) {
			final int xit = i%3-1;
			final int zit = i/3-1;
			explodeMine((EntityPlayer)user, w, x+xit, y, z+zit, hardness);
		}
		explodeMine((EntityPlayer)user, w, x, y+1, z, hardness);
		explodeMine((EntityPlayer)user, w, x, y-1, z, hardness);
		w.createExplosion(user, x+0.5, y+0.5, z+0.5, 0.5F, false);
		return 2*super.onHarvest(is, user, w, x, y, z);
	}
	
	protected void explodeMine(EntityPlayer user, World w, int x, int y, int z, float hard) {
		Block bl = w.getBlock(x, y, z);
		if(!bl.canHarvestBlock(user, w.getBlockMetadata(x, y, z)))
			return;
		if(bl.getBlockHardness(w, x, y, z) < hard)
			return;
		w.func_147480_a(x, y, z, true);
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
			if(o != target && o instanceof EntityLivingBase) {
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
			if(o != target && o instanceof EntityLivingBase) {
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
