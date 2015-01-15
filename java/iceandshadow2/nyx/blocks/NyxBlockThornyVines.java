package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSRegistration;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxBlockThornyVines extends BlockVine implements IIaSModName {

	public NyxBlockThornyVines(String texName) {
		this.setBlockName("nyx" + texName);
		this.setBlockTextureName(this.getTexName());
		this.setLightLevel(0.1F);
		this.setLightOpacity(0);
		this.setStepSound(soundTypeGrass);
	}

	/**
	 * Checks to see if its valid to put this block at the specified
	 * coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return par1World.isSideSolid(par2 - 1, par3, par4, ForgeDirection.EAST)
				|| par1World.isSideSolid(par2 + 1, par3, par4,
						ForgeDirection.WEST, false)
				|| par1World.isSideSolid(par2, par3, par4 - 1,
						ForgeDirection.SOUTH, false)
				|| par1World.isSideSolid(par2, par3, par4 + 1,
						ForgeDirection.NORTH, false);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2,
			int par3, int par4) {
		return 0xFFFFFF;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return EnumIaSModule.NYX;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public void onEntityCollidedWithBlock(World theWorld, int x, int y, int z,
			Entity theEntity) {

		if (theEntity instanceof EntityLivingBase && !theEntity.onGround) {
			boolean movflag = theEntity.posY != theEntity.prevPosY;
			movflag |= theEntity.posX != theEntity.prevPosX;
			movflag |= theEntity.posZ != theEntity.prevPosZ;
			if (theEntity instanceof EntityMob || !movflag)
				return;
			if (theWorld.difficultySetting == EnumDifficulty.HARD)
				theEntity.attackEntityFrom(IaSDamageSources.dmgVines, 2);
			else
				theEntity.attackEntityFrom(IaSDamageSources.dmgVines, 1);
		}
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world,
			int x, int y, int z, int fortune) {
		final ArrayList<ItemStack> li = new ArrayList<ItemStack>();
		li.add(new ItemStack(NyxItems.vineBundle, 1));
		return li;
	}

	public Block register() {
		IaSRegistration.register(this);
		return this;
	}
}
