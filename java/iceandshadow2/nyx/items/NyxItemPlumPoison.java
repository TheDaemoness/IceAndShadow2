package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSItemFood;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class NyxItemPlumPoison extends IaSItemFood {

	public NyxItemPlumPoison(String texName) {
		super(EnumIaSModule.NYX, texName, 4, 14.4F, false);
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_,
			EntityPlayer pwai) {
		pwai.addPotionEffect(new PotionEffect(Potion.poison.id, 69, 1)); // Lol,
																			// 69.
		return super.onEaten(p_77654_1_, p_77654_2_, pwai);
	}
}
