package iceandshadow2.nyx.toolmats;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class NyxMaterialCortra extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_cortra.png");

	@Override
	public float getBaseDamage() {
		return 2;
	}

	@Override
	public int getBaseLevel() {
		return 2;
	}

	@Override
	public float getBaseSpeed() {
		return 10;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 256;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialCortra.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.cortra;
	}

	@Override
	public String getMaterialName() {
		return "Cortra";
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.cortraIngot && mat.getItemDamage() == 1;
	}

	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (user instanceof EntityPlayer && target instanceof EntityLivingBase
				&& IaSEntityHelper.getMagicLevel((EntityLivingBase) target) > 0) {
			((EntityPlayer) user).addExperience(target.isEntityInvulnerable() ? 0 : 1);
		}
		return super.onAttack(is, user, target);
	}

	@Override
	public void onKnifeThrow(ItemStack is, EntityLivingBase user, IaSEntityKnifeBase knife) {
		final Entity victim = user.worldObj.findNearestEntityWithinAABB(EntityLivingBase.class,
				AxisAlignedBB.getBoundingBox(user.posX - 12, user.posY - 16, user.posZ - 12, user.posX + 12,
						user.posY + 8, user.posZ + 12),
				user);
		if (victim != null && EnumIaSAspect.getAspect(victim) != EnumIaSAspect.getAspect(user)) {
			knife.setThrowableHeading(victim.posX - user.posX,
					victim.posY - user.posY + victim.getEyeHeight() - user.getEyeHeight() + 0.5,
					victim.posZ - user.posZ, 2F, 0.0F);
		}
	}

	@Override
	public boolean onPreHarvest(ItemStack is, EntityPlayer user, World w, int x, int y, int z, Block bl) {
		List<ItemStack> items = IaSBlockHelper.harvest(user, x, y, z);
		for(ItemStack drop : items)
			IaSPlayerHelper.giveItem(user, drop);
		return super.onPreHarvest(is, user, w, x, y, z, bl);
	}
}
