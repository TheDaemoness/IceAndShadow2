package iceandshadow2.nyx.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IaSRegistration;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;

public class NyxBlockHookClimbing extends BlockFence {

	private final String texName;

	public NyxBlockHookClimbing(String texName) {
		super(IceAndShadow2.MODID + ':' + EnumIaSModule.NYX.prefix + "BlockEchir", Material.iron);
		setBlockName(EnumIaSModule.NYX.prefix + texName);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(IaSCreativeTabs.tools);
		setLightLevel(0.1F);
		setResistance(20.0F);
		setHardness(10.0F);
		this.setHarvestLevel("pickaxe", 0);
		this.texName = texName;
	}

	@Override
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z, int side) {
		return side != 1;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		final ArrayList<ItemStack> is = super.getDrops(world, x, y, z, metadata, fortune);
		if (world.getBlock(x, y - 1, z) == NyxBlocks.ropeY) {
			is.add(new ItemStack(NyxItems.rope));
		}
		return is;
	}

	@Override
	public String getItemIconName() {
		return IceAndShadow2.MODID + ':' + EnumIaSModule.NYX.prefix + texName;
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer pl, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		if (w.getBlock(x, y - 1, z) == NyxBlocks.ropeY)
			if (pl.getEquipmentInSlot(0) != null)
				if (pl.getEquipmentInSlot(0).getItem() != NyxItems.rope) {
					w.func_147480_a(x, y - 1, z, false);
					IaSPlayerHelper.giveItem(pl, new ItemStack(NyxItems.rope));
				}
		return super.onBlockActivated(w, x, y, z, pl, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block cock) {
		if (canConnectFenceTo(w, x + 1, y, z))
			return;
		if (canConnectFenceTo(w, x - 1, y, z))
			return;
		if (canConnectFenceTo(w, x, y, z + 1))
			return;
		if (canConnectFenceTo(w, x, y, z - 1))
			return;
		if (w.isSideSolid(x, y + 1, z, ForgeDirection.DOWN))
			return;
		w.func_147480_a(x, y, z, true);
	}

	public final Block register() {
		IaSRegistration.register(this);
		return this;
	}

}
