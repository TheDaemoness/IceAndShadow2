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

public class NyxMaterialExousium extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_exousium.png");

	@Override
	public int getBaseLevel() {
		return 0;
	}

	@Override
	public float getBaseSpeed() {
		return 24;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 32;
	}

	@Override
	public DamageSource getKnifeDamageSource(IaSEntityKnifeBase knife,
			Entity thrower) {
		final DamageSource ds = super.getKnifeDamageSource(knife, thrower)
				.setDamageBypassesArmor().setDamageIsAbsolute();
		return ds;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return knife_tex;
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
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.echirIngot;
	}

}
