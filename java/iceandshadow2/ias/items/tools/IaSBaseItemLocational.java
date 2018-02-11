package iceandshadow2.ias.items.tools;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.util.BlockPos3;
import iceandshadow2.ias.util.EnumNBTType;
import iceandshadow2.ias.util.IntBits;
import iceandshadow2.nyx.NyxItems;

/**
 * Base class for items that can be assigned a block location.
 */
public class IaSBaseItemLocational extends IaSBaseItemSingle {
	
	public static final String
		nbtCoordXID = "iasBlockPosX",
		nbtCoordYID = "iasBlockPosY",
		nbtCoordZID = "iasBlockPosZ";

	public IaSBaseItemLocational(EnumIaSModule mod, String id) {
		super(mod, id);
		setMaxStackSize(1);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1));
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is) {
		final String base = super.getItemStackDisplayName(is);
		if(hasPos(is))
			return base+" <"+getX(is)+" "+getY(is)+" "+getZ(is)+">";
		return base;
	}
	
	
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ep) {
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(w, ep, false);
		if(mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			if(is.getTagCompound() == null)
				is.setTagCompound(new NBTTagCompound());
			if(canBindTo(w, mop.blockX, mop.blockY, mop.blockZ)) {
				NBTTagCompound tc = is.getTagCompound();
				EnumNBTType.set(tc, nbtCoordXID, mop.blockX);
				EnumNBTType.set(tc, nbtCoordYID, mop.blockY);
				EnumNBTType.set(tc, nbtCoordZID, mop.blockZ);
				is.setTagCompound(tc);
				is.setItemDamage(is.getItemDamage() | 1);
			}
		}
		return is;
	}
	
	public static boolean hasPos(ItemStack is) {
		return IntBits.areAllSet(is.getItemDamage(), 1)
			&& is.hasTagCompound()
			&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordXID)
			&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordYID)
			&& EnumNBTType.INT.has(is.getTagCompound(), nbtCoordZID);
	}
	
	public static int getX(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordXID);
	}
	public static int getY(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordYID);
	}
	public static int getZ(ItemStack is) {
		return EnumNBTType.INT.get(is.getTagCompound(), nbtCoordZID);
	}

	public boolean canBindTo(World w, int x, int y, int z) {
		return true;
	}
}
