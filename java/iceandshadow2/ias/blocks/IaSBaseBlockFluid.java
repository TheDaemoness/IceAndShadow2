package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
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

public class IaSBaseBlockFluid extends BlockFluidFinite implements IIaSModName {

	private final EnumIaSModule MODULE;

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon, flowingIcon;

	public IaSBaseBlockFluid(EnumIaSModule mod, String texName, Fluid fluid) {
		super(fluid, Material.water);
		setBlockName(mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
		this.MODULE = mod;
		this.setQuantaPerBlock(16);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return this.MODULE;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		switch (ForgeDirection.getOrientation(side)) {
		case UP:
		case DOWN:
		case UNKNOWN:
			return this.stillIcon;
		default:
			return this.flowingIcon;
		}
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	public IaSBaseBlockFluid register() {
		IaSRegistration.register(this);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.stillIcon = register.registerIcon(getTexName() + "Still");
		this.flowingIcon = register.registerIcon(getTexName() + "Flowing");
		this.getFluid().setIcons(this.stillIcon, this.flowingIcon);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		Block block = world.getBlock(x, y, z);
		if (block != this)
			return block.shouldSideBeRendered(world, x, y, z, side);
		final ForgeDirection dir = ForgeDirection.getOrientation(side);
		final Block bl = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		return dir == ForgeDirection.UP || (!bl.isOpaqueCube() && bl != this);
	}

}
