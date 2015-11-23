package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;
import iceandshadow2.util.IaSWorldHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
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
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.4F, 0.9F);
		setLightColor(1.0F, 0.60F, 0.80F);
		setLuminescence(0.2F);
	}

	public void doTPFX(World theWorld, double posX, double posY, double posZ,
			int modX, int modZ) {
		theWorld.playSoundEffect(posX, posY, posZ, "mob.endermen.portal", 1.0F,
				0.8F + theWorld.rand.nextFloat() * 0.1F);
		if (theWorld.isRemote)
			theWorld.spawnParticle("portal", posX,
					posY + theWorld.rand.nextDouble() * 2.0D, posZ,
					theWorld.rand.nextGaussian() * (modX / NyxBlockGatestone.RANGE), 0.0D,
					theWorld.rand.nextGaussian() * (modZ / NyxBlockGatestone.RANGE));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2) {
		if (par2 > 2)
			par2 = 2;
		return par1 == 1 ? this.iconTop[par2] : this.blockIcon;
	}

	@Override
	public int getMobilityFlag() {
		return 0;
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
	public boolean onBlockActivated(World par1World, int x, int y, int z,
			EntityPlayer par5EntityPlayer, int side, float px, float py,
			float pz) {
		if (par1World.getBlockMetadata(x, y, z) > 0) {
			if (par5EntityPlayer.getEquipmentInSlot(0) == null) {
				IaSPlayerHelper
				.messagePlayer(par5EntityPlayer,
						"It's missing something. That center stone looks like bloodstone...");
				return false;
			}
			if (par5EntityPlayer.getEquipmentInSlot(0).getItem() == NyxItems.bloodstone) {
				par1World.setBlockMetadataWithNotify(x, y, z,
						par1World.getBlockMetadata(x, y, z) - 1, 0x2);
				if (!par5EntityPlayer.capabilities.isCreativeMode)
					par5EntityPlayer.getEquipmentInSlot(0).stackSize -= 1;
				if (par1World.getBlockMetadata(x, y, z) == 0) {
					for (int xit = -1; xit <= 1; ++xit) {
						for (int zit = -1; zit <= 1; ++zit) {
							if (par1World.getBlock(x + xit, y - 1, z + zit) == Blocks.obsidian)
								par1World.setBlock(x + xit, y - 1, z + zit,
										NyxBlocks.cryingObsidian, 2, 0x2);
						}
					}
					par1World.spawnEntityInWorld(new EntityLightningBolt(
							par1World, x, y, z));
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void onEntityWalking(World theWorld, int x, int y, int z,
			Entity theEntity) {
		if (theWorld.getBlockMetadata(x, y, z) != 0)
			return;
		if (theEntity.dimension != IaSFlags.dim_nyx_id)
			return;
		if (!(theEntity instanceof EntityMob)) {
			if (theEntity instanceof EntityLivingBase) {
				final EntityLivingBase elb = (EntityLivingBase) theEntity;
				if(!elb.isSprinting())
					return;
				ForgeDirection dir;
				final Vec3 v = elb.getLookVec();
				if (Math.abs(v.xCoord) > Math.abs(v.zCoord)) {
					if (v.xCoord > 0)
						dir = ForgeDirection.EAST;
					else
						dir = ForgeDirection.WEST;
				} else {
					if (v.zCoord > 0)
						dir = ForgeDirection.SOUTH;
					else
						dir = ForgeDirection.NORTH;
				}
				final int posXMod = NyxBlockGatestone.RANGE*dir.offsetX;
				final int posZMod = NyxBlockGatestone.RANGE*dir.offsetZ;
				int posYNew = theWorld.getTopSolidOrLiquidBlock(x
						+ posXMod, z + posZMod) + 1;
				for (int gateY = posYNew; gateY >= 0; --gateY) {
					if (theWorld.getBlock(x + posXMod, gateY, z
							+ posZMod) == this) {
						posYNew = gateY;
						break;
					}
				}
				doTPFX(theWorld, elb.posX, elb.posY, elb.posZ, posXMod, posZMod);
				if (!theWorld.isRemote)
					elb.setPositionAndUpdate(x + 0.5 + posXMod,
							posYNew, x + 0.5 + posZMod);
				elb.attackEntityFrom(IaSDamageSources.dmgGatestone,
						3.0F + 2.0F*IaSWorldHelper.getDifficulty(theWorld));
				doTPFX(theWorld, elb.posX + posXMod, posYNew, elb.posZ + posZMod,
						posXMod, posZMod);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.iconTop = new IIcon[3];
		for (int i = 0; i <= 2; ++i)
			this.iconTop[i] = reg.registerIcon(getTexName() + "Top" + i);
		this.blockIcon = reg.registerIcon(getTexName() + "Side");
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
