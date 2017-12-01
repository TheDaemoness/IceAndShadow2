package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreDraconium extends NyxBlockOre {

	public NyxBlockOreDraconium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 3);
		setHardness(10.0F);
		setLuminescence(0.3F);
		setLightColor(0.5F, 0.0F, 0.0F);
		setResistance(7.5F);
		GameRegistry.addSmelting(this, new ItemStack(NyxItems.draconium), 1);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.draconium));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 0;
	}
}
