package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemExousicIce extends IaSBaseItemSingle implements IIaSApiTransmute {

	public NyxItemExousicIce(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		return 0;
		/*
		 * if (catalyst.getItem() != this) return 0; if (target.getItem()
		 * instanceof ItemBlock) { final Block bl = ((ItemBlock)
		 * target.getItem()).field_150939_a; if (bl == NyxBlocks.brickFrozen &&
		 * catalyst.stackSize >= 3) return 25; if (bl ==
		 * NyxBlocks.brickPaleCracked) return 25; } return 0;
		 */
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final ArrayList<ItemStack> li = new ArrayList<ItemStack>();
		int quant = Math.min(target.stackSize, catalyst.stackSize);
		if (target.getItem() instanceof ItemBlock) {
			if (((ItemBlock) target.getItem()).field_150939_a == NyxBlocks.brickPaleCracked) {
				catalyst.stackSize -= 1 * quant;
				li.add(new ItemStack(NyxBlocks.brickPale, quant));
			} else {
				quant = Math.min(target.stackSize, catalyst.stackSize / 3);
				catalyst.stackSize -= 3 * quant;
				li.add(new ItemStack(NyxBlocks.brickPale, quant * 2));
			}
			target.stackSize -= 1 * quant;
		}
		return li;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer p, World w, int x, int y, int z, int side, float fX, float fY,
			float fZ) {
		final Block bl = w.getBlock(x, y, z);
		if (bl == NyxBlocks.stone || bl == NyxBlocks.stoneGrowing) {
			is.stackSize -= 1;
			IaSBlockHelper.breakBlock(w, x, y, z);
			w.setBlock(x, y, z, NyxBlocks.brickPale);
			return true;
		}
		return false;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
