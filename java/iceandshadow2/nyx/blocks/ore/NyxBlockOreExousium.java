package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockOreExousium extends NyxBlockOre {

	@SideOnly(Side.CLIENT)
	IIcon iconEmpty;

	public NyxBlockOreExousium(String texName) {
		super(texName);
		setHardness(20.0F);
		setLuminescence(1.0F);
		setLightColor(0.9F, 1.0F, 0.9F);
		setResistance(2.5F);
		setTickRandomly(true);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x,
			int y, int z, int metadata) {
		return false;
	}

	@Override
	public void onBlockHarvested(World w, int x,
			int y, int z, int fortune,
			EntityPlayer pwai) {
		if(w.rand.nextInt(3+fortune) <= fortune)
			IaSPlayerHelper.giveItem(pwai, new ItemStack(NyxItems.exousium,1,0));
		IaSPlayerHelper.giveItem(pwai, new ItemStack(NyxItems.exousium,1,0));
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 5;
	}

	@Override
	public IIcon getIcon(int side, int m) {
		if (side == 0 || side == 1)
			return NyxBlocks.stone.getIcon(side, m);
		else {
			if (m != 0)
				return this.iconEmpty;
			return this.blockIcon;
		}
	}

	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z,
			Explosion e) {
		w.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta) {
		if (meta == 0)
			w.setBlock(x, y, z, this, 1, 0x2);
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(getTexName());
		this.iconEmpty = reg.registerIcon(getTexName() + "Empty");
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if (w.getBlockMetadata(x, y, z) != 0 && r.nextBoolean()) {
			while(true) {
				if(w.getBlock(x + 1, y, z) == NyxBlocks.exousicWater)
					break;
				if(w.getBlock(x - 1, y, z) == NyxBlocks.exousicWater)
					break;
				if(w.getBlock(x, y, z + 1) == NyxBlocks.exousicWater)
					break;
				if(w.getBlock(x, y, z - 1) == NyxBlocks.exousicWater)
					break;
				return;
			}
			w.setBlock(x, y, z, this, 0, 0x2);
		}
	}
}
