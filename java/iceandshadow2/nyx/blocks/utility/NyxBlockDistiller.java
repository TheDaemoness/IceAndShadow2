package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiDistillable;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockDistiller extends IaSBaseBlockSingle {

	// TODO: Change this WHOLE THING to use the IIaSApiItemStackIO
	public NyxBlockDistiller(String texName) {
		super(EnumIaSModule.NYX, texName, Material.rock);
		this.setBlockBounds(0.3F, 0.05F, 0.3F, 0.7F, 0.95F, 0.7F);
		this.setLuminescence(0.3F);
		this.setHardness(2.0F);
		this.setResistance(5.0F);
		this.setLightOpacity(5);
	}

	protected void depositStack(ItemStack is, World w, int x, int y, int z) {
		final Block b = w.getBlock(x, y, z);
		if (b.hasTileEntity(w.getBlockMetadata(x, y, z))) {
			if (b instanceof BlockChest) {
				final TileEntityChest tent = (TileEntityChest) w.getTileEntity(
						x, y, z);
				TileEntityChest tent2 = null;
				int mov = 1;
				for (; mov < 9; mov += 2) {
					if (w.getTileEntity(x + mov / 3 - 1, y, z + mov % 3 - 1) instanceof TileEntityChest) {
						tent2 = (TileEntityChest) w.getTileEntity(x + mov / 3
								- 1, y, z + mov % 3 - 1);
						break;
					}
				}
				int stacksize = tent.getSizeInventory();
				if (tent2 != null)
					stacksize = Math.max(stacksize, tent2.getSizeInventory());
				for (int i = 0; i < stacksize; ++i) {
					if (i < tent.getSizeInventory()
							&& tent.isItemValidForSlot(i, is)) {
						if (tent.getStackInSlot(i) != null) {
							final ItemStack iis = tent.getStackInSlot(i);
							final int size = Math.min(iis.getMaxStackSize()
									- iis.stackSize, is.stackSize);
							if (size <= 0)
								continue;
							final ItemStack nis = is.splitStack(size);
							nis.stackSize += iis.stackSize;
							tent.setInventorySlotContents(i, nis);
							if (is.stackSize <= 0)
								break;
						} else {
							tent.setInventorySlotContents(i, is);
							break;
						}
					}
					if (tent2 != null && i < tent2.getSizeInventory()) {
						if (tent2.isItemValidForSlot(i, is)) {
							if (tent.getStackInSlot(i) != null) {
								final ItemStack iis = tent2.getStackInSlot(i);
								final int size = Math.min(iis.getMaxStackSize()
										- iis.stackSize, is.stackSize);
								if (size <= 0)
									continue;
								final ItemStack nis = is.splitStack(size);
								nis.stackSize += iis.stackSize;
								tent2.setInventorySlotContents(i, nis);
								if (is.stackSize <= 0)
									break;
							} else {
								tent2.setInventorySlotContents(i, is);
								break;
							}
						}
					}
				}
				w.markTileEntityChunkModified(x, y, z, tent);
				if (tent2 != null)
					w.markTileEntityChunkModified(mov / 3 - 1, y, mov % 3 - 1,
							tent2);
				return;
			}
		}
		final EntityItem it = new EntityItem(w, x + 0.5, y + 1, z + 0.5, is);
		it.setVelocity(0.0F, -0.1F, 0.0F);
		it.lifespan = Integer.MAX_VALUE - 1;
		w.spawnEntityInWorld(it);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return this.blockIcon;
		return NyxBlocks.brickPale.getIcon(side, 0);
	}
	
	@Override
	public int getMobilityFlag() {
		return 2;
	}

	protected int getStackDecr(ItemStack stq) {
		if (stq != null && stq.getItem() != null) {
			final IIaSApiDistillable dist = IaSRegistry
					.getHandlerDistillation(stq);
			if (dist != null)
				return dist.getDistillationRate(stq);
		}
		return 0;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z,
			ForgeDirection side) {
		return false;
	}

	@Override
	public int onBlockPlaced(World w, int x, int y, int z, int meta, float lx,
			float ly, float lz, int wut) {
		w.scheduleBlockUpdate(x, y, z, this, 1000+w.rand.nextInt(400));
		return 0;
	}

	protected ItemStack processStack(ItemStack stq) {
		if (stq != null && stq.getItem() != null) {
			final IIaSApiDistillable dist = IaSRegistry
					.getHandlerDistillation(stq);
			if (dist != null)
				return dist.getDistillationYield(stq);
		}
		return null;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if (w.isRemote)
			return;
		w.scheduleBlockUpdate(x, y, z, this, 1000+w.rand.nextInt(400));
		final Block b = w.getBlock(x, y + 1, z);
		if (b.hasTileEntity(w.getBlockMetadata(x, y + 1, z))) {
			if (b instanceof BlockChest) {
				final TileEntityChest tent = (TileEntityChest) w.getTileEntity(
						x, y + 1, z);
				TileEntityChest tent2 = null;
				int mov = 1;
				for (; mov < 9; mov += 2) {
					if (w.getTileEntity(x + mov / 3 - 1, y + 1, z + mov % 3 - 1) instanceof TileEntityChest) {
						tent2 = (TileEntityChest) w.getTileEntity(x + mov / 3
								- 1, y + 1, z + mov % 3 - 1);
						break;
					}
				}
				int stacksize = tent.getSizeInventory();
				if (tent2 != null)
					stacksize = Math.max(stacksize, tent2.getSizeInventory());
				for (int i = 0; i < stacksize; ++i) {
					boolean took2 = false;
					ItemStack stq = null;
					if (i < tent.getSizeInventory())
						stq = tent.getStackInSlot(i);
					if (stq == null && tent2 != null
							&& i < tent2.getSizeInventory()) {
						stq = tent2.getStackInSlot(i);
						took2 = true;
					}
					final ItemStack lst = processStack(stq);
					if (lst != null) {
						if (took2)
							tent2.decrStackSize(i, getStackDecr(stq));
						else
							tent.decrStackSize(i, getStackDecr(stq));
						depositStack(lst, w, x, y - 1, z);
						break;
					}
				}
				w.markTileEntityChunkModified(x, y + 1, z, tent);
				if (tent2 != null)
					w.markTileEntityChunkModified(x + mov / 3 - 1, y + 1, z
							+ mov % 3 - 1, tent2);
			} else if (b instanceof BlockHopper) {
				final TileEntityHopper hop = (TileEntityHopper) w
						.getTileEntity(x, y + 1, z);
				for (int i = 0; i < hop.getSizeInventory(); ++i) {
					final ItemStack lst = processStack(hop.getStackInSlot(i));
					if (lst != null) {
						hop.decrStackSize(i,
								getStackDecr(hop.getStackInSlot(i)));
						depositStack(lst, w, x, y - 1, z);
						break;
					}
				}
				w.markTileEntityChunkModified(x, y + 1, z, hop);
			}
		}
	}
}
