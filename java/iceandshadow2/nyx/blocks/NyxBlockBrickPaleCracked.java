package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockBrickPaleCracked extends IaSBaseBlockSingle {
	
	public NyxBlockBrickPaleCracked(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setResistance(5.0F);
        this.setHardness(1.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setLuminescence(0.3F);
	}
	
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float var5 = 0.0125F;
        return AxisAlignedBB.getBoundingBox(par2 + var5, par3 + var5, par4 + var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
    }

	@Override
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_,
			int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
		NyxBlocks.brickPale.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
	}
}
