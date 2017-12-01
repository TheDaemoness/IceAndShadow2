package iceandshadow2.nyx.blocks.technical;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSPlayerHelper;

public abstract class NyxBlockRope extends IaSBaseBlockTechnical {

	public NyxBlockRope(String texName) {
		super(EnumIaSModule.NYX, texName, Material.cloth);
		setStepSound(Block.soundTypeCloth);
		setBlockUnbreakable();
		setLightLevel(0.1F);
		setLightOpacity(0);
		setResistance(9001F);
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		return false;
	}

	@Override
	public void dropBlockAsItemWithChance(World w, int x, int y, int z, int m, float q, int f) {
		return;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(NyxItems.rope);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getIaSModule().prefix + "Rope";
	}

	@Override
	public boolean isBlockNormalCube() {
		return false;
	}

	@Override
	public boolean isBlockSolid(IBlockAccess w, int x, int y, int z, int meta) {
		return false;
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		return false;
	}

	@Override
	public void onBlockClicked(World w, int x, int y, int z, EntityPlayer pl) {
		if (!pl.isSneaking())
			return;
		if (!w.isRemote) {
			IaSPlayerHelper.giveItem(pl, new ItemStack(NyxItems.rope));
			w.func_147480_a(x, y, z, false);
		}
	}

	@Override
	public abstract void onNeighborBlockChange(World w, int x, int y, int z, Block bl);

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 0;
	}

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 0;
	}

	@Override
	public int quantityDroppedWithBonus(int p_149679_1_, Random p_149679_2_) {
		return 0;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(getTexName());
	}
}
