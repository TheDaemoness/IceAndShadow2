package iceandshadow2.nyx.toolmats;

import java.util.HashMap;
import java.util.Map;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialCortra extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_cortra.png");

	@Override
	public int getBaseLevel() {
		return 8;
	}

	@Override
	public float getBaseSpeed() {
		return 6;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 256;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return knife_tex;
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
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if(target instanceof EntityLivingBase) {
			EntityLivingBase elb = (EntityLivingBase)target;
			Map<Integer,Integer> nu = new HashMap<Integer,Integer>();
			final int shlvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
			final int smlvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, is);
			if(!user.worldObj.isRemote) {
				if(elb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
					if(smlvl < 5 && user.worldObj.rand.nextInt(64+smlvl*12)==0)
						nu.put(Enchantment.smite.effectId, smlvl+1);
					else if(smlvl > 0)
						nu.put(Enchantment.smite.effectId, smlvl);
				}
				else {
					if(shlvl < 5 && user.worldObj.rand.nextInt(48+shlvl*8)==0)
						nu.put(Enchantment.sharpness.effectId, shlvl+1);
					else if(shlvl > 0)
						nu.put(Enchantment.sharpness.effectId, shlvl);
				}
				EnchantmentHelper.setEnchantments(nu, is);
			}
		}
		return super.onAttack(is, user, target);
	}

	@Override
	public int onHarvest(ItemStack is, EntityLivingBase user, World w, int x,
			int y, int z) {
		final int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, is);
		if(!user.worldObj.isRemote && user.worldObj.rand.nextInt(48+16*lvl)==0) {
			Map<Integer,Integer> nu = new HashMap<Integer,Integer>();
			nu.put(Enchantment.efficiency.effectId, Math.min(5, lvl+1));
			final int flvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, is);
			final int ulvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is);
			if(flvl < 3 && lvl >= 3 && user.worldObj.rand.nextInt(5) == 0)
				nu.put(Enchantment.fortune.effectId, Math.min(3, flvl+1));
			else {
				if(flvl > 0)
					nu.put(Enchantment.fortune.effectId, flvl);
				if(ulvl < 5 && lvl >= 1 && user.worldObj.rand.nextInt(4) == 0)
					nu.put(Enchantment.unbreaking.effectId, Math.min(5, ulvl+1));
				else if(ulvl > 0)
					nu.put(Enchantment.unbreaking.effectId, ulvl);
			}
			EnchantmentHelper.setEnchantments(nu, is);
		}
		return super.onHarvest(is, user, w, x, y, z);
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.cortraIngot && mat.getItemDamage() == 1;
	}
}
