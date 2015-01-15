package iceandshadow2.nyx.items.materials;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialNavistra extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation("iceandshadow2:textures/entity/nyxknife_navistra.png");

	@Override
	public String getMaterialName() {
		return "Navistra";
	}

	@Override
	public float getBaseSpeed() {
		return 6;
	}

	@Override
	public int getBaseLevel() {
		return 4;
	}
	
	@Override
	public int damageToolOnAttack(ItemStack is, EntityLivingBase user,
			Entity target) {
		return 0;
	}

	@Override
	public int onHarvest(ItemStack is, EntityLivingBase user, World w, int x,
			int y, int z) {
		super.onHarvest(is, user, w, x, y, z);
		return 0;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 16;
	}
	
	@Override
	public DamageSource getKnifeDamageSource(IaSEntityKnifeBase knife,
			Entity thrower) {
		DamageSource ds = super.getKnifeDamageSource(knife, thrower).setDamageBypassesArmor();
		return ds;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return knife_tex;
	}
	
	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.navistraShard;
	}
	
	@Override
	protected Item getMaterialItem() {
		return NyxItems.navistraShard;
	}

}
