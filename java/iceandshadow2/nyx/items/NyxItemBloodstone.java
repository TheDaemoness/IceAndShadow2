package iceandshadow2.nyx.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.util.EnumIaSModule;

public class NyxItemBloodstone extends IaSItemFood {

	public NyxItemBloodstone(String texName) {
		super(EnumIaSModule.NYX, texName, -3, 0.0F, false);
		this.setAlwaysEdible();
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 16;
	}

	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par2World.playSoundAtEntity(par3EntityPlayer, "mob.zombie.unfect", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);       
		par3EntityPlayer.clearActivePotions();
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 159, 5));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 159, 0));
	}
}
