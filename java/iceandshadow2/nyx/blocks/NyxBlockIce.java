package iceandshadow2.nyx.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlockIce extends IaSBaseBlockSingle {

	public NyxBlockIce(String texName) {
		super(EnumIaSModule.NYX, texName, Material.ice);
		this.setLuminescence(0.3F);
		this.setLightOpacity(2);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setTickRandomly(true);
		this.setStepSound(soundTypeGlass);
		this.slipperiness = 0.99F;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
	public boolean shouldSideBeRendered(IBlockAccess w,
			int x, int y, int z, int s) {
		if(w.getBlock(x, y, z) == this)
			return false;
		return super.shouldSideBeRendered(w, x, y, z, s);
	}
	
	@Override
	public boolean isOpaqueCube() {
        return false;
    }
	
	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		for(int xit = -1; xit <= 1; ++xit) {
			for(int zit = -1; zit <= 1; ++zit) {
				if(xit != 0 && zit != 0)
					continue;
				if(w.isAirBlock(x+xit, y, z+zit) && r.nextInt(3) != 0) {
					if(w.getBlock(x+xit, y-1, z+zit) == NyxBlocks.exousicWater) {
						w.setBlock(x+xit, y, z+zit, this);
					}
				}
			}
		}
	}
}
