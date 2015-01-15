package iceandshadow2.nyx.blocks.ropes;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.nyx.NyxBlocks;

public class NyxBlockHookTightrope extends IaSBaseBlockSingle implements
		IIaSTechnicalBlock {

	public NyxBlockHookTightrope(String texName) {
		super(EnumIaSModule.NYX, texName, Material.iron);
		this.setStepSound(soundTypeMetal);
		this.setLightLevel(0.1F);
		this.setResistance(20.0F);
		this.setHardness(10.0F);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x,
			int y, int z, int metadata) {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		final ArrayList<ItemStack> lilili = new ArrayList<ItemStack>();
		lilili.add(new ItemStack(NyxBlocks.hookClimbing));
		return lilili;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world,
			int x, int y, int z) {
		return new ItemStack(NyxBlocks.hookClimbing);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + EnumIaSModule.NYX.prefix
				+ "BlockEchir";
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z,
			ForgeDirection side) {
		return false;
	}
}
