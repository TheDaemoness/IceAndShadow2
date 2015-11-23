package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IaSBaseBlockFluid extends BlockFluidClassic implements IIaSModName {

	private final EnumIaSModule MODULE;

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon, flowingIcon;

	public IaSBaseBlockFluid(EnumIaSModule mod, String texName, Fluid fluid) {
		super(fluid, Material.water);
		setBlockName(mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix
				+ texName);
		this.MODULE = mod;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid())
			return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return this.MODULE;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? this.stillIcon : this.flowingIcon;
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
	}

}
