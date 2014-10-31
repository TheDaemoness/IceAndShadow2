package iceandshadow2.nyx.items.materials;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.api.IIaSThrowingKnife;
import iceandshadow2.api.IaSToolMaterial;

public class NyxMaterialEchir extends IaSToolMaterial {

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
		return "Echir";
	}

	@Override
	public float getBaseSpeed() {
		return 10;
	}

	@Override
	public int getBaseLevel() {
		return 2;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 384;
	}

}
