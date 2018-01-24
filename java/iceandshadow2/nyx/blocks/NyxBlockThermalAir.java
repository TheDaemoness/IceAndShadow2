package iceandshadow2.nyx.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSBlockThawable;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxBlockThermalAir extends IaSBaseBlockTechnical {

	public static boolean canReplace(World w, int x, int y, int z) {
		final Block bl = w.getBlock(x, y, z);
		return (bl.getMaterial() == Material.air || bl.getMaterial() == Material.snow
				|| bl.getMaterial() == Material.craftedSnow || bl.getMaterial() == Material.leaves
				|| bl.getMaterial() == Material.plants);
	}

	public NyxBlockThermalAir(String texName) {
		super(EnumIaSModule.NYX, texName, Material.fire);
		setLightOpacity(0);
		setLightLevel(0.6F);
		setTickRandomly(true);
	}

	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_) {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
			int p_149668_4_) {
		return null;
	}

	@Override
	public String getTextureName() {
		return IceAndShadow2.MODID + ':' + EnumIaSModule.IAS.prefix + "Invisible";
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int x, int y, int z, Entity par5Entity) {
		if (par5Entity instanceof EntityLivingBase) {
			final EntityLivingBase elb = (EntityLivingBase) par5Entity;
			if (elb.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && !elb.isPotionActive(Potion.regeneration))
				elb.addPotionEffect(new PotionEffect(Potion.regeneration.id, 51, 0));
			else if (elb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				elb.attackEntityFrom(DamageSource.inFire, 1);
				elb.setFire(2);
			}
		}
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		if (r.nextInt(32) == 0)
			IaSFxManager.spawnParticle(w, "vanilla_flame", x + r.nextFloat(), y + r.nextFloat(), z + r.nextFloat(), 0.0,
					0.0, 0.0, false, false);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		int highestsource = 0;
		final int lvl = w.getBlockMetadata(x, y, z);
		for (int xit = -1; xit <= 1; ++xit)
			for (int yit = -1; yit <= 1; ++yit)
				for (int zit = -1; zit <= 1; ++zit) {
					final Block bl = w.getBlock(x + xit, y + yit, z + zit);
					if (bl == this)
						highestsource = Math.max(highestsource, w.getBlockMetadata(x + xit, y + yit, z + zit));
					else if (bl instanceof IIaSBlockThawable) {
						final Block nb = ((IIaSBlockThawable) bl).onThaw(w, x + xit, y + yit, z + zit);
						if (nb == null || r.nextInt(4) == 0) {
						} else if (nb == bl)
							w.setBlockMetadataWithNotify(x + xit, y + yit, z + zit, 0, 0x3);
						else
							w.setBlock(x + xit, y + yit, z + zit, nb);
					} else if (lvl > 1 && NyxBlockThermalAir.canReplace(w, x + xit, y + yit, z + zit))
						w.setBlock(x + xit, y + yit, z + zit, this, lvl - 1, 0x2);
				}
		if (highestsource <= lvl)
			for (int xit = -2; xit <= 2; ++xit)
				for (int yit = -2; yit <= 2; ++yit)
					for (int zit = -2; zit <= 2; ++zit)
						if (w.getBlock(x + xit, y + yit, z + zit) == this)
							w.setBlock(x + xit, y + yit, z + zit, Blocks.air, 0, 0x3);
	}
}
