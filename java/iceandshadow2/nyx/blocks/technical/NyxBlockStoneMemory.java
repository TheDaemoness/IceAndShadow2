package iceandshadow2.nyx.blocks.technical;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.nyx.NyxBlocks;

public class NyxBlockStoneMemory extends IaSBaseBlockAirlike {

	public NyxBlockStoneMemory(String texName) {
		super(EnumIaSModule.NYX, texName);
		setLightOpacity(0);
		setTickRandomly(true);
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		updateTick(w, x, y, z, w.rand);
		super.onBlockAdded(w, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block what) {
		updateTick(w, x, y, z, w.rand);
		super.onNeighborBlockChange(w, x, y, z, what);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (w.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == NyxBlocks.stone)
				w.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, NyxBlocks.stoneGrowing);
		super.updateTick(w, x, y, z, r);
	}
}
