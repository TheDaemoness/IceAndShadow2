package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.nyx.NyxItems;

public class NyxItemAlabasterShard extends NyxItemAlabaster {

	public NyxItemAlabasterShard(String texName) {
		super(texName);
		setMaxStackSize(16);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != this)
			return 0;
		if (target.getItem() == Items.bone)
			return 210; // Smoulder it.
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> retval = new ArrayList<ItemStack>();
		if (catalyst.getItem() == this) {
			catalyst.stackSize -= 1;
			target.stackSize -= 1;
			if (target.getItem() == Items.bone)
				retval.add(new ItemStack(NyxItems.boneSanctified));
		}
		return retval;
	}

}
