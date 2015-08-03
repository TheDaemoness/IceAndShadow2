package iceandshadow2.nyx.toolmats;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NyxMaterialExousium extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_exousium.png");

	@Override
	public int getBaseLevel() {
		return -1;
	}
	
	@Override
	public int getHarvestLevel(ItemStack is, String toolClass) {
		return getBaseLevel();
	}

	@Override
	public float getBaseSpeed() {
		return 36;
	}
	
	@Override
	public float getHarvestSpeed(ItemStack is, Block target) {
		return getBaseSpeed();
	}

	@Override
	public int getDurability(ItemStack is) {
		return 48;
	}
	
	@Override
	public float getBaseDamage() {
		return 7;
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
		return mat.getItem() == NyxItems.exousium && mat.getItemDamage() == 1;
	}
	
	@Override
	public boolean glows(EnumIaSToolClass mat) {
		return true;
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife,
			ChunkCoordinates block) {
		super.onKnifeHit(user, knife, block);
		return false;
	}

	@Override
	public int onHarvest(ItemStack is, EntityLivingBase user, World w, int x,
			int y, int z) {
		int durab = Math.max(0,w.getBlock(x, y, z).getHarvestLevel(w.getBlockMetadata(x,y,z)));
		w.setBlockToAir(x, y, z);
		return durab;
	}
}
