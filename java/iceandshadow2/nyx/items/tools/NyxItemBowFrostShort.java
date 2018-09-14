package iceandshadow2.nyx.items.tools;

import iceandshadow2.IIaSModName;
import iceandshadow2.ias.api.IIaSEntityWeaponRanged;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.entities.ai.EntityAINyxRangedAttack;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemBowFrostShort extends NyxBaseItemBow implements IIaSModName, IIaSGlowing, IIaSEntityWeaponRanged {

	public NyxItemBowFrostShort(String par1) {
		super(par1);
		setMaxDamage(384);
	}

	@Override
	public void doRangedAttack(EntityLivingBase user, EntityLivingBase target) {
		final int slowtime = IaSWorldHelper.getDifficulty(user.worldObj) * 15;
		final int slowstr = IaSWorldHelper.getDifficulty(user.worldObj) - 1;
		final int dif = IaSWorldHelper.getDifficulty(user.worldObj);
		final EntityIceArrow arrow = new EntityIceArrow(user.worldObj, user, target, 1.8F, 5.0F, slowstr, slowtime);
		int power = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, user.getHeldItem());
		final int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, user.getHeldItem());
		power += dif == 3 ? 1 : 0;

		final int damage = dif >= 3 ? 8 : 7;
		arrow.setDamage(damage);

		if (power > 0)
			arrow.setDamage(arrow.getDamage() + power * 0.5D + 0.5D);
		if (punch > 0)
			arrow.setKnockbackStrength(punch);

		user.playSound("random.bow", 1.0F, 1.0F / (user.getRNG().nextFloat() * 0.3F + 0.6F));
		user.worldObj.spawnEntityInWorld(arrow);
	}

	@Override
	public EntityAIBase getEntityUseTask(EntityLivingBase user) {
		return new EntityAINyxRangedAttack((IRangedAttackMob) user, user.getAIMoveSpeed(), 25, 35, 24.0F);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 20 - getSpeedModifier(par1ItemStack);
	}

	@Override
	public int getTimeForIcon(int mod, int index) {
		if (index == 2)
			return 15 - mod;
		if (index == 1)
			return 10 - mod;
		return 0;
	}

	@Override
	public int getUpgradeCost(int mod) {
		return 7 + (mod + 1) / 2;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, 0);
		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer,
			int par4) {
		final int var6 = getMaxItemUseDuration(par1ItemStack) - par4;
		inuse = false;

		float var7 = var6 / (20.0F - getSpeedModifier(par1ItemStack));
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if (var7 < 0.6D)
			return;

		if (var7 > 0.95F)
			var7 = 1.0F;

		final int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack) + 1;
		final int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
		final EntityIceArrow var8 = new EntityIceArrow(par2World, par3EntityPlayer, var7 * (2.0F + 0.1F * var9),
				var10 + 1, var9 * 30 + 70);

		var8.setDamage(var8.getDamage() + 0.5D);

		if (var10 > 0)
			var8.setKnockbackStrength(var10);

		if (!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
				1.0F / (Item.itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(var8);
	}
}
