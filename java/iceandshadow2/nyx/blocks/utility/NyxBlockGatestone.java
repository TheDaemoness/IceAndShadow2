package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.styx.Styx;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockGatestone extends IaSBaseBlockMulti {

	public static final int RANGE = 384;

	protected IIcon[] iconTop;

	public NyxBlockGatestone(String par1) {
		super(EnumIaSModule.NYX, par1, Material.rock, (byte) 3);
		setBlockUnbreakable();
		setResistance(9001.0F);
		setLightOpacity(0);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.35F, 0.9F);
		setLightColor(1.0F, 0.60F, 0.80F);
		setLuminescence(0.2F);
	}

	public void doTPFX(World theWorld, double posX, double posY, double posZ, int modX, int modZ) {
		theWorld.playSoundEffect(posX, posY, posZ, "mob.endermen.portal", 1.0F,
				0.8F + theWorld.rand.nextFloat() * 0.1F);
		if (theWorld.isRemote) {
			for(int i = 0; i < 8; ++i) {
				IaSFxManager.spawnParticle(theWorld, "vanilla_portal", posX, posY + theWorld.rand.nextDouble() * 2.0D, posZ,
					theWorld.rand.nextGaussian() * (modX / NyxBlockGatestone.RANGE), 0.0D,
					theWorld.rand.nextGaussian() * (modZ / NyxBlockGatestone.RANGE),
					false, true);
			}
		}
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.ANCIENT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par2 > 2) {
			par2 = 2;
		}
		return par1 == 1 ? iconTop[par2] : blockIcon;
	}

	@Override
	public int getMobilityFlag() {
		return 2;
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

	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side,
			float px, float py, float pz) {
		if (par1World.getBlockMetadata(x, y, z) > 0) {
			if (par5EntityPlayer.getEquipmentInSlot(0) == null) {
				IaSPlayerHelper.messagePlayer(par5EntityPlayer,
						"It's missing something. That center stone looks like bloodstone...");
				return false;
			}
			if (par5EntityPlayer.getEquipmentInSlot(0).getItem() == NyxItems.bloodstone) {
				par1World.setBlockMetadataWithNotify(x, y, z, par1World.getBlockMetadata(x, y, z) - 1, 0x2);
				if (!par5EntityPlayer.capabilities.isCreativeMode) {
					par5EntityPlayer.getEquipmentInSlot(0).stackSize -= 1;
				}
				if (par1World.getBlockMetadata(x, y, z) == 0) {
					par1World.setBlock(x, y - 1, z, NyxBlocks.sanguineObsidian, 1, 0x2);
					par1World.spawnEntityInWorld(new EntityLightningBolt(par1World, x, y, z));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World theWorld, int x, int y, int z, Entity theEntity) {
		if (theWorld.getBlockMetadata(x, y, z) != 0)
			return;
		if (theEntity.dimension != IaSFlags.dim_nyx_id)
			return;
		if (!theEntity.isSneaking())
			return;
		if (!(theEntity instanceof EntityMob))
			if (theEntity instanceof EntityLivingBase) {
				final EntityLivingBase elb = (EntityLivingBase) theEntity;
				ForgeDirection dir;
				final Vec3 v = elb.getLookVec();
				if (Math.abs(v.xCoord) > Math.abs(v.zCoord)) {
					if (v.xCoord > 0) {
						dir = ForgeDirection.EAST;
					} else {
						dir = ForgeDirection.WEST;
					}
				} else if (v.zCoord > 0) {
					dir = ForgeDirection.SOUTH;
				} else {
					dir = ForgeDirection.NORTH;
				}
				final int posXMod = NyxBlockGatestone.RANGE * dir.offsetX;
				final int posZMod = NyxBlockGatestone.RANGE * dir.offsetZ;
				final int newX = x + posXMod;
				final int newZ = z + posZMod;
				final Chunk ck = IaSWorldHelper.loadChunk(theWorld, newX, newZ);
				if(ck == null || !ck.isChunkLoaded || ck.isEmpty())
					return;
				int posYNew = 255;
				for (int gateY = posYNew; gateY >= 4; --gateY)
					if (ck.getBlock(newX&15, gateY, newZ&15) == Styx.gatestone) {
						posYNew = gateY;
						break;
					}
				doTPFX(theWorld, elb.posX, elb.posY, elb.posZ, posXMod, posZMod);
				elb.setSneaking(false);
				elb.setPositionAndUpdate(newX + 0.5 , posYNew+1, newZ + 0.5);
				elb.attackEntityFrom(IaSDamageSources.dmgGatestone,
						3.0F + 2.0F * IaSWorldHelper.getDifficulty(theWorld));
				doTPFX(theWorld, elb.posX + posXMod, posYNew, elb.posZ + posZMod, posXMod, posZMod);
			}
	}

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (par1World.getBlockMetadata(par2, par3, par4) != 0)
			return;

		final double var9 = par3 + par5Random.nextFloat();
		double var13 = 0.0D;
		double var15 = 0.0D;
		double var17 = 0.0D;
		final int var19 = par5Random.nextInt(2) * 2 - 1;
		final int var20 = par5Random.nextInt(2) * 2 - 1;
		var13 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		var15 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		var17 = (par5Random.nextFloat() - 0.5D) * 0.125D;
		final double var11 = par4 + 0.5D + 0.25D * var20;
		var17 = par5Random.nextFloat() * 1.0F * var20;
		final double var7 = par2 + 0.5D + 0.25D * var19;
		var13 = par5Random.nextFloat() * 1.0F * var19;
		IaSFxManager.spawnParticle(par1World, "vanilla_portal", var7, var9, var11, var13, var15, var17, false, true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		iconTop = new IIcon[3];
		for (int i = 0; i <= 2; ++i) {
			iconTop[i] = reg.registerIcon(getTextureName() + "Top" + i);
		}
		blockIcon = reg.registerIcon(getTextureName() + "Side");
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False
	 * (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
