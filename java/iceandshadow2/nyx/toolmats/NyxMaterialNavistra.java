package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		}
		return damageToolOnAttack(is, user, target);
	}

	@Override
	public int onPostHarvest(ItemStack is, EntityLivingBase user, World w, int x, int y, int z, Block bl) {
		super.onPostHarvest(is, user, w, x, y, z, bl);
		return 0;
	}

}
