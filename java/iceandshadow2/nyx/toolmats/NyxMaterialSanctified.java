package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NyxMaterialSanctified extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_sanctified.png");

	@Override
	public float getBaseDamage() {
		return 1;
	}

	@Override
	public int getBaseLevel() {
		return 3;
	}

	@Override
	public float getBaseSpeed() {
		return 12;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 96;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialSanctified.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.alabasterShard;
	}

	@Override
	public String getMaterialName() {
		return "Sanctified";
	}

	@Override
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(NyxItems.alabasterShard, 1, 0);
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.alabasterShard && mat.getItemDamage() == 0;
	}

}
