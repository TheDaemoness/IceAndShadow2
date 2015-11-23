package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockOreNifelhium extends NyxBlockOre {

	public NyxBlockOreNifelhium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 2);
		setHardness(20.0F);
		setLuminescence(0.3F);
		setLightColor(0.9F, 0.9F, 0.9F);
		setResistance(15.0F);
		GameRegistry.addSmelting(this, new ItemStack(NyxItems.nifelhiumPowder,1), 2);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.nifelhiumPowder, world.rand.nextInt(1+fortune)>=2?2:1));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 7;
	}
}
