package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreCortra extends NyxBlockOre {

	public NyxBlockOreCortra(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		setHardness(20.0F);
		setLuminescence(0.2F);
		setLightColor(0.0F, 0.75F, 1.0F);
		setResistance(10.0F);
		GameRegistry.addSmelting(this, new ItemStack(NyxItems.cortra, 4), 4);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		final int e = world.rand.nextInt(3 + fortune) + 2;
		for (int i = 0; i < e; ++i) {
			if (world.rand.nextInt(3) == 0)
				is.add(new ItemStack(Items.redstone, 2));
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
