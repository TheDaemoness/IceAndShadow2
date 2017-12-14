package iceandshadow2.nyx.blocks.technical;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.ias.util.IaSPlayerHelper;

public class NyxBlockFakeFurnace extends IaSBaseBlockTechnical {

	public NyxBlockFakeFurnace() {
		super(EnumIaSModule.IAS, "FakeFurnace", Material.rock);
		this.setHarvestLevel("pickaxe", 0);
		setHardness(Blocks.furnace.getBlockHardness(null, 0, 0, 0));
		setResistance(Blocks.furnace.getExplosionResistance(null));
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> li = new ArrayList<ItemStack>();
		li.add(new ItemStack(Blocks.furnace, 1));
		return li;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.furnace.getIcon(side, meta);
	}

	@Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_,
			EntityPlayer ep, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		IaSPlayerHelper.messagePlayer(ep,
				"It's too cold to use a traditional furnace in Nyx. Find another way to heat your items.");
		return true;
	}

	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
	}
}
