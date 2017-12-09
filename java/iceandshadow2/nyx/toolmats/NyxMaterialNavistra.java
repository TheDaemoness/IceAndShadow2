package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialNavistra extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_navistra.png");

	@Override
	public int damageToolOnAttack(ItemStack is, EntityLivingBase user, Entity target) {
		return 0;
	}

	@Override
	public float getBaseDamage() {
		return 2;
	}

	@Override
	public int getBaseLevel() {
		return 4;
	}

	@Override
	public float getBaseSpeed() {
		return 6;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 16;
	}
	
	@Override
	public int getKnifeCooldown(ItemStack par1ItemStack, World par2World, EntityLivingBase elb) {
		return 24;
	}

	@Override
	public DamageSource getKnifeDamageSource(IaSEntityKnifeBase knife, Entity thrower) {
		final DamageSource ds = super.getKnifeDamageSource(knife, thrower).setDamageBypassesArmor();
		return ds;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialNavistra.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.navistraShard;
	}

	@Override
	public String getMaterialName() {
		return "Navistra";
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.navistraShard;
	}

	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (target instanceof EntityLivingBase) {
			if (user instanceof EntityPlayer)
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user).setDamageBypassesArmor(),
						getToolDamage(is, user, target));
			else
				target.attackEntityFrom(DamageSource.causeMobDamage(user).setDamageBypassesArmor(),
						getToolDamage(is, user, target));
			((EntityLivingBase)target).addPotionEffect(new PotionEffect(Potion.confusion.id, 25, 0));
		}
		final float force = (3+Math.abs(user.getEyeHeight()))/(1+Math.abs(target.getEyeHeight()));
		target.addVelocity(user.motionX*force, 0.2, user.motionZ*force);
		return damageToolOnAttack(is, user, target);
	}
	
	

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, Entity target) {
		if(knife.worldObj.isRemote)
			return false;
		final float force = 4/(1+Math.abs(2*target.getEyeHeight()));
		if(target instanceof EntityLivingBase) {
			EntityLivingBase victim = (EntityLivingBase)target;
			final boolean isMob = victim instanceof EntityMob;
			if(victim.isPotionActive(Potion.confusion.id) || (isMob && ((EntityMob)target).getAttackTarget() != user)) {
				if(!isMob || (victim.getEquipmentInSlot(0) != null && victim.getEquipmentInSlot(0).getRarity() == EnumRarity.common))
					IaSEntityHelper.dropItem(victim, victim.getEquipmentInSlot(0));
				victim.setCurrentItemOrArmor(0, null);
			}
			victim.addPotionEffect(new PotionEffect(Potion.confusion.id, 35, 0));
		}
		target.addVelocity(knife.motionX*force, 0.1, knife.motionZ*force);
		return super.onKnifeHit(user, knife, target);
	}

	@Override
	public int onPostHarvest(ItemStack is, EntityLivingBase user, World w, int x, int y, int z, Block bl) {
		super.onPostHarvest(is, user, w, x, y, z, bl);
		return 0;
	}

}
