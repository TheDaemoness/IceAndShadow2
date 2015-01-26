package iceandshadow2.nyx.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;

public class NyxItemRope extends IaSBaseItemSingle {

	public NyxItemRope(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(4);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer pl, World w, int x,
			int y, int z, int meta, float lx, float ly, float lz) {
		if (w.getBlock(x, y, z) == NyxBlocks.hookClimbing) {
			for (int i = 1; i <= 2; ++i) {
				final Block bl = w.getBlock(x, y - i, z);
				if (bl.getMaterial() != Material.air) {
					if (!bl.isReplaceable(w, x, y - 1, z))
						return false;
				}
			}
			is.stackSize -= 1;
			w.func_147480_a(x, y - 1, z, true);
			w.setBlock(x, y - 1, z, NyxBlocks.ropeY);
			w.func_147480_a(x, y - 2, z, true);
			w.setBlock(x, y - 2, z, NyxBlocks.ropeY);
			for (y -= 3; y > 0; --y) {
				final Block bl = w.getBlock(x, y, z);
				if (bl.getMaterial() != Material.air) {
					if (bl.getMaterial() == Material.water || bl.getMaterial() == Material.lava)
						break;
					if (!bl.isReplaceable(w, x, y, z))
						break;
				}
				w.func_147480_a(x, y, z, true);
				w.setBlock(x, y, z, NyxBlocks.ropeY);
			}
			return true;
		}
		return false;
	}

}
