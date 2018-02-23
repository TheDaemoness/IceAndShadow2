package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.api.IIaSSignalReceiverBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.blocks.IaSBaseBlockTe;
import iceandshadow2.ias.blocks.IaSBaseBlockTeRefCount;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.tileentities.NyxTeTransfusionAltar;
import iceandshadow2.render.block.RenderBlockAntenna;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockAntenna extends IaSBaseBlockTeRefCount implements IIaSSignalReceiverBlock {
	
	public NyxBlockAntenna(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(Blocks.stone.getExplosionResistance(null));
		setHardness(Blocks.stone.getBlockHardness(null, 0, 0, 0));
		setLightOpacity(4);
		setStepSound(Block.soundTypeMetal);
		this.setLightLevel(0.5f);
		this.setTickRandomly(true);
		this.setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 1.1f, 0.6f);
		this.fullCube = false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderType() {
		return RenderBlockAntenna.id;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
		return true;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z,
			int s) {
		return w.getBlockMetadata(x, y, z) > 0?15:0;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public void onSignalStart(World w, int x, int y, int z, Object data) {
		this.addRef(w, x, y, z);
	}

	@Override
	public void onSignalStop(World w, int x, int y, int z, Object data) {
		this.rmRef(w, x, y, z);
	}
}
