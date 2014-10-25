package iceandshadow2.nyx.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.util.EnumIaSModule;

public class NyxItemFoodCookie extends IaSItemFood {

	public NyxItemFoodCookie(String texName) {
		super(EnumIaSModule.NYX, texName, 3, 7.2F, false);
		this.setEatTime(24);
	}

	@Override
	public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_,
			EntityPlayer pwai) {
		pwai.heal(1.0F);
		return super.onEaten(p_77654_1_, p_77654_2_, pwai);
	}
	
	

}
