package iceandshadow2.ias.items.tools;

//import iceandshadow2.nyx.entity.projectile.EntityThrowingKnife;

import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class IaSItemThrowingKnife extends IaSItemSword {

	public IaSItemThrowingKnife() {
		super();
		this.setNoRepair();
		this.setMaxDamage(0);
		this.setUnlocalizedName("iasThrowingKnife");
		this.setMaxStackSize(32);
	}
	
	/*
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage() > 0)
			return par1ItemStack;
		
		int var10 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.knockback.effectId, par1ItemStack);
		EntityThrowingKnife var8 = new EntityThrowingKnife(par2World,
				par3EntityPlayer, 
				par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeWarped.itemID?1.25F:1.0F, 
				par1ItemStack);

		if(par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeEchir.itemID)
			var8.setType(0);
		else if(par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeNavistra.itemID)
			var8.setType(1);
		else if (par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeDevora.itemID) {
			var8.setIsCritical(true);
			var8.setType(2);
		}
		else if(par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeCortra.itemID)
			var8.setType(3);
		else if(par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeWarped.itemID)
			var8.setType(4);

		var8.setDamage(((IaSItemThrowingKnife) par1ItemStack.getItem())
				.func_82803_g() + 4.0F);

		if (var10 > 0) {
			var8.setKnockbackStrength(var10);
		}

		if (!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.stackSize -= 1;

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.75F);

		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(var8);
		
		if(par1ItemStack.getItem().itemID == IaSTools.nyxThrowingKnifeWarped.itemID)
			par1ItemStack.setItemDamage(8);
		else
			par1ItemStack.setItemDamage(12);

		return par1ItemStack;
    }
    */
	

	public void onUpdate(ItemStack par1ItemStack, World world,
			Entity player, int par4, boolean par5) {
		if(par1ItemStack.getItemDamage() > 0)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()-1);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.common;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		return true;
	}
	
	@Override
	public int getMaxDamage(ItemStack is) {
		return 0;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_,
			Block p_150894_3_, int p_150894_4_, int p_150894_5_,
			int p_150894_6_, EntityLivingBase p_150894_7_) {
		return false;
	}
}
