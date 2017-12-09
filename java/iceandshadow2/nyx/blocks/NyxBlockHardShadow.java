package iceandshadow2.nyx.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.items.NyxItemExousium;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxBlockHardShadow extends IaSBaseBlockSingle {

	@SideOnly(Side.CLIENT)
	protected IIcon openIcon;

	public NyxBlockHardShadow(String texName) {
		super(EnumIaSModule.NYX, texName, Material.portal);
		setBlockUnbreakable();
		slipperiness = 0.99F;
		setTickRandomly(true);
	}

	@Override
	public boolean addHitEffects(World w, MovingObjectPosition t, EffectRenderer effectRenderer) {
		IaSFxManager.spawnParticle(w, "shadowSmokeSmall", t.blockX + w.rand.nextDouble(),
				t.blockY + w.rand.nextDouble(), t.blockZ + w.rand.nextDouble(), 0.0, 0.0, 0.0, false, true);
		return true;
	}

	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_) {
		return meta == 0;
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
		final float var5 = 0.0125F;
		if (w.getBlockMetadata(x, y, z) != 0)
			return null;
		return AxisAlignedBB.getBoundingBox(x + var5, y + var5, z + var5, x + 1 - var5, y + 1 - var5, z + 1 - var5);
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX,
			double explosionY, double explosionZ) {
		if (world.getBlockMetadata(x, y, z) == 0)
			return 9000;
		return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return meta > 0 ? openIcon : blockIcon;
	}

	@Override
	public int getLightOpacity(IBlockAccess w, int x, int y, int z) {
		return w.getBlockMetadata(x, y, z) > 0 ? 7 : 15;
	}

	@Override
	public int getMobilityFlag() {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return world.getBlockMetadata(x, y, z) == 0;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ep, int side, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		if (ep.getCurrentEquippedItem() != null)
			if (ep.getCurrentEquippedItem().getItem() instanceof NyxItemExousium) {
				w.playSound(x + 0.5, y + 0.5, z + 0.5, "random.fizz", 0.2F, 0.95F + w.rand.nextFloat() / 10.0F, true);
				w.setBlockToAir(x, y, z);
				return true;
			}
		return false;
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		onNeighborBlockChange(w, x, y, z, null);
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e) {
		final boolean iselb = e instanceof EntityLivingBase;
		if (w.getBlockMetadata(x, y, z) != 0) {
			if (iselb)
				((EntityLivingBase) e).addPotionEffect(new PotionEffect(Potion.blindness.id, 22, 0));
			e.motionX *= 0.1;
			e.motionY *= 0.3;
			e.motionZ *= 0.1;
		}
		if (iselb && w.rand.nextBoolean())
			IaSFxManager.spawnParticle(w, "shadowSmokeSmall", x + w.rand.nextDouble(), y + w.rand.nextDouble(),
					z + w.rand.nextDouble(), 0.0, 0.0, 0.0, false, true);
	}

	@Override
	public void onFallenUpon(World w, int x, int y, int z, Entity e, float vel) {
		if (w.isRemote)
			if (e instanceof EntityLivingBase)
				for (int i = 0; i < 2 + vel * 4; ++i)
					IaSFxManager.spawnParticle(w, "shadowSmokeSmall", x + w.rand.nextDouble(),
							y + 1 - w.rand.nextFloat() * 1.5, z + w.rand.nextDouble(), 0.0, 0.0, 0.0, false, true);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b) {
		int power = w.getBlockPowerInput(x, y, z);
		for (int xit = -1; xit <= 1; ++xit)
			for (int zit = -1; zit <= 1; ++zit)
				for (int yit = -1; yit <= 1; ++yit) {
					if (((xit == 0 ? 1 : 0) + (yit == 0 ? 1 : 0) + (zit == 0 ? 1 : 0)) != 2)
						continue;
					final Block bl = w.getBlock(x + xit, y + yit, z + zit);
					if (bl instanceof NyxBlockHardShadow)
						power = Math.max(power, Math.max(0, w.getBlockMetadata(x + xit, y + yit, z + zit) - 1));
					if (w.isBlockIndirectlyGettingPowered(x + xit, y + yit, z + zit))
						power = Math.max(power, w.getBlockPowerInput(x + xit, y + yit, z + zit));
					power = Math.max(power, bl.isProvidingStrongPower(w, x + xit, y + yit, z + zit,
							w.getBlockMetadata(x + xit, y + yit, z + zit)));
					power = Math.max(power, bl.isProvidingWeakPower(w, x + xit, y + yit, z + zit,
							w.getBlockMetadata(x + xit, y + yit, z + zit)));
				}
		final int oldmeta = w.getBlockMetadata(x, y, z);
		if (power != oldmeta)
			w.setBlockMetadataWithNotify(x, y, z, power, 0x3);
	}

	@Override
	public void randomDisplayTick(World w, int x, int y, int z, Random r) {
		int chance = 8;
		if (w.getBlockMetadata(x, y, z) != 0)
			chance = 2;
		if (r.nextInt(chance) == 0)
			IaSFxManager.spawnParticle(w, "shadowSmokeSmall", x + r.nextFloat(), y + r.nextFloat(), z + r.nextFloat(),
					0.0, 0.0, 0.0, false, w.rand.nextBoolean());
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		openIcon = reg.registerIcon(IceAndShadow2.MODID + ':' + EnumIaSModule.IAS.prefix + "Invisible");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int s) {
		if (w.getBlock(x, y, z) == this && w.getBlockMetadata(x, y, z) == 0)
			return false;
		return super.shouldSideBeRendered(w, x, y, z, s);
	}

}
