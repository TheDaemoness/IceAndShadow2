package iceandshadow2.styx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StyxBlockBarrier extends IaSBaseBlockSingle {

	public StyxBlockBarrier(String texName) {
		super(EnumIaSModule.STYX, texName, Material.portal);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		slipperiness = 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return 0;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		world.setBlock(x, y, z, this);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_) {
		return p_149646_5_ != 0
				&& super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

}
