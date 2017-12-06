package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.IIaSModName;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.tileentities.NyxTeTransmutationAltar;
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

public class NyxBlockAltarTransmutationBroken extends IaSBaseBlockSingle {

	@SideOnly(Side.CLIENT)
	protected IIcon side0, side1, side2, bot;

	public NyxBlockAltarTransmutationBroken(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setLightLevel(0.4F);
		setResistance(Blocks.obsidian.getExplosionResistance(null));
		setHardness(Blocks.obsidian.getBlockHardness(null, 0, 0, 0));
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setLightOpacity(7);
		setStepSound(Block.soundTypeStone);
		setTickRandomly(false);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0)
			return NyxBlocks.sanctifiedObsidian.getIcon(side, meta);
		if (side == 1)
			return this.blockIcon;
		if ((side-1)*2 < meta+1)
			return this.side2;
		if ((side-1)*2 == meta+1)
			return this.side1;
		return this.side0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 2+world.getBlockMetadata(x, y, z)/2;
	}
	
	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 15);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> arl = new ArrayList<ItemStack>(2);
		arl.add(new ItemStack(this.getItem(world, x, y, z)));
		if(metadata > 0) {
			fortune = Math.min(metadata-1, fortune*2);
			arl.add(new ItemStack(NyxItems.cortra, 1+fortune+world.rand.nextInt(metadata-fortune)));
		}
		return arl;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int side, float xl, float yl,
			float zl) {
		final ItemStack is = ep.getCurrentEquippedItem();
		if(is != null && is.getItem() == NyxItems.cortra) {
			final int meta = w.getBlockMetadata(x, y, z);
			if(meta == 7)
				w.setBlock(x, y, z, NyxBlocks.transmutationAltar);
			else
				w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 2);
		} else
			IaSPlayerHelper.messagePlayer(ep, "It appears to be broken. The sides are covered in an aquamarine dust.");
		return true;
	}
	
	@Override
	public String getTexName() {
		return ((IIaSModName)NyxBlocks.transmutationAltar).getTexName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(getTexName() + "TopDead");
		this.side0 = reg.registerIcon(getTexName() + "SideEmpty");
		this.side1 = reg.registerIcon(getTexName() + "SideHalf");
		this.side2 = reg.registerIcon(getTexName() + "Side");
	}
}
