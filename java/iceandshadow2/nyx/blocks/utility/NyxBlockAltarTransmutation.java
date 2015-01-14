package iceandshadow2.nyx.blocks.utility;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockTileEntity;
import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

//See, this is why we need some form of MI in Java, be it mixins or traits.
public class NyxBlockAltarTransmutation extends IaSBaseBlockTileEntity {

	@SideOnly(Side.CLIENT)
	protected IIcon side, bot;
	
	public NyxBlockAltarTransmutation(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setLightLevel(0.4F);
		this.setResistance(10.0F);
		this.setHardness(5.0F);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		this.setLightOpacity(7);
		this.setStepSound(Block.soundTypeStone);
		this.setTickRandomly(false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_,
			int p_149673_3_, int p_149673_4_, int side) {
		if(side == 0)
			return bot;
		if(side == 1)
			return this.blockIcon;
		return this.side;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTexName()+"Top");
		this.side = reg.registerIcon(this.getTexName()+"Side");
		this.bot = reg.registerIcon(this.getTexName()+"Bottom");
	}

	@Override
	public void onNeighborBlockChange(World w, int x,
			int y, int z, Block bl) {
		Block check = w.getBlock(x, y+1, z);
		if(check != null && check.getMaterial() != Material.air) {
			TileEntity te = w.getTileEntity(x, y, z);
			if(!(te instanceof NyxTeTransmutationAltar))
				return;
			NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
			tte.dropItems();
			w.setTileEntity(x, y, z, tte);
			w.setBlockToAir(x, y, z);
			w.setBlock(x, y, z, this);
		}
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y,
			int z, EntityPlayer ep, int side, float xl,
			float yl, float zl) {
		Block check = w.getBlock(x, y+1, z);
		if(check != null && check.getMaterial() != Material.air) {
			IaSPlayerHelper.messagePlayer(ep, "That altar needs an empty space above it to work.");
			return true;
		}
		TileEntity te = w.getTileEntity(x, y, z);
		if(!(te instanceof NyxTeTransmutationAltar))
			return false;
		NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
		ItemStack is = ep.getEquipmentInSlot(0);
		if(is == null) {
			ItemStack its = tte.handleRemove();
			if(its != null)
				IaSPlayerHelper.giveItem(ep, its);
			w.setTileEntity(x, y, z, tte);
			return true;
		}
		if(!tte.handlePlace(is.copy()))
			return false;
		else
			is.stackSize = 0;
		if(tte.canAttemptTransmutation()) {
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
			if(tte.handler == null) {
				w.setTileEntity(x, y, z, tte);
				return true;
			}
			tte.scheduleUpdate(x, y, z, tte.handler.getTransmutationTime(tte.target, tte.catalyst));
			w.setTileEntity(x, y, z, tte);
			return true;
		}
		w.setTileEntity(x, y, z, tte);
		return false;
	}

	public void doTransmutation(World w, int x, int y, int z, Random r) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(!(te instanceof NyxTeTransmutationAltar))
			return;
		NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
		if(!tte.canAttemptTransmutation())
			return;
		if(tte.handler == null)
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
		if(tte.handler == null)
			return;
		List<ItemStack> l_ist = tte.handler.getTransmutationYield(tte.target, tte.catalyst, w);
		if(tte.target.stackSize <= 0)
			tte.target = null;
		if(tte.catalyst.stackSize <= 0)
			tte.catalyst = null;
		if(l_ist != null) {
			TileEntityHopper teh = null;
			if(w.getTileEntity(x, y-1, z) instanceof TileEntityHopper)
				teh = (TileEntityHopper)w.getTileEntity(x, y-1, z);
			for(ItemStack is : l_ist) {
				if(teh != null) {
					int i;
					for(i = 0; i < teh.getSizeInventory(); ++i) {
						if(teh.isItemValidForSlot(i, is))
							break;
					}
					if(i != teh.getSizeInventory()) {
						teh.setInventorySlotContents(i, is);
						continue;
					}
				}
				if(!w.isRemote) {
					EntityItem ei = new EntityItem(w, x+0.5, y+0.8, z+0.5, is);
					ei.lifespan = Integer.MAX_VALUE-1;
					w.spawnEntityInWorld(ei);
				}
			}
			if(teh != null)
				w.setTileEntity(x, y-1, z, teh);
		}
		if(tte.canAttemptTransmutation()) {
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
			if(tte.handler == null) {
				w.setTileEntity(x, y, z, tte);
				return;
			}
			tte.scheduleUpdate(x, y, z, tte.handler.getTransmutationTime(tte.target, tte.catalyst));
		}
		w.setTileEntity(x, y, z, tte);
	}
	
	@Override
	public void breakBlockPre(World w, int x, int y, int z, Block bl, int meta) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(!(te instanceof NyxTeTransmutationAltar))
			return;
		NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
		tte.dropItems();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new NyxTeTransmutationAltar();
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		TileEntity te = w.getTileEntity(x, y, z);
		if(!(te instanceof NyxTeTransmutationAltar))
			return;
		NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar)te;
		if(tte.handler != null && tte.canAttemptTransmutation())
			Blocks.ender_chest.randomDisplayTick(w, x, y, z, r);
	}
	
	
}
