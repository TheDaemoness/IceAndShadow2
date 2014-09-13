package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlockOreExousium extends NyxBlockOre {

	public NyxBlockOreExousium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(20.0F);
		this.setLuminescence(1.0F);
		this.setLightColor(0.9F, 1.0F, 0.9F);
		this.setResistance(2.5F);
	}	

	@Override
	public IIcon getIcon(int side, int m) {
		if(side == 0 || side == 1)
			return NyxBlocks.stone.getIcon(side, m);
		else
			return this.blockIcon;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		if(world.rand.nextInt(3+fortune) != 0)
			is.add(new ItemStack(NyxItems.exousium,1,0));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 5;
	}
}
