package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.util.IaSRegistration;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDirectional;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockSilkBerryPod extends BlockCocoa implements IIaSModName, IIaSAspect {

	protected IIcon[] icons;

	public NyxBlockSilkBerryPod(String par1) {
		super();
		setLightLevel(0.2F);
		setResistance(0.5F);
		setHardness(0.1F);
		setStepSound(Block.soundTypeCloth);
		setBlockName("nyx" + par1);
		setBlockTextureName(getTextureName());
	}

	/**
	 * Can this block stay at this position. Similar to canPlaceBlockAt except gets
	 * checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
		final int l = BlockDirectional.getDirection(par1World.getBlockMetadata(par2, par3, par4));
		par2 += Direction.offsetX[l];
		par4 += Direction.offsetZ[l];
		final Block i1 = par1World.getBlock(par2, par3, par4);
		return i1 == NyxBlocks.infestLog;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getCocoaIcon(int par1) {
		if (par1 < 0 || par1 >= icons.length)
			par1 = icons.length - 1;

		return icons[par1];
	}

	/**
	 * Get the block's damage value (for use with pick block).
	 */
	@Override
	public int getDamageValue(World par1World, int par2, int par3, int par4) {
		return 0;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int p_149690_5_, int fortune) {
		final ArrayList<ItemStack> dropped = new ArrayList<ItemStack>();
		final int j1 = BlockCocoa.func_149987_c(p_149690_5_);
		byte b0 = 0;

		if (j1 >= 2) {
			b0 = (byte) (2 + world.rand.nextInt(2));
			if (world.rand.nextInt(4) == 0)
				dropped.add(new ItemStack(NyxItems.silkBerries, 1, 1));
		}

		dropped.add(new ItemStack(NyxItems.silkBerries, 1, 1));
		for (int k1 = 0; k1 < b0; ++k1)
			dropped.add(new ItemStack(NyxItems.silkBerries, 1));
		return dropped;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		return blockIcon;
	}

	@Override
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return NyxItems.silkBerries;
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	public final Block register() {
		IaSRegistration.register(this);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[3];
		for (int i = 0; i < 3; ++i)
			icons[i] = reg.registerIcon(getTextureName() + (i + 1));
		blockIcon = icons[2];
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par5Random.nextBoolean())
			super.updateTick(par1World, par2, par3, par4, par5Random);
	}
}
