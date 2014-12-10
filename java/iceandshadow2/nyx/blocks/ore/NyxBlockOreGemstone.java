package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreGemstone extends NyxBlockOre {

	public NyxBlockOreGemstone(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(4.0F);
		this.setLuminescence(0.2F);
		this.setResistance(1.0F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(Items.diamond,1+(world.rand.nextInt(fortune+5)>=5?1:0)));
		if(world.rand.nextInt(fortune+6)>=5)
			is.add(new ItemStack(Items.emerald,1));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}
}
