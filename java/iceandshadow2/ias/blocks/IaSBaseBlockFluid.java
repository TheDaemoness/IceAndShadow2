package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.ias.interfaces.IIaSBlockLight;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IaSBaseBlockFluid extends BlockFluidFinite implements IIaSModName, IIaSAspect, IIaSBlockLight {

	private final EnumIaSModule MODULE;

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon, flowingIcon;

	public IaSBaseBlockFluid(EnumIaSModule mod, String texName, Fluid fluid) {
		super(fluid, Material.water);
		setBlockName(mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
		MODULE = mod;
		setQuantaPerBlock(16);
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
	public IIcon getIcon(int side, int meta) {
		switch (ForgeDirection.getOrientation(side)) {
		case UP:
		case DOWN:
		case UNKNOWN:
			return stillIcon;
		default:
			return flowingIcon;
		}
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	public IaSBaseBlockFluid register() {
		IaSRegistration.register(this);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(getTextureName() + "Still");
		flowingIcon = register.registerIcon(getTextureName() + "Flowing");
		getFluid().setIcons(stillIcon, flowingIcon);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		final Block block = world.getBlock(x, y, z);
		if (block != this)
			return block.shouldSideBeRendered(world, x, y, z, side);
		final ForgeDirection dir = ForgeDirection.getOrientation(side);
		final Block bl = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		return dir == ForgeDirection.UP || (!bl.isOpaqueCube() && bl != this);
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return getLightValue(world.getBlockMetadata(x, y, z));
	}

	@Override
	public int getLightValue(int meta) {
		if(maxScaledLight == 0)
			return super.getLightValue();
		return (int) (meta / quantaPerBlockFloat * maxScaledLight);
	}
}
