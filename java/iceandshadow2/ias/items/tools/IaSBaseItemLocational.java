package iceandshadow2.ias.items.tools;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.adapters.EnumNBTType;

/**
 * Base class for items that can be assigned a block location.
 */
public class IaSBaseItemLocational extends IaSBaseItemSingle {

	public static final String nbtCoordXID = "iasBlockPosX", nbtCoordYID = "iasBlockPosY", nbtCoordZID = "iasBlockPosZ";

	public static int getX(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordXID);
	}

	public static int getY(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordYID);
	}

	public static int getZ(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordZID);
	}

	public static boolean hasPos(ItemStack is) {
		return IntBits.areAllSet(is.getItemDamage(), 1) && is.hasTagCompound()
				&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordXID)
				&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordYID)
				&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordZID);
	}

	public IaSBaseItemLocational(EnumIaSModule mod, String id) {
		super(mod, id);
		setMaxStackSize(1);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1));
	}

	public boolean canBindTo(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is) {
		final String base = super.getItemStackDisplayName(is);
		if (hasPos(is))
			return base + " <" + getX(is) + " " + getY(is) + " " + getZ(is) + ">";
		return base;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep) {
		final MovingObjectPosition mop = getMovingObjectPositionFromPlayer(w, ep, false);
		if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			if (is.getTagCompound() == null)
				is.setTagCompound(new NBTTagCompound());
			if (canBindTo(w, mop.blockX, mop.blockY, mop.blockZ)) {
				final NBTTagCompound tc = is.getTagCompound();
				EnumNBTType.set(tc, nbtCoordXID, mop.blockX);
				EnumNBTType.set(tc, nbtCoordYID, mop.blockY);
				EnumNBTType.set(tc, nbtCoordZID, mop.blockZ);
				is.setTagCompound(tc);
				is.setItemDamage(is.getItemDamage() | 1);
			}
		}
		return is;
	}
}
