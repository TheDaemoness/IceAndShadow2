package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.util.IaSBlockHelper;
import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class NyxBaseBlockSpawner extends IaSBaseBlockMulti {

	@SideOnly(Side.CLIENT)
	protected IIcon icon, top;

	public NyxBaseBlockSpawner(String par1, int subtypes) {
		super(EnumIaSModule.NYX, par1, Material.iron, subtypes);
		setTickRandomly(true);
		setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 4);
		setHardness(Blocks.obsidian.getBlockHardness(null, 0, 16, 0));
		setResistance(Blocks.obsidian.getExplosionResistance(null));
		setBlockBounds(0.15f, 0.0f, 0.15f, 0.75f, 0.85f, 0.75f);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.ANCIENT;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>(0);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 1 || side == 0) ? top : icon;
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 6);
	}

	public abstract Class<? extends EntityLiving> getSpawn(int metadata);

	public int getSpawnCount(int metadata) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	protected boolean hasDifferentTopIcon() {
		return true;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icon = reg.registerIcon(getTextureName());
		if (hasDifferentTopIcon())
			top = reg.registerIcon(getTextureName() + "Top");
		else
			top = icon;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		final int meta = w.getBlockMetadata(x, y, z);
		final Class<? extends EntityLiving> whatToSpawn = getSpawn(meta);
		if (!w.getEntitiesWithinAABB(whatToSpawn,
				AxisAlignedBB.getBoundingBox(x - 8, y - 6, z - 8, x + 9, y + 4, z + 9)).isEmpty())
			return;
		final int end = getSpawnCount(meta);
		for (int c = 0; c < end; ++c) {
			final int xmod = -1 + r.nextInt(3), zmod = -1 + r.nextInt(3);
			for (int i = y + 1; i < 192; ++i)
				if (IaSBlockHelper.isAir(w.getBlock(x + xmod, i, z + zmod))) {
					try {
						final EntityLiving toSpawn = whatToSpawn.getConstructor(World.class).newInstance(w);
						toSpawn.setPosition(x + xmod + 0.5, i, z + zmod + 0.5);
						toSpawn.onSpawnWithEgg(null);
						w.spawnEntityInWorld(toSpawn);
					} catch (final Exception e) {
						e.printStackTrace();
					}
					return;
				}
		}
	}

}
