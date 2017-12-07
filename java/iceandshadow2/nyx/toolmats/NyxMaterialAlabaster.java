package iceandshadow2.nyx.toolmats;

import java.util.List;

import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class NyxMaterialAlabaster extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation(
			"iceandshadow2:textures/entity/nyxknife_alabaster.png");

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
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return NyxMaterialAlabaster.knife_tex;
	}

	@Override
	protected Item getMaterialItem() {
		return NyxItems.alabasterShard;
	}

	@Override
	public String getMaterialName() {
		return "Alabaster";
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
	public float getKnifeDamage(IaSEntityKnifeBase knife, EntityLivingBase user, Entity target) {
		return super.getKnifeDamage(knife, user, target)  + ((user instanceof EntityPlayer)?((EntityPlayer)user).experienceLevel/6F:0);
	}

	@Override
	public float getToolDamage(ItemStack is, EntityLivingBase user, Entity target) {
		return super.getToolDamage(is, user, target) + ((user instanceof EntityPlayer)?((EntityPlayer)user).experienceLevel/5F:0);
	}

	@Override
	public boolean onPreHarvest(ItemStack is, EntityPlayer user, World worldObj, int x, int y, int z, Block block) {
		final int fortune = (int)Math.max(0, (5+user.experienceLevel)/10);
		final int metadata = worldObj.getBlockMetadata(x, y, z);
		block.dropBlockAsItem(worldObj, x, y, z, metadata, fortune);
		block.onBlockDestroyedByPlayer(worldObj, x, y, z, metadata);
		IaSBlockHelper.breakBlock(worldObj, x, y, z, false);
		return false;
	}	
}
