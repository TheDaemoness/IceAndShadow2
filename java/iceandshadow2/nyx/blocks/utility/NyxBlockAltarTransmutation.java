package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.blocks.IaSBaseBlockTileEntity;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;
import iceandshadow2.util.IaSPlayerHelper;
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

//See, this is why we need some form of MI in Java, be it mixins or traits.
public class NyxBlockAltarTransmutation extends IaSBaseBlockTileEntity {

	@SideOnly(Side.CLIENT)
	protected IIcon side, bot;

	public NyxBlockAltarTransmutation(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(Blocks.obsidian.getExplosionResistance(null));
		setHardness(Blocks.obsidian.getBlockHardness(null, 0, 0, 0));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setLightOpacity(7);
		setStepSound(Block.soundTypeStone);
		setTickRandomly(false);
	}
	
	@Override
	public int getLightValue() {
		return 6;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> arl = new ArrayList<ItemStack>(2);
		arl.add(new ItemStack(NyxBlocks.transmutationAltarBroken.getItem(world, x, y, z)));
		fortune = Math.min(4, fortune);
		arl.add(new ItemStack(NyxItems.cortra, 4+fortune+world.rand.nextInt(5-fortune)));
		return arl;
	}

	@Override
	public void breakBlockPre(World w, int x, int y, int z, Block bl, int meta) {
		final TileEntity te = w.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar))
			return;
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		tte.dropItems();
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new NyxTeTransmutationAltar();
	}

	public void doTransmutation(World w, int x, int y, int z, Random r) {
		final TileEntity te = w.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar))
			return;
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		if (!tte.canAttemptTransmutation())
			return;
		if (tte.handler == null)
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
		if (tte.handler == null)
			return;
		final List<ItemStack> l_ist = tte.handler.getTransmuteYield(tte.target, tte.catalyst, w);
		if (tte.target.stackSize <= 0)
			tte.target = null;
		if (tte.catalyst.stackSize <= 0)
			tte.catalyst = null;
		if (l_ist != null) {
			TileEntityHopper teh = null;
			if (w.getTileEntity(x, y - 1, z) instanceof TileEntityHopper)
				teh = (TileEntityHopper) w.getTileEntity(x, y - 1, z);
			if (l_ist.size() == 1 && tte.target == null) {
				if (teh == null || w.isBlockIndirectlyGettingPowered(x, y - 1, z))
					if (l_ist.size() == 1 && tte.target == null) {
						tte.target = l_ist.get(0);
						l_ist.clear();
					}
			}
			for (final ItemStack is : l_ist) {
				if (teh != null && !w.isBlockIndirectlyGettingPowered(x, y - 1, z)) {
					int i;
					for (i = 0; i < teh.getSizeInventory(); ++i) {
						if (teh.getStackInSlot(i) == null)
							break;
					}
					if (i != teh.getSizeInventory()) {
						teh.setInventorySlotContents(i, is);
						continue;
					}
				}
				if (!w.isRemote) {
					final EntityItem ei = new EntityItem(w, x + 0.5, y + 0.8, z + 0.5, is);
					ei.lifespan = Integer.MAX_VALUE - 1;
					w.spawnEntityInWorld(ei);
				}
			}
			if (teh != null)
				w.markBlockForUpdate(x, y - 1, z);
		}
		if (tte.canAttemptTransmutation()) {
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
			if (tte.handler == null) {
				w.markBlockForUpdate(x, y, z);
				return;
			}
			tte.scheduleUpdate(x, y, z, tte.handler.getTransmuteTime(tte.target, tte.catalyst));
		}
		w.markBlockForUpdate(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess w, int x, int y, int z, int side) {
		final TileEntity e = w.getTileEntity(x, y, z);
		if (e instanceof NyxTeTransmutationAltar) {
			final ItemStack tar = ((NyxTeTransmutationAltar) e).target;
			if (tar != null && tar.getItem() instanceof IIaSApiTransmuteLens) {
				final IIcon retval = ((IIaSApiTransmuteLens) tar.getItem()).getAltarTopTexture(tar);
				if (retval != null)
					return retval;
			}
		}
		return getIcon(side, 0);
	}
	
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 15);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0)
			return NyxBlocks.sanctifiedObsidian.getIcon(side, meta);
		if (side == 1)
			return this.blockIcon;
		return this.side;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int side, float xl, float yl,
			float zl) {
		final Block check = w.getBlock(x, y + 1, z);
		if (check != null && !(check.getMaterial() instanceof MaterialTransparent)) {
			IaSPlayerHelper.messagePlayer(ep, "That altar needs an empty space above it to work.");
			return false;
		}
		final TileEntity te = w.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar))
			return false;
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		final ItemStack is = ep.getEquipmentInSlot(0);
		if (is == null) {
			final ItemStack its = tte.handleRemove(ep.isSneaking());
			if (its != null)
				IaSPlayerHelper.giveItem(ep, its);
			w.markBlockForUpdate(x, y, z);
			return true;
		}
		if (!tte.handlePlace(is.copy()))
			return false;
		else
			is.stackSize = 0;
		if (tte.canAttemptTransmutation()) {
			tte.handler = IaSRegistry.getHandlerTransmutation(tte.target, tte.catalyst);
			if (tte.handler != null) {
				tte.scheduleUpdate(x, y, z, tte.handler.getTransmuteTime(tte.target, tte.catalyst));
			}
			w.markBlockForUpdate(x, y, z);
			return true;
		}
		w.markBlockForUpdate(x, y, z);
		return false;
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		final Block check = w.getBlock(x, y + 1, z);
		if (check != null && !(check.getMaterial() instanceof MaterialTransparent)) {
			final TileEntity te = w.getTileEntity(x, y, z);
			if (!(te instanceof NyxTeTransmutationAltar))
				return;
			final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
			tte.dropItems();
			w.markBlockForUpdate(x, y, z);
			w.setBlockToAir(x, y, z);
			w.setBlock(x, y, z, this);
		}
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		final TileEntity te = w.getTileEntity(x, y, z);
		if (!(te instanceof NyxTeTransmutationAltar))
			return;
		final NyxTeTransmutationAltar tte = (NyxTeTransmutationAltar) te;
		if (tte.handler != null && tte.canAttemptTransmutation())
			Blocks.ender_chest.randomDisplayTick(w, x, y+1, z, r);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(getTexName() + "Top");
		this.side = reg.registerIcon(getTexName() + "Side");
	}

}
