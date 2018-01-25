package iceandshadow2.nyx.blocks.utility;

import java.util.ArrayList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.IIaSModName;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
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
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.ANCIENT;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> arl = new ArrayList<ItemStack>(2);
		arl.add(new ItemStack(getItem(world, x, y, z)));
		if (metadata > 0) {
			fortune = Math.min(metadata - 1, fortune * 2);
			arl.add(new ItemStack(NyxItems.cortra, 1 + fortune + world.rand.nextInt(metadata - fortune)));
		}
		return arl;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0)
			return NyxBlocks.sanctifiedObsidian.getIcon(side, meta);
		if (side == 1)
			return blockIcon;
		if ((side - 1) * 2 < meta + 1)
			return side2;
		if ((side - 1) * 2 == meta + 1)
			return side1;
		return side0;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 2 + world.getBlockMetadata(x, y, z) / 2;
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 15);
	}

	@Override
	public String getTextureName() {
		return ((IIaSModName) NyxBlocks.transmutationAltar).getTextureName();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int side, float xl, float yl,
			float zl) {
		final ItemStack is = ep.getCurrentEquippedItem();
		if (is != null && is.getItem() == NyxItems.cortra) {
			final int meta = w.getBlockMetadata(x, y, z);
			is.stackSize -= 1;
			if (meta == 7) {
				w.setBlock(x, y, z, NyxBlocks.transmutationAltar);
			} else {
				w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z) + 1, 2);
			}
		} else {
			IaSPlayerHelper.messagePlayer(ep, "It appears to be broken. The sides are covered in an aquamarine dust.");
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = reg.registerIcon(getTextureName() + "TopDead");
		side0 = reg.registerIcon(getTextureName() + "SideEmpty");
		side1 = reg.registerIcon(getTextureName() + "SideHalf");
		side2 = reg.registerIcon(getTextureName() + "Side");
	}
}
