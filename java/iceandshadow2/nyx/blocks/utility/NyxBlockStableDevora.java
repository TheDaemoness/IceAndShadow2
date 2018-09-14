package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.util.IaSBlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class NyxBlockStableDevora extends IaSBaseBlockSingle {
	public NyxBlockStableDevora(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		setHardness(0.25F);
		setResistance(0.5F);
		this.setLuminescence(0.2f);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.LAND;
	}

	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z, Explosion e) {
		IaSBlockHelper.breakBlock(w, x, y, z, false);
		if (!w.isRemote) {
			IaSBlockHelper.breakBlock(w, x, y, z, false);
			w.createExplosion(e.getExplosivePlacedBy(), x + 0.5, y + 0.5, z + 0.5, 5.5f, true);
		}
	}

}
