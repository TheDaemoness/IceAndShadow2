package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.IIaSSignalReceiverBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class NyxBlockDetonator extends IaSBaseBlockSingle implements IIaSSignalReceiverBlock {

	public NyxBlockDetonator(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 0.15f, 0.6f);
		setLightOpacity(0);
		fullCube = false;
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		tryBlowUp(w, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		tryBlowUp(w, x, y, z);
	}

	@Override
	public void onSignalStart(World w, int x, int y, int z, Object data) {
		IaSBlockHelper.breakBlock(w, x, y, z, false);
		if (!w.isRemote)
			w.newExplosion(null, x + 0.5, y, z + 0.5, 0.5f, true, true);
	}

	@Override
	public void onSignalStop(World w, int x, int y, int z, Object data) {
	}

	public void tryBlowUp(World w, int x, int y, int z) {
		if (w.isBlockIndirectlyGettingPowered(x, y, z))
			onSignalStart(w, x, y, z, null);
	}

}
