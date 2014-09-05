package iceandshadow2.nyx.blocks;

import iceandshadow2.IaSFlags;
import iceandshadow2.ias.blocks.IaSBaseBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxItems;
//import iceandshadow2.renderer.fx.IaSFxManager;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSPlayerHelper;
import iceandshadow2.nyx.world.NyxTeleporter;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class NyxBlockPortal extends IaSBaseBlockSingle {

	IIcon closed;

	public NyxBlockPortal(String id) {
		super(EnumIaSModule.NYX, id, Material.portal);
		this.setLuminescence(0.3F);
		this.setLightColor(0.5F, 0.0F, 1.0F);
		this.setBlockUnbreakable();
		this.setResistance(666.0F);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

	@Override
	public int getRenderBlockPass() {
		return 0;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 0)
			return closed;
		else
			return this.blockIcon;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		closed = reg.registerIcon("IceAndShadow:nyxInvisible");
		this.blockIcon = reg.registerIcon("IceAndShadow:nyxPortal");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int x, int y, int z) {
		super.onBlockAdded(par1World, x, y, z);
		if (IaSFlags.force_portals_open)
			par1World.setBlockMetadataWithNotify(x, y, z, 1, 0x2);
		else
			updateTick(par1World, x, y, z, par1World.rand);
	}

	@Override
	public void updateTick(World par1World, int x, int y, int z,
			Random par5Random) {
		if (par1World.isRemote || IaSFlags.force_portals_open)
			return;

		int yit;
		for (yit = y; yit < 256; ++yit) {
			Block id = par1World.getBlock(x, yit, z);
			if (id == Blocks.ice)
				break;
		}
		if (par1World.getBlockMetadata(x, y, z) == 0
				&& par1World.getBlockLightValue(x, yit + 1, z) < 7)
			par1World.setBlockMetadataWithNotify(x, y, z, 1, 0x2);

		if (!par1World.provider.hasNoSky) {
			if (!par1World.canBlockSeeTheSky(x, yit + 1, z))
				par1World.setBlockMetadataWithNotify(x, y, z, 0, 0x2);
		}
		if (par1World.getBlockLightValue(x, yit + 1, z) > 7)
			par1World.setBlockMetadataWithNotify(x, y, z, 0, 0x2);

		for (int xit = -1; xit <= 1; xit += 2) {
			for (int zit = -1; zit <= 1; zit += 2) {
				if (par1World.getBlock(x + xit, y, z + zit) != Blocks.ice) {
					if (par1World.getBlockMetadata(x, y, z) != 0)
						par1World.createExplosion(null, 0.5 + x, 0.5 + y,
								0.5 + z, 7.0F, true);
					par1World.setBlockToAir(x, y, z);
					return;
				}
			}
		}
		par1World.scheduleBlockUpdate(x, y, z, this, 100);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
			int par4, Entity par5Entity) {

		if (par5Entity instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer) par5Entity;
			if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
				if (!pl.isPotionActive(Potion.confusion))
					IaSPlayerHelper
							.messagePlayer(pl,
									"The portal doesn't seem open. Maybe it needs to be darker.");
				pl.addPotionEffect(new PotionEffect(Potion.confusion.id, 4, 0));
				return;
			}
			while (true) {
				if (pl.isSprinting())
					break;
				if (pl.dimension == IaSFlags.dim_nyx_id)
					break;

				if (!pl.isPotionActive(Potion.blindness)) {
					IaSPlayerHelper
							.messagePlayer(
									pl,
									"The portal pulls on you harder the faster you move. Maybe sprinting will get you through it?");
				}
				pl.addPotionEffect(new PotionEffect(Potion.blindness.id, 20, 0));
				return;
			}
		}

		if (par5Entity.ridingEntity == null
				&& par5Entity.riddenByEntity == null) {
			if (par5Entity instanceof EntityPlayerMP) {
				EntityPlayerMP thePlayer = (EntityPlayerMP) par5Entity;

				if (!thePlayer.isEntityAlive())
					return;

				if (par5Entity.dimension != IaSFlags.dim_nyx_id) {
					thePlayer.mcServer
							.getConfigurationManager()
							.transferPlayerToDimension(
									thePlayer,
									IaSFlags.dim_nyx_id,
									new NyxTeleporter(
											thePlayer.mcServer
													.worldServerForDimension(IaSFlags.dim_nyx_id)));
				} else {
					thePlayer.mcServer.getConfigurationManager()
							.transferPlayerToDimension(
									thePlayer,
									0,
									new NyxTeleporter(thePlayer.mcServer
											.worldServerForDimension(0)));
				}
			} else if (par5Entity instanceof EntityLiving)
				par5Entity.attackEntityFrom(DamageSource.magic, 6.0F);
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int x, int y, int z,
			Block par5) {
		if ((par1World.getBlock(x, y + 1, z) != Blocks.ice && par1World
				.getBlock(x, y + 1, z) != this)
				|| (par1World.getBlock(x, y - 1, z) != Blocks.ice && par1World
						.getBlock(x, y - 1, z) != this)) {
			if (par1World.getBlockMetadata(x, y, z) != 0)
				par1World.createExplosion(null, 0.5 + x, 0.5 + y, 0.5 + z,
						4.0F, true);
			par1World.setBlockToAir(x, y, z);

		}
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/*
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		double var12 = 0.00D;
		double var14 = 0.00D;
		double var16 = 0.00D;

		if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
			int e = 3 + par5Random.nextInt(2);
			for (int i = 0; i < e; ++i) {
				var12 = par5Random.nextFloat() * 0.5 - 0.25;
				var16 = par5Random.nextFloat() * 0.5 - 0.25;
				double var6 = (double) ((float) par2 + 0.49F + par5Random
						.nextFloat() * 0.02);
				double var8 = (double) ((float) par3 + par5Random.nextFloat());
				double var10 = (double) ((float) par4 + 0.49F + par5Random
						.nextFloat() * 0.02);
				IaSFxManager.spawnParticle(par1World, "vanilla_portal", var6,
						var8, var10, var12, var14, var16, true, false);
			}
			return;
		}

		if (par5Random.nextInt(150) == 0)
			par1World.playSound((double) par2 + 0.5D, (double) par3 + 0.5D,
					(double) par4 + 0.5D, "iceandshadow:portal_ambient", 0.4F,
					par5Random.nextFloat() * 0.1F + 0.9F, false);

		int e = 8 + par5Random.nextInt(8);
		for (int i = 0; i < e; ++i) {
			double var6 = (double) ((float) par2 + par5Random.nextFloat());
			double var8 = (double) ((float) par3 + par5Random.nextFloat() - 0.5F);
			double var10 = (double) ((float) par4 + par5Random.nextFloat());
			IaSFxManager.spawnParticle(par1World, "vanilla_portal", var6, var8,
					var10, var12, var14, var16, false, true);
		}
	}
	*/
}
