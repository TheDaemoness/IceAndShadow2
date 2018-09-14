package iceandshadow2.nyx.blocks.utility;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.util.IaSRegistration;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockRail extends BlockRailBase implements IIaSModName, IIaSAspect {

	public static boolean connects(ForgeDirection dir, int meta, boolean curves) {
		if (!curves)
			meta &= 7;
		switch (meta) {
		case 0: // NS
		case 4: // N
		case 5: // S
			return dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH;
		case 1: // EW
		case 2: // E
		case 3: // W
			return dir == ForgeDirection.EAST || dir == ForgeDirection.WEST;
		}
		if (curves)
			switch (meta) {
			case 6: // S+E
				return dir == ForgeDirection.SOUTH || dir == ForgeDirection.EAST;
			case 7: // S+W
				return dir == ForgeDirection.SOUTH || dir == ForgeDirection.WEST;
			case 8: // N+W
				return dir == ForgeDirection.NORTH || dir == ForgeDirection.WEST;
			case 9: // N+E
				return dir == ForgeDirection.NORTH || dir == ForgeDirection.EAST;
			}
		return false;
	}

	@SideOnly(Side.CLIENT)
	protected IIcon alt;
	protected boolean curvy;

	public NyxBlockRail(String name, boolean powered, boolean curvy) {
		super(powered);
		this.curvy = curvy && !powered;
		setBlockName(EnumIaSModule.NYX.prefix + name);
		setLightLevel(0.2f);
		setCreativeTab(IaSCreativeTabs.misc);
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return isPowered();
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NATIVE;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_2_ >= 6 ? alt : blockIcon;
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, int x, int y, int z) {
		/*
		 * for(int i = 2; i <= 5; ++i) { final ForgeDirection dir =
		 * ForgeDirection.getOrientation(i); for(int yi = -1; yi <= 1; ++yi) { final int
		 * meta = world.getBlockMetadata(x+dir.offsetX, y+yi, z+dir.offsetZ); final
		 * Block bl = world.getBlock(x+dir.offsetX, y+yi, z+dir.offsetZ); if(bl
		 * instanceof BlockRailBase && meta >= 6) { return 0.5f; } } }
		 */
		return 0.7f;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public boolean isFlexibleRail(IBlockAccess world, int x, int y, int z) {
		return curvy;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b) {
		final int meta = w.getBlockMetadata(x, y, z);
		if (curvy && meta >= 6) {
			// TODO: Track direction switching.
		} else if (isPowered()) {
			boolean isNearPower = false;
			for (final ForgeDirection dir : new ForgeDirection[] { ForgeDirection.UNKNOWN, ForgeDirection.DOWN,
					ForgeDirection.WEST, ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.NORTH })
				if (w.isBlockIndirectlyGettingPowered(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
					isNearPower = true;
			if (isNearPower)
				w.setBlockMetadataWithNotify(x, y, z, meta | 8, 3);
			else
				w.setBlockMetadataWithNotify(x, y, z, meta & (~8), 3);
			for (final Object o : w.getEntitiesWithinAABB(EntityMinecart.class,
					AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.5, 1).offset(x, y, z)))
				onMinecartPass(w, (EntityMinecart) o, x, y, z); // Liar liar Forge on fire.
		}
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		final int meta = w.getBlockMetadata(x, y, z);
		if (w.isRemote && isPowered() && IntBits.areAllSet(meta, 8) && r.nextInt(4) == 0) {
			boolean spawnForce = false;
			for (int i = 2; i <= 5; ++i) {
				final ForgeDirection dir = ForgeDirection.getOrientation(i);
				if (NyxBlockRail.connects(dir, meta, false) && (spawnForce || r.nextBoolean())) {
					final double xL = 0.25 + dir.offsetX / 4, zL = 0.25 + dir.offsetZ / 4;
					spawnOnParticle(w, x + xL + r.nextDouble() / 2, y + 0.1, z + zL + r.nextDouble() / 2);
					return;
				} else
					spawnForce = true;
			}
		}
	}

	public Block register() {
		IaSRegistration.register(this);
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		if (isPowered() || !curvy)
			alt = reg.registerIcon(getTextureName() + "On");
		else
			alt = reg.registerIcon(getTextureName() + "Curved");
	}

	/**
	 * Called occasionally to spawn a decorative particle while a powered rail is
	 * on.
	 */
	public void spawnOnParticle(World w, double x, double y, double z) {
		IaSFxManager.spawnParticle(w, "cortraSmoke", x, y, z, 0, 0, 0, false, true);
	}
}
