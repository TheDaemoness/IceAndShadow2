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

public class StyxBlockGatestone extends IaSBaseBlockSingle {

	public StyxBlockGatestone(String texName) {
		super(EnumIaSModule.STYX, texName, Material.dragonEgg);
		setBlockUnbreakable();
		setResistance(Float.MAX_VALUE);
		setCreativeTab(IaSCreativeTabs.blocks);
		setBlockBounds(0.1f, 0.2f, 0.1f, 0.9f, 0.9f, 0.9f);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getMixedBrightnessForBlock(IBlockAccess w, int x, int y, int z) {
		return w.getLightBrightnessForSkyBlocks(x, y, z, 15) - w.getLightBrightnessForSkyBlocks(x, y, z, 0);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		world.setBlock(x, y, z, this);
	}

}
