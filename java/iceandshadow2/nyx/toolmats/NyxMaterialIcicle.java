package iceandshadow2.nyx.toolmats;

import java.util.List;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialIcicle extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_icicle.png");

	@Override
	public float getBaseDamage() {
		return 3;
	}

	@Override
	public int getBaseLevel() {
		return 2;
	}

	@Override
	public float getBaseSpeed() {
		return 12;
	}

	@Override
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return false;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 8;
	}

	@Override
	public int getKnifeCooldown(ItemStack par1ItemStack, World par2World, EntityLivingBase elb) {
		return 8;
	}

	@Override
	public String getKnifeMissSound() {
		return "dig.glass";
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialIcicle.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return null;
	}

	@Override
	public String getMaterialName() {
		return "Icicle";
	}

	@Override
	public boolean glows(EnumIaSToolClass mat) {
		return false;
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return false;
	}

	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (!target.worldObj.isRemote) {
			if (target instanceof EntityLivingBase)
				((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 65, 1));
		}
		return super.onAttack(is, user, target);
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, ChunkCoordinates block) {
		if (knife.worldObj.isRemote)
			return false;
		final List ents = knife.worldObj.getEntitiesWithinAABBExcludingEntity(knife,
				AxisAlignedBB.getBoundingBox(knife.posX - 1.5F, knife.posY - 2.0F, knife.posZ - 1.5F, knife.posX + 1.5F,
						knife.posY + 1.0F, knife.posZ + 1.5F));
		for (final Object o : ents) {
			if (o instanceof EntityLivingBase) {
				final EntityLivingBase elb = (EntityLivingBase) o;
				if (o instanceof EntityPlayer && !(user instanceof EntityPlayer))
					continue;
				if (o instanceof EntityMob && user instanceof EntityMob)
					continue;
				elb.attackEntityFrom(DamageSource.causeThrownDamage((Entity) o, user), getBaseDamage());
				elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 45, 0));
			}
		}
		knife.setDead();
		return false;
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, Entity target) {
		if (knife.worldObj.isRemote)
			return false;
		if (target instanceof EntityLivingBase)
			((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 65, 1));
		final List ents = knife.worldObj.getEntitiesWithinAABBExcludingEntity(knife,
				AxisAlignedBB.getBoundingBox(knife.posX - 1.5F, knife.posY - 2.0F, knife.posZ - 1.5F, knife.posX + 1.5F,
						knife.posY + 1.0F, knife.posZ + 1.5F));
		for (final Object o : ents) {
			if (o instanceof EntityLivingBase) {
				final EntityLivingBase elb = (EntityLivingBase) o;
				if (o instanceof EntityPlayer && !(user instanceof EntityPlayer))
					continue;
				if (o instanceof EntityMob && user instanceof EntityMob)
					continue;
				elb.attackEntityFrom(DamageSource.causeThrownDamage((Entity) o, user), getBaseDamage());
				elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 45, 0));
			}
		}
		knife.setDead();
		return false;
	}

}
