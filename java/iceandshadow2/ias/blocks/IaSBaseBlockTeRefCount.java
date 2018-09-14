package iceandshadow2.ias.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.nyx.tileentities.NyxTeRefCount;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class IaSBaseBlockTeRefCount extends IaSBaseBlockTe {

	@SideOnly(Side.CLIENT)
	protected IIcon iconRef;

	public IaSBaseBlockTeRefCount(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
	}

	public void addRef(IBlockAccess w, int x, int y, int z) {
		final TileEntity e = w.getTileEntity(x, y, z);
		if (e instanceof NyxTeRefCount)
			((NyxTeRefCount) e).addRef();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new NyxTeRefCount();
	}

	@Override
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side) {
		if (IntBits.areAllSet(w.getBlockMetadata(x, y, z), 1))
			return iconRef;
		return blockIcon;
	}

	public void onReferenceBegin(World w, int x, int y, int z) {
		w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z) | 1, 3);
	}

	public void onReferenceEnd(World w, int x, int y, int z) {
		w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z) & (~1), 3);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		iconRef = reg.registerIcon(getTextureName() + "On");
	}

	public void rmRef(IBlockAccess w, int x, int y, int z) {
		final TileEntity e = w.getTileEntity(x, y, z);
		if (e instanceof NyxTeRefCount)
			((NyxTeRefCount) e).rmRef();
	}
}
