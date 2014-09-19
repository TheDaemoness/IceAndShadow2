package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

public class NyxBlockOreExousium extends NyxBlockOre {
	
	@SideOnly(Side.CLIENT)
	IIcon iconEmpty;

	public NyxBlockOreExousium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(20.0F);
		this.setLuminescence(1.0F);
		this.setLightColor(0.9F, 1.0F, 0.9F);
		this.setResistance(2.5F);
		this.setTickRandomly(true);
	}
		
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTexName());
		iconEmpty = reg.registerIcon(this.getTexName()+"Empty");
	}

	@Override
	public void onBlockDestroyedByPlayer(World w, int x,
			int y, int z, int meta) {
		if(meta == 0)
			w.setBlock(x, y, z, this, 1, 0x2);
	}

	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		if(w.getBlockMetadata(x, y, z) != 0 && r.nextBoolean()) {
			w.setBlock(x, y, z, this, 0, 0x2);
		}
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x,
			int y, int z, int metadata) {
		return false;
	}

	@Override
	public IIcon getIcon(int side, int m) {
		if(side == 0 || side == 1)
			return NyxBlocks.stone.getIcon(side, m);
		else {
			if(m != 0)
				return iconEmpty;
			return this.blockIcon;
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		if(metadata != 0) {
			is.add(new ItemStack(NyxBlocks.stone,1));
			return is;
		}
		if(world.rand.nextInt(3+fortune) != 0)
			is.add(new ItemStack(NyxItems.exousium,1,0));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 5;
	}
}
