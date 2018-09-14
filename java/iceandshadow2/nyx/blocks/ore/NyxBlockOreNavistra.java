package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreNavistra extends NyxBlockOre implements IIaSAspect {

	public NyxBlockOreNavistra(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 3);
		setHardness(Blocks.obsidian.getBlockHardness(null, 0, 0, 0) * 2);
		setResistance(120.0F);
		GameRegistry.addSmelting(this, new ItemStack(NyxItems.navistraShard, 2), 2);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NAVISTRA;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.navistraShard, world.rand.nextInt(1 + fortune) >= 2 ? 2 : 1));
		if (world.rand.nextInt(3) == 0)
			is.add(new ItemStack(NyxItems.navistraShard, 1));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 5;
	}
}
