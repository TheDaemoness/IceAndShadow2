package iceandshadow2.nyx.blocks.mixins;

import iceandshadow2.ias.IaSDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class NyxBlockFunctionsPoisonwood {

	public static void onBlockClicked(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer) {
		if (par5EntityPlayer.getCurrentEquippedItem() == null)
			par5EntityPlayer
					.addPotionEffect(new PotionEffect(
							Potion.poison.id,
							20 * (par1World.difficultySetting.getDifficultyId() + 1),
							0));
	}

	public static void onEntityCollidedWithBlock(World par1World, int par2,
			int par3, int par4, Entity par5Entity) {
		if (par5Entity instanceof EntityLivingBase
				&& !(par5Entity instanceof EntityMob)) {
			((EntityLivingBase) par5Entity).attackEntityFrom(
					IaSDamageSources.dmgPoisonwood, 1);
			if (!((EntityLivingBase) par5Entity).isPotionActive(Potion.poison))
				((EntityLivingBase) par5Entity)
						.addPotionEffect(new PotionEffect(Potion.poison.id,
								35 * (par1World.difficultySetting
										.getDifficultyId() + 1), 0));
		}
	}
}
