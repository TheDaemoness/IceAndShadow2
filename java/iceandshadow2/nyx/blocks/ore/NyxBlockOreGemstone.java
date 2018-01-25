package iceandshadow2.nyx.blocks.ore;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreGemstone extends NyxBlockOre {

	public NyxBlockOreGemstone(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		setHardness(4.0F);
		setLuminescence(0.2F);
		setResistance(1.0F);
		GameRegistry.addSmelting(this, new ItemStack(Items.diamond, 2), 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(Items.diamond, world.rand.nextInt(fortune + 5) >= 5 ? 2 : 1));
		if (world.rand.nextInt(fortune + 6) >= 5) {
			is.add(new ItemStack(Items.emerald, 1));
		}
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}
}
