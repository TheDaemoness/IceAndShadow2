package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockGatestone extends IaSBaseBlockMulti {

	protected IIcon[] iconTop;

	public NyxBlockGatestone(String par1) {
		super(EnumIaSModule.NYX, par1, Material.rock, (byte) 3);
		this.setBlockUnbreakable();
		this.setResistance(9001.0F);
		this.setLightOpacity(0);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.4F, 0.9F);
		this.setLightColor(1.0F, 0.60F, 0.80F);
		this.setLuminescence(0.2F);
	}

	public void doTPFX(World theWorld, double posX, double posY, double posZ,
			int modX, int modZ) {
		theWorld.playSoundEffect(posX, posY, posZ, "mob.endermen.portal", 1.0F,
				0.8F + theWorld.rand.nextFloat() * 0.1F);
		if (theWorld.isRemote)
			theWorld.spawnParticle("portal", posX,
					posY + theWorld.rand.nextDouble() * 2.0D, posZ,
					theWorld.rand.nextGaussian() * (modX / 128), 0.0D,
					theWorld.rand.nextGaussian() * (modZ / 128));
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
		if (theEntity.dimension == IaSFlags.dim_nyx_id) {
			if (!(theEntity instanceof EntityMob)) {
				if (theEntity instanceof EntityLivingBase) {
					final EntityLivingBase elb = (EntityLivingBase) theEntity;
					if (elb.isSprinting()) {
						final int fac = MathHelper
								.floor_double(elb.rotationYaw * 90.0F + 0.5D) & 3;
						int posXMod = 0;
						int posZMod = 0;
						if (fac == 0)
							posZMod = 128;
						else if (fac == 1)
							posXMod = -128;
						else if (fac == 2)
							posZMod = -128;
						else if (fac == 3)
							posXMod = 128;
						int posYNew = theWorld.getTopSolidOrLiquidBlock(x
								+ posXMod, z + posZMod) + 1;
						for (int gateY = posYNew; gateY >= 0; --gateY) {
							if (theWorld.getBlock(x + posXMod, gateY, z
									+ posZMod) == this) {
								posYNew = gateY;
								break;
							}
						}
						final double ppX = elb.posX, ppY = elb.posY, ppZ = elb.posZ;
						doTPFX(theWorld, ppX, ppY, ppZ, posXMod, posZMod);
						if (!theWorld.isRemote)
							elb.setPositionAndUpdate(elb.posX + posXMod,
									posYNew, elb.posZ + posZMod);
						elb.attackEntityFrom(IaSDamageSources.dmgGatestone,
								5.0F);
						doTPFX(theWorld, ppX + posXMod, posYNew, ppZ + posZMod,
								posXMod, posZMod);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		iconTop = new IIcon[3];
		for (int i = 0; i <= 2; ++i)
			this.iconTop[i] = reg.registerIcon(this.getTexName() + "Top" + i);
		this.blockIcon = reg.registerIcon(this.getTexName() + "Side");
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
