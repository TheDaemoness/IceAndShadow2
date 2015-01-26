package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreCortra extends NyxBlockOre {

	public NyxBlockOreCortra(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(20.0F);
		this.setLuminescence(0.2F);
		this.setLightColor(0.0F, 0.75F, 1.0F);
		this.setResistance(10.0F);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		final int e = world.rand.nextInt(2 + fortune) + 2;
		for (int i = 0; i < e; ++i) {
			if (world.rand.nextBoolean())
				is.add(new ItemStack(Items.redstone));
			else
				is.add(new ItemStack(NyxItems.cortra));
		}
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 3;
	}
}
