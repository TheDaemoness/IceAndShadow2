package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.nyx.NyxItems;

public class NyxBlockOreDevora extends NyxBlockOre {

	public NyxBlockOreDevora(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(4.0F);
		this.setLuminescence(0.3F);
		this.setLightColor(1.0F, 1.0F, 0.75F);
		this.setResistance(1.0F);
		this.setTickRandomly(true);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		int e = world.rand.nextInt(5);
		for(int i = 0; i < e; ++i)
			is.add(new ItemStack(NyxItems.devora,1,1));
		if(world.rand.nextInt(Math.max(1,5-world.rand.nextInt(fortune))) == 0)
			is.add(new ItemStack(NyxItems.devora,1));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}
}
