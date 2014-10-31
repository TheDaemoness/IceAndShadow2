package iceandshadow2.nyx.items.materials;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.api.IIaSThrowingKnife;
import iceandshadow2.api.IaSToolMaterial;

public class NyxMaterialNavistra extends IaSToolMaterial {

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

}
