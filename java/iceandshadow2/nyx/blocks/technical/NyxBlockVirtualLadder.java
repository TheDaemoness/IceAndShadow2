package iceandshadow2.nyx.blocks.technical;

import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;

public class NyxBlockVirtualLadder
		extends IaSBaseBlockAirlike /* DO NOT MARK CLIMBABLE! */ {

	public NyxBlockVirtualLadder(String texName) {
		super(EnumIaSModule.NYX, texName);
		setLightOpacity(0);
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		if (!w.isAirBlock(x, y, z))
			return false;
		return testClimbable(w, x, y, z);
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		setClimbable(w, x, y, z, true);
		super.onBlockAdded(w, x, y, z);
	}

	@Override
	public void setClimbable(World w, int x, int y, int z, boolean yes) {
		if (yes) {
			super.setClimbable(w, x, y, z, yes);
		} else {
			w.setBlockToAir(x, y, z);
		}
	}

	@Override
	public int tickRate(World p_149738_1_) {
		return 2;
	}
}
