package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlockOreNavistra extends NyxBlockOre {

	public NyxBlockOreNavistra(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 3);
		this.setHardness(40.0F);
		this.setResistance(120.0F);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.navistraShard));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 5;
	}
}
