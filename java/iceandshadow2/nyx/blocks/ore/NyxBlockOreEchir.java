package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import iceandshadow2.nyx.NyxItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreEchir extends NyxBlockOre {

	public NyxBlockOreEchir(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 1);
		setHardness(10.0F);
		setLuminescence(0.2F);
		setLightColor(0.75F, 1.0F, 0.75F);
		setResistance(20.0F);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		final int e = world.rand.nextInt(2 + fortune) + 1;
		for (int i = 0; i < e; ++i)
			is.add(new ItemStack(NyxItems.echirDust));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}
}
