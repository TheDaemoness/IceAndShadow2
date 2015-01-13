package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreDraconium extends NyxBlockOre {

	public NyxBlockOreDraconium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 3);
		this.setHardness(10.0F);
		this.setLuminescence(0.3F);
		this.setLightColor(0.5F, 0.0F, 0.0F);
		this.setResistance(7.5F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.draconium));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 0;
	}
}
