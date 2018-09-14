package iceandshadow2.nyx.toolmats;

import java.util.List;

import iceandshadow2.ias.api.IaSEntityKnifeBase;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
		return 8;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 192;
	}

	@Override
	public float getKnifeDamage(IaSEntityKnifeBase knife, EntityLivingBase user, Entity target) {
		return super.getKnifeDamage(knife, user, target)
				+ ((user instanceof EntityPlayer) ? ((EntityPlayer) user).experienceLevel / 6F : 0);
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
	public EnumRarity getRarity() {
		return EnumRarity.uncommon;
	}

	@Override
	public float getToolDamage(ItemStack is, EntityLivingBase user, Entity target) {
		return super.getToolDamage(is, user, target)
				+ ((user instanceof EntityPlayer) ? ((EntityPlayer) user).experienceLevel / 5F : 0);
	}

	@Override
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(NyxItems.alabasterShard, 1, 0);
	}

	@Override
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return mat.getItem() == NyxItems.alabasterShard && mat.getItemDamage() == 0;
	}

	@Override
	public boolean onPreHarvest(ItemStack is, EntityPlayer user, World worldObj, int x, int y, int z, Block block) {
		final int fortune = Math.max(0, (5 + user.experienceLevel) / 10);
		final List<ItemStack> items = IaSBlockHelper.harvest(user, x, y, z, fortune);
		IaSEntityHelper.spawnItems(worldObj, items, x, y, z, block);
		return false;
	}
}
