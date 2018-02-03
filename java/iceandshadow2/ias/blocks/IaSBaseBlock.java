package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class IaSBaseBlock extends Block implements IIaSModName, IIaSAspect {
	public final EnumIaSModule MODULE;
	protected boolean fullCube;

	protected IaSBaseBlock(EnumIaSModule mod, Material mat) {
		super(mat);
		MODULE = mod;
		if (mod == EnumIaSModule.NYX && !(this instanceof IIaSTechnicalBlock)) {
			setCreativeTab(IaSCreativeTabs.blocks);
		}
		fullCube = true;
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
		if (this.getLightOpacity() >= 15) {
			setLightOpacity(14);
		}
		setLightLevel(lum);
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
}
