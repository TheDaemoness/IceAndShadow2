package iceandshadow2.ias.items.tools;

//import iceandshadow2.nyx.entity.projectile.EntityThrowingKnife;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.nyx.entities.projectile.EntityThrowingKnife;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class IaSItemThrowingKnife extends IaSItemWeapon {

	public IaSItemThrowingKnife() {
		super(EnumIaSToolClass.KNIFE);
		this.setNoRepair();
		this.setMaxDamage(0);
		this.setUnlocalizedName("iasThrowingKnife");
		this.setMaxStackSize(32);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage() > 0)
			return par1ItemStack;
		
		int var10 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.knockback.effectId, par1ItemStack);
		EntityThrowingKnife var8 = new EntityThrowingKnife(par2World,
				par3EntityPlayer, 1.0F, 
				par1ItemStack);
		
		if (!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.stackSize -= 1;
		
		IaSToolMaterial mat = IaSToolMaterial.extractMaterial(par1ItemStack);
		mat.onKnifeThrow(par1ItemStack, par3EntityPlayer, var8);

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.75F);

		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(var8);
		
		par1ItemStack.setItemDamage(IaSToolMaterial.extractMaterial(par1ItemStack).getKnifeCooldown(par1ItemStack, par2World, par3EntityPlayer));

		return par1ItemStack;
    }
	
	@Override
	public boolean onLeftClickEntity(ItemStack is, EntityPlayer user,
			Entity target) {
		IaSToolMaterial m = IaSToolMaterial.extractMaterial(is);
		m.onAttack(is, user, target);
		return true;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World world,
			Entity player, int par4, boolean par5) {
		if(par1ItemStack.getItemDamage() > 0)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage()-1);
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
