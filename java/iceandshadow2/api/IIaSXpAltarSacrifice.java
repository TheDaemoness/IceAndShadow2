package iceandshadow2.api;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An interface for handlers of IaS's experience altar sacrifice system.
 * This can either be implemented by Item or Block derivatives, or implemented by a handler and registered with IaSRegistry.
 * If an Item or Block has a handler, it will always be run instead of any in IaSRegistry.
 */
public interface IIaSXpAltarSacrifice {
	public int getXpValue(ItemStack is, Random rand);
	public boolean rejectWhenZero();
}
