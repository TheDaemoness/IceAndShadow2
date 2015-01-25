package iceandshadow2.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * A list of functions a throwing knife entity is guaranteed to have. This is
 * NOT existent on the throwing knife item itself. Can be used to check if an
 * entity is a throwing knife by instanceof.
 */
public abstract class IaSEntityKnifeBase extends Entity implements IProjectile {
	public IaSEntityKnifeBase(World w) {
		super(w);
	}

	public abstract void doDrop(IaSToolMaterial mat);

	/**
	 * Gets an ItemStack representation of this throwing knife.
	 */
	public abstract ItemStack getItemStack();

	public abstract void setSource(ItemStack stack);
}
