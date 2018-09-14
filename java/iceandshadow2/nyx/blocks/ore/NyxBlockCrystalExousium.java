package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.nyx.NyxItems;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockCrystalExousium extends NyxBlockCrystal {

	public NyxBlockCrystalExousium(String texName) {
		super(texName);
		setLuminescence(0.2F);
		setLightColor(0.0F, 0.5F, 0.4F);
		setResistance(1.5F);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.EXOUSIUM;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		is.add(new ItemStack(NyxItems.exousium, 2 + 2 * world.rand.nextInt(2) + world.rand.nextInt(2), 0));
		return is;
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 2;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
		return super.getSelectedBoundingBoxFromPool(w, x, y, z).contract(0.1, 0.15, 0.1).offset(0.0, -0.15, 0.0);
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity ent) {
		if (ent instanceof EntityLivingBase)
			((EntityLivingBase) ent).addPotionEffect(new PotionEffect(Potion.wither.id, 81, 2));
		super.onEntityCollidedWithBlock(w, x, y, z, ent);
	}
}
