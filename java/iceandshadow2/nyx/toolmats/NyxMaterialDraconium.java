package iceandshadow2.nyx.toolmats;

import iceandshadow2.ias.api.IaSEntityKnifeBase;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class NyxMaterialDraconium extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_draconium.png");

	@Override
	public float getBaseDamage() {
		return 4;
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
		return NyxMaterialDraconium.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.draconium;
	}

	@Override
	public String getMaterialName() {
		return "Draconium";
	}

	@Override
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(NyxItems.draconium, 1, 1);
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.draconiumIngot && mat.getItemDamage() == 1;
	}

}
