package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSDistillable;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class NyxBlockDistiller extends IaSBaseBlockSingle {

	public NyxBlockDistiller(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
		this.setLuminescence(0.3F);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setBlockBounds(0.3F, 0.7F, 0.05F, 0.95F, 0.3F, 0.7F);
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		Block b = w.getBlock(x, y+1, z);
		if(b.hasTileEntity(w.getBlockMetadata(x, y+1, z))) {
			if(b instanceof BlockChest) {
				TileEntityChest tent = (TileEntityChest)w.getTileEntity(x, y+1, z);
				TileEntityChest tent2 = null;
				tent.getSizeInventory();
				for(int xit = -1; xit <= 1 && tent2 == null; ++xit) {
					for(int zit = -1; zit <= 1; ++zit) {
						if(xit != 0 && zit != 0)
							continue;
						if(w.getBlock(x+xit, y+1, z+zit) == Blocks.chest) {
							tent2 = (TileEntityChest)w.getTileEntity(x+xit, y+1, z+zit);
							break;
						}
					}
				}
				int stacksize = tent.getSizeInventory();
				for(int i = 0; i < stacksize; ++i) {
					ItemStack stq = tent.getStackInSlot(i);
					List<ItemStack> lst = processStack(stq);
					if(lst != null) {
						depositStacks(lst);
						break;
					}
				}
			}
		}
	}

	protected List<ItemStack> processStack(ItemStack stq) {
		if(stq != null && stq.getItem() != null) {
			Object it = stq.getItem();
			if(it instanceof ItemBlock)
				it = ((ItemBlock)it).field_150939_a;
			if(it instanceof IIaSDistillable) {
				IIaSDistillable dist = (IIaSDistillable)it;
				int rate = dist.getBaseRate(stq);
				if(rate <= 0)
					return null;
				int quant = Math.min(stq.stackSize, rate);
				List<ItemStack> retval = dist.getDistillationYield(stq);
				stq.stackSize -= quant;
				return retval;
			}
		}
		return null;
	}

	protected void depositStacks(List<ItemStack> lst) {
		
	}
}
