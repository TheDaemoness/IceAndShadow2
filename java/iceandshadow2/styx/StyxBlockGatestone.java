package iceandshadow2.styx;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class StyxBlockGatestone extends IaSBaseBlockSingle {

	public StyxBlockGatestone(String texName) {
		super(EnumIaSModule.STYX, texName, Material.dragonEgg);
		setBlockBounds(0.1f, 0.2f, 0.1f, 0.9f, 0.9f, 0.9f);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess w, int x, int y, int z) {
		return w.getLightBrightnessForSkyBlocks(x, y, z, 15) - w.getLightBrightnessForSkyBlocks(x, y, z, 0);
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
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		world.setBlock(x, y, z, this);
	}

	@Override
	public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity e,
			float p_149746_6_) {
		e.fallDistance = 0;
		super.onFallenUpon(p_149746_1_, p_149746_2_, p_149746_3_, p_149746_4_, e, p_149746_6_);
	}
}
