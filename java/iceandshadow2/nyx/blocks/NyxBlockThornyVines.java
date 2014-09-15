package iceandshadow2.nyx.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IIaSModName;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;

public class NyxBlockThornyVines extends BlockVine implements IIaSModName {

	public NyxBlockThornyVines(String texName) {
		this.setBlockName("nyx"+texName);
		this.setBlockTextureName(this.getTexName());
		this.setCreativeTab(IaSCreativeTabs.blocks);
        this.setLightLevel(0.1F);
        this.setLightOpacity(0);
        this.setStepSound(soundTypeGrass);
	}
	
	/**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
	@Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.EAST) ||
               par1World.isSideSolid(par2 + 1, par3, par4, ForgeDirection.WEST,  false) ||
               par1World.isSideSolid(par2, par3, par4 - 1, ForgeDirection.SOUTH, false) ||
               par1World.isSideSolid(par2, par3, par4 + 1, ForgeDirection.NORTH, false);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World theWorld, int x, int y, int z, Entity theEntity) {
		
		if(theEntity instanceof EntityLivingBase && !theEntity.onGround) {
			boolean movflag = theEntity.posY != theEntity.prevPosY;
			movflag |= theEntity.posX != theEntity.prevPosX;
			movflag |= theEntity.posZ != theEntity.prevPosZ;
			if(theEntity instanceof EntityMob || !movflag)
				return;
			if(theWorld.difficultySetting == EnumDifficulty.HARD)
				theEntity.attackEntityFrom(IaSDamageSources.dmgVines, 2);
			else
				theEntity.attackEntityFrom(IaSDamageSources.dmgVines, 1);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return 0xFFFFFF;
    }

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}
	
	public Block register() {
		IaSRegistration.register(this);
		return this;
	}
}
