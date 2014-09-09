package iceandshadow2.nyx.blocks;

import java.util.Random;

import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockFalling;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class NyxBlockUnstableIce extends IaSBaseBlockFalling {
	public NyxBlockUnstableIce(String par1) {
		super(EnumIaSModule.NYX, par1, Material.sand);
		this.setStepSound(Block.soundTypeGlass);
		this.setHardness(0.1F);
		this.setResistance(0.5F);
		this.setLightOpacity(4);
		this.setTickRandomly(true);
		this.slipperiness = 1.08F;
		this.setHarvestLevel("spade", 0);
	}
	
	public int getMobilityFlag() {
		return 0;
	}
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.updateTick(par1World, par2, par3, par4, par5Random);
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11)
        {
            if (par1World.provider.isHellWorld)
                par1World.setBlockToAir(par2, par3, par4);
            else
            	par1World.setBlock(par2, par3, par4, Blocks.water);
        }
    }
	
	public boolean isOpaqueCube() {
        return false;
    }
	
	public int getRenderBlockPass() {
		return 1;
	}
}
