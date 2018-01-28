package iceandshadow2.ias.util;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class IaSItemHelper {
	public static Object extractItem(ItemStack is) {
		if(is == null)
			return null;
		Object o = is.getItem();
		if(o instanceof ItemBlock)
			return ((ItemBlock)o).field_150939_a;
		return o;
	}
}
