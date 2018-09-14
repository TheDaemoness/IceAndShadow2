package iceandshadow2.nyx.blocks.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.IIaSSignalReceiverBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockTeRefCount;
import iceandshadow2.render.block.RenderBlockAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockAntenna extends IaSBaseBlockTeRefCount implements IIaSSignalReceiverBlock {

	public NyxBlockAntenna(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(Blocks.stone.getExplosionResistance(null));
		setHardness(Blocks.stone.getBlockHardness(null, 0, 0, 0));
		setLightOpacity(4);
		setStepSound(Block.soundTypeMetal);
		setLightLevel(0.5f);
		setTickRandomly(true);
		setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 1.1f, 0.6f);
		fullCube = false;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderType() {
		return RenderBlockAntenna.id;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z, int s) {
		return w.getBlockMetadata(x, y, z) > 0 ? 15 : 0;
	}

	@Override
	public void onSignalStart(World w, int x, int y, int z, Object data) {
		addRef(w, x, y, z);
	}

	@Override
	public void onSignalStop(World w, int x, int y, int z, Object data) {
		rmRef(w, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
		return true;
	}
}
