package iceandshadow2.nyx.blocks;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockFalling;
import iceandshadow2.ias.util.IaSBlockHelper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockGravel extends IaSBaseBlockFalling {
	public NyxBlockGravel(String par1) {
		super(EnumIaSModule.NYX, par1, Material.sand);
		setStepSound(Block.soundTypeGravel);
		setHardness(0.5F);
		setResistance(1.0F);
		setTickRandomly(true);
		this.setHarvestLevel("shovel", 1);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.LAND;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return Math.max(super.getLightValue(world, x, y, z),
				IaSBlockHelper.isAdjacent(world, x, y, z, NyxBlocks.stone) ? 2 : 0);
	}

	@Override
	public int getMobilityFlag() {
		return 0;
	}

	public boolean isGenMineableReplaceable(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		if (!IaSBlockHelper.isAdjacent(w, x, y, z, NyxBlocks.stone)) {
			w.scheduleBlockUpdate(x, y, z, this, tickRate(w));
		} else for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			if (w.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == NyxBlocks.stone) {
				w.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, NyxBlocks.stoneGrowing);
			}
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par5EntityPlayer.getCurrentEquippedItem() == null
				&& par5EntityPlayer.worldObj.difficultySetting.getDifficultyId() > 0) {
			par5EntityPlayer.attackEntityFrom(IaSDamageSources.dmgStone,
					par5EntityPlayer.worldObj.difficultySetting.getDifficultyId());
		}
	}

	@Override
	public void onEntityWalking(World theWorld, int x, int y, int z, Entity theEntity) {
		NyxBlockStone.doDamage(theWorld, x, y, z, theEntity, theEntity.worldObj.difficultySetting.getDifficultyId());
	}

	@Override
	public void onFallenUpon(World woild, int x, int y, int z, Entity theEntity, float height) {
		super.onFallenUpon(woild, x, y, z, theEntity, height);

		final int dmg = 1 + theEntity.worldObj.difficultySetting.getDifficultyId();
		NyxBlockStone.doDamage(woild, x, y, z, theEntity, dmg);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block b) {
		if (!IaSBlockHelper.isAdjacent(w, x, y, z, NyxBlocks.stone)) {
			w.scheduleBlockUpdate(x, y, z, this, tickRate(w));
		}

	}

	@Override
	public void updateTick(World par1World, int x, int y, int z, Random par5Random) {
		if (IaSBlockHelper.isAdjacent(par1World, x, y, z, NyxBlocks.stone)) {
			if (!par1World.isRemote) {
				par1World.setBlock(x, y, z, NyxBlocks.stone);
			}
		} else {
			super.updateTick(par1World, x, y, z, par5Random);
		}
	}
}
