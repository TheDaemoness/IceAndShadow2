package iceandshadow2.api;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IaSXpAltarSacrifice {
	public int getXpValue(World world, ItemStack is);
	public boolean rejectWhenZero();
}
