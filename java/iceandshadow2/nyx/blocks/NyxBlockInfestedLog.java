package iceandshadow2.nyx.blocks;

import iceandshadow2.ias.blocks.IaSBlockDirectional;
import iceandshadow2.util.EnumIaSModule;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class NyxBlockInfestedLog extends IaSBlockDirectional {

	public NyxBlockInfestedLog(String par1) {
		super(EnumIaSModule.NYX, par1, Material.wood);
		this.setLuminescence(0.2F);
		this.setLightColor(0.0F, 1.0F, 0.7F);
        this.setHardness(7.5F);
        this.setResistance(3.0F);
		this.setHarvestLevel("axe", 1);
		this.setStepSound(soundTypeWood);
	}    
	
	/**
     * The type of render function that is called for this block
     */
	@SideOnly(Side.CLIENT)
    public int getRenderType() {
        return 31;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte var7 = 4;
        int var8 = var7 + 1;

        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        Block var12 = par1World.getBlock(par2 + var9, par3 + var10, par4 + var11);

                        if (var12 != null)
                            var12.beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
                    }
                }
            }
        }
    }
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return 0;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    @Override
    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        float var5 = 0.05F;
        return AxisAlignedBB.getBoundingBox((double)((float)par2 + var5), (double)((float)par3 + var5), (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }
    
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	if(!(par5Entity instanceof EntityMob))
    		par5Entity.setInWeb();
    }
    
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
    	par5EntityPlayer.setInWeb();
    	par5EntityPlayer.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 35, 4));
    }

}
