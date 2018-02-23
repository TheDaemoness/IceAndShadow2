package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSApiTransmuteLens;
import iceandshadow2.ias.api.IIaSSignalReceiverBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.blocks.IaSBaseBlockTe;
import iceandshadow2.ias.blocks.IaSBaseBlockTeRefCount;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockUnstableDevora;
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

public class NyxBlockDetonator extends IaSBaseBlockSingle implements IIaSSignalReceiverBlock {
	
	public NyxBlockDetonator(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		this.setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 0.15f, 0.6f);
		this.setLightOpacity(0);
		this.fullCube = false;
	}

	@Override
	public void onSignalStart(World w, int x, int y, int z, Object data) {
		IaSBlockHelper.breakBlock(w, x, y, z, false);
		if(!w.isRemote)
			w.newExplosion(null, x+0.5, y, z+0.5, 0.5f, true, true);
	}

	@Override
	public void onSignalStop(World w, int x, int y, int z, Object data) {
	}
	
	
	public void tryBlowUp(World w, int x, int y, int z) {
		if(w.isBlockIndirectlyGettingPowered(x, y, z))
			onSignalStart(w, x, y, z, null);
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		tryBlowUp(w, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z,
			Block bl) {
		tryBlowUp(w, x, y, z);
	}

	
}
