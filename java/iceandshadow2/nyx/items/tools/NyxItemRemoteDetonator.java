package iceandshadow2.nyx.items.tools;

import iceandshadow2.nyx.blocks.utility.NyxBlockDetonator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemRemoteDetonator extends NyxItemRemote {

	public NyxItemRemoteDetonator(String par1) {
		super(par1);
	}

	// Possible todo: allow signaling of certain detonator blocks.
	@Override
	public boolean canBindTo(World w, int x, int y, int z) {
		return w.getBlock(x, y, z) instanceof NyxBlockDetonator;
	}

	@Override
	public void onRelease(World w, ItemStack is) {
		super.onRelease(w, is);
		is.setItemDamage(is.getItemDamage() & (~1));
	}
}
