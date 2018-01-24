package iceandshadow2.ias.blocks;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class IaSBaseBlockStairs extends BlockStairs implements IIaSModName, IIaSAspect {

	public final IaSBaseBlock SOURCE;
	public final EnumIaSModule MODULE;

	public IaSBaseBlockStairs(IaSBaseBlock bl) {
		this(bl, 0);
	}

	public IaSBaseBlockStairs(IaSBaseBlock bl, int meta) {
		super(bl, meta);
		MODULE = bl.MODULE;
		SOURCE = bl;
	}

	@Override
	public float getAmbientOcclusionLightValue() {
		return SOURCE.getAmbientOcclusionLightValue();
	}

	@Override
	public EnumIaSAspect getAspect() {
		return SOURCE.getAspect();
	}

	@Override
	public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
		return SOURCE.getBlockHardness(p_149712_1_, p_149712_2_, p_149712_3_, p_149712_4_);
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return SOURCE.getBlocksMovement(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_);
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX,
			double explosionY, double explosionZ) {
		return SOURCE.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return SOURCE.getFlammability(world, x, y, z, face);
	}

	@Override
	public int getHarvestLevel(int metadata) {
		return SOURCE.getHarvestLevel(metadata);
	}

	@Override
	public String getHarvestTool(int metadata) {
		return SOURCE.getHarvestTool(metadata);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_) {
		return SOURCE.getIcon(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_, p_149673_5_);
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return SOURCE.getIcon(p_149691_1_, p_149691_2_);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(SOURCE);
	}

	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
		return SOURCE.getLightOpacity(world, x, y, z);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return (1 + SOURCE.getLightValue(world, x, y, z)) / 2;
	}

	@Override
	public int getMobilityFlag() {
		return SOURCE.getMobilityFlag();
	}

	@Override
	public String getModName() {
		return SOURCE.getModName() + "Slab";
	}

	@Override
	public String getTextureName() {
		return SOURCE.getTextureName();
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_,
			Explosion p_149723_5_) {
		SOURCE.onBlockDestroyedByExplosion(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_, p_149723_5_);
	}

	@Override
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_,
			Entity p_149670_5_) {
		SOURCE.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
	}

	@Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_,
			Entity p_149724_5_) {
		SOURCE.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
	}

	@Override
	public void onFallenUpon(World p_149746_1_, int p_149746_2_, int p_149746_3_, int p_149746_4_, Entity p_149746_5_,
			float p_149746_6_) {
		SOURCE.onFallenUpon(p_149746_1_, p_149746_2_, p_149746_3_, p_149746_4_, p_149746_5_, p_149746_6_);
	}
}
