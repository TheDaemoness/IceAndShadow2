package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class IaSBaseBlockTileEntity extends IaSBaseBlockSingle
implements ITileEntityProvider {

	public IaSBaseBlockTileEntity(EnumIaSModule mod, String texName,
			Material mat) {
		super(mod, texName, mat);
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block bl, int meta) {
		super.breakBlock(w, x, y, z, bl, meta);
		breakBlockPre(w, x, y, z, bl, meta);
		w.removeTileEntity(x, y, z);
	}

	/**
	 * Called before the tile entity is removed.
	 */
	public void breakBlockPre(World w, int x, int y, int z, Block bl, int meta) {
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		onBlockAddedPre(w, x, y, z);
		w.setTileEntity(x, y, z,
				this.createNewTileEntity(w, w.getBlockMetadata(x, y, z)));
	}

	/**
	 * Called before the tile entity is set.
	 */
	public void onBlockAddedPre(World w, int x, int y, int z) {
	}

	@Override
	public boolean onBlockEventReceived(World w, int x, int y, int z, int a,
			int b) {
		super.onBlockEventReceived(w, x, y, z, a, b);
		final TileEntity tileentity = w.getTileEntity(x, y, z);
		return tileentity != null ? tileentity.receiveClientEvent(a, b) : false;
	}

}
