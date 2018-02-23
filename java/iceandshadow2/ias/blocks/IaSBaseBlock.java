package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.interfaces.IIaSBlockLight;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class IaSBaseBlock extends Block implements IIaSModName, IIaSAspect, IIaSBlockLight {
	public final EnumIaSModule MODULE;
	protected boolean fullCube;

	protected IaSBaseBlock(EnumIaSModule mod, Material mat) {
		super(mat);
		MODULE = mod;
		if (!(this instanceof IIaSTechnicalBlock)) {
			if(mod == EnumIaSModule.NYX)
				setCreativeTab(IaSCreativeTabs.blocks);
			else
				setCreativeTab(IaSCreativeTabs.misc);
		}
		fullCube = !(mat instanceof MaterialTransparent);
        lightOpacity = fullCube?15:0;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return MODULE.aspect;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	public IaSBaseBlock register() {
		IaSRegistration.register(this);
		return this;
	}

	public IaSBaseBlock setLightColor(float r, float g, float b) {
		return this;
	}
	
	public IaSBaseBlock setLuminescence(float lum) {
		return setLuminescence((int)(15 * lum));
	}

	public IaSBaseBlock setLuminescence(int lum) {
		this.lightOpacity = Math.min(this.lightOpacity, 14);
		this.lightValue = Math.min(lum, 15);
		return this;
	}
	
	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return fullCube;
	}

	@Override
	public boolean isOpaqueCube() {
		return fullCube;
	}
	
	@Override
	public String getModName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean func_149730_j() {
		return fullCube;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		switch(side) {
		case NORTH:
			return
				minZ == 0
				&& minY <= 0
				&& maxY >= 1
				&& minX <= 0
				&& maxX >= 1;
		case SOUTH:
			return maxZ == 1
				&& minY <= 0
				&& maxY >= 1
				&& minX <= 0
				&& maxX >= 1;
		case WEST:
			return minX == 0
				&& minY <= 0
				&& maxY >= 1
				&& minZ <= 0
				&& maxZ >= 1;
		case EAST:
			return maxX == 1
				&& minY <= 0
				&& maxY >= 1
				&& minZ <= 0
				&& maxZ >= 1;
		case DOWN:
			return minY == 0
				&& minX <= 0
				&& maxX >= 1
				&& minZ <= 0
				&& maxZ >= 1;
		case UP:
			return maxY == 1
				&& minX <= 0
				&& maxX >= 1
				&& minZ <= 0
				&& maxZ >= 1;
		default:
			return fullCube;
		}	
	}
	
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess w, int x, int y, int z) {
		return w.getLightBrightnessForSkyBlocks(x, y, z, this.getLightValue(w, x, y, z));
	}

	@Override
	public int getLightValue(int meta) {
		return getLightValue();
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return getLightValue(world.getBlockMetadata(x, y, z));
	}
	
	
}
