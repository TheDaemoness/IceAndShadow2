package iceandshadow2.nyx.items.materials;

import iceandshadow2.api.IaSToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxMaterialCortra extends IaSToolMaterial {

	@Override
	public int getXpValue(World world, ItemStack is) {
		return 0;
	}

	@Override
	public boolean rejectWhenZero() {
		return false;
	}

	@Override
	public String getMaterialName() {
		return "Cortra";
	}

	@Override
	public float getBaseSpeed() {
		return 6;
	}

	@Override
	public int getBaseLevel() {
		return 8;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 256;
	}

}
