package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBowFrostShort extends NyxItemBow implements IIaSModName,
		IIaSGlowing {

	public NyxItemBowFrostShort(String par1) {
		super(par1);
		this.setMaxDamage(384);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		final int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
		inuse = false;

		float var7 = var6 / (20.0F-this.getSpeedModifier(par1ItemStack));
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if (var7 < 0.6D)
			return;

		if (var7 > 0.95F)
			var7 = 1.0F;

		final int var9 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.power.effectId, par1ItemStack) + 1;
		final int var10 = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.punch.effectId, par1ItemStack);
		final EntityIceArrow var8 = new EntityIceArrow(par2World,
				par3EntityPlayer, var7 * 2.0F, var10 + 1, var9 * 30 + 70);

		var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);

		if (var10 > 0) {
			var8.setKnockbackStrength(var10);
		}

		if (!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + 1);

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F
				/ (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(var8);
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 20-this.getSpeedModifier(par1ItemStack);
	}
	
	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		this.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, 0);
		return par1ItemStack;
	}
	
	@Override
	public int getTimeForIcon(int mod, int index) {
		if(index == 2)
			return 15-mod;
		if(index == 1)
			return 10-mod;
		return 0;
	}
	
	@Override
	public int getUpgradeCost() {
		return 7;
	}
}
