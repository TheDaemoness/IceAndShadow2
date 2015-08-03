package iceandshadow2.nyx.toolmats;

import java.util.Set;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
		return 48;
	}
	
	@Override
	public float getHarvestSpeed(ItemStack is, Block target) {
		final Set<String> s = is.getItem().getToolClasses(is);
		if (!s.contains(target.getHarvestTool(0)))
			return getBaseSpeed()/4;
		return getBaseSpeed();
	}

	@Override
	public int getDurability(ItemStack is) {
		return 32;
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
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (target instanceof EntityLivingBase) {
			if (user instanceof EntityPlayer)
				target.attackEntityFrom(
						DamageSource.causePlayerDamage((EntityPlayer) user).
						setDamageBypassesArmor().setDamageIsAbsolute(),
						getToolDamage(is, user, target));
			else
				target.attackEntityFrom(DamageSource.causeMobDamage(user).
						setDamageBypassesArmor().setDamageIsAbsolute(),
						getToolDamage(is, user, target));
		}
		return damageToolOnAttack(is, user, target);
	}


	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.exousium && mat.getItemDamage() == 0;
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
		if(w.isRemote)
			return 0;
		Block bl = w.getBlock(x, y, z);
		int hl = bl.getHarvestLevel(w.getBlockMetadata(x,y,z));
		int durab = Math.max(0,hl);
		if (!is.getItem().getToolClasses(is).contains(bl.getHarvestTool(0)))
			durab += Math.max(0,hl+1);
		w.setBlockToAir(x, y, z);
		return durab;
	}
	
	@Override
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(NyxItems.exousium,1,1);
	}
}
