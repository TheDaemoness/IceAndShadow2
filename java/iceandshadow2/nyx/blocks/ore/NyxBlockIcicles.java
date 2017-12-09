package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.nyx.NyxItems;
import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockIcicles extends NyxBlockCrystal {
	public NyxBlockIcicles(String texName) {
		super(texName);
		setLuminescence(0.1F);
		setLightColor(0.8F, 0.8F, 1.0F);
		setResistance(1.5F);
		slipperiness = 2.0F;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.FROZEN;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.icicle, 2 + world.rand.nextInt(2), 0));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return getCollisionBoundingBoxFromPool(w, x, y, z);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onEntityWalking(World w, int x, int y, int z, Entity e) {
		onFallenUpon(w, x, y, z, e, 0);
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity e, float distance) {
		e.attackEntityFrom(IaSDamageSources.dmgStalagmite,
				3 + 2 * world.difficultySetting.getDifficultyId() + distance * 2);
		super.onFallenUpon(world, x, y, z, e, 3 + 2 * world.difficultySetting.getDifficultyId() + distance * 2);
	}
}
