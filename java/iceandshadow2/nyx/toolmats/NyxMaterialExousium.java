package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.EnumIaSToolClass;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.api.IaSEntityKnifeBase;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialExousium extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_exousium.png");

	@Override
	public float getBaseDamage() {
		return 1;
	}

	@Override
	public int getBaseLevel() {
		return 4;
	}

	@Override
	public float getBaseSpeed() {
		return 48;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 32;
	}

	@Override
	public int getHarvestLevel(ItemStack is, String toolClass) {
		return getBaseLevel();
	}

	@Override
	public float getHarvestSpeed(ItemStack is, Block target) {
		if (target instanceof IIaSAspect)
			if (((IIaSAspect) target).getAspect() == EnumIaSAspect.NAVISTRA)
				return 0.5F;
		return getBaseSpeed();
	}

	@Override
	public int getKnifeCooldown(ItemStack par1ItemStack, World par2World, EntityLivingBase elb) {
		return 16;
	}

	@Override
	public DamageSource getKnifeDamageSource(IaSEntityKnifeBase knife, Entity thrower) {
		final DamageSource ds = super.getKnifeDamageSource(knife, thrower).setDamageBypassesArmor()
				.setDamageIsAbsolute();
		return ds;
	}

	@Override
	public String getKnifeMissSound() {
		return "random.fizz";
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialExousium.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.exousium;
	}

	@Override
	public String getMaterialName() {
		return "Exousium";
	}

	@Override
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(NyxItems.exousium, 1, 0);
	}

	@Override
	public boolean glows(EnumIaSToolClass mat) {
		return true;
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return false;
	}

	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (target instanceof EntityLivingBase) {
			final EntityLivingBase elb = ((EntityLivingBase) target);
			elb.addPotionEffect(new PotionEffect(Potion.wither.id, 45, 2));
			for (int i = 1; i < 5; ++i) {
				final ItemStack eqi = elb.getEquipmentInSlot(i);
				if (eqi != null) {
					final int severity = (int) (16 * getToolDamage(is, user, target) + Math.cbrt(eqi.getMaxDamage()));
					if (eqi.attemptDamageItem(severity, user.getRNG()))
						elb.setCurrentItemOrArmor(i, null);
				}
			}
			if (user instanceof EntityPlayer)
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user).setDamageBypassesArmor()
						.setDamageIsAbsolute(), getToolDamage(is, user, target));
			else
				target.attackEntityFrom(
						DamageSource.causeMobDamage(user).setDamageBypassesArmor().setDamageIsAbsolute(),
						getToolDamage(is, user, target));
		}
		return damageToolOnAttack(is, user, target);
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, ChunkCoordinates block) {
		super.onKnifeHit(user, knife, block);
		return false;
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, Entity target) {
		if (!target.worldObj.isRemote && target instanceof EntityLivingBase) {
			final EntityLivingBase elb = ((EntityLivingBase) target);
			elb.addPotionEffect(new PotionEffect(Potion.wither.id, 45, 2));
			for (int i = 1; i < 5; ++i) {
				final ItemStack eqi = elb.getEquipmentInSlot(i);
				if (eqi != null) {
					final int severity = (int) (16 * getKnifeDamage(knife, user, target)
							+ Math.cbrt(eqi.getMaxDamage()));
					if (eqi.attemptDamageItem(severity, user.getRNG()))
						elb.setCurrentItemOrArmor(i, null);
				}
			}
			elb.addPotionEffect(new PotionEffect(Potion.wither.id, 45, 2));
		}
		return false;
	}

	@Override
	public boolean onPreHarvest(ItemStack is, EntityPlayer user, World worldObj, int x, int y, int z, Block bl) {
		if (!worldObj.isRemote)
			worldObj.setBlockToAir(x, y, z);
		return true;
	}
}
