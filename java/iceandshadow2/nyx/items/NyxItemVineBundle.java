package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.NyxBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemVineBundle extends IaSBaseItemSingleGlow {

	public NyxItemVineBundle(String par1) {
		super(EnumIaSModule.NYX, par1);
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int i, int j,
			int k, int sideHit, float par8, float par9, float par10) {

		int meta = 0;

		if (sideHit == 0) {
			meta = 0;
			++j;
		} else if (sideHit == 1) {
			meta = 0;
			--j;
		} else if (sideHit == 2) {
			meta = 1;
			--k;
		} else if (sideHit == 3) {
			meta = 4;
			++k;
		} else if (sideHit == 4) {
			meta = 8;
			--i;
		} else if (sideHit == 5) {
			meta = 2;
			++i;
		}

		if (!par3World.isAirBlock(i, j, k) || !NyxBlocks.thornyVines.canPlaceBlockAt(par3World, i, j, k))
			return false;

		if (par3World.setBlock(i, j, k, NyxBlocks.thornyVines, meta, 0x2)) {
			par1ItemStack.stackSize -= 1;
			return true;
		}
		return false;
	}

}
