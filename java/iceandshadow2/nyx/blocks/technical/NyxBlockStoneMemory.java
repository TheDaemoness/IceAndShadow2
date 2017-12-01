package iceandshadow2.nyx.blocks.technical;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSBlockThawable;
import iceandshadow2.ias.blocks.IaSBaseBlockTechnical;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSBlockHelper;

public class NyxBlockStoneMemory extends IaSBaseBlockTechnical {

	public NyxBlockStoneMemory(String texName) {
		super(EnumIaSModule.NYX, texName, Material.fire);
		setLightOpacity(0);
		setTickRandomly(true);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return new ArrayList<ItemStack>(0);
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		this.updateTick(w, x, y, z, w.rand);
		super.onBlockAdded(w, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z,
			Block what) {
		this.updateTick(w, x, y, z, w.rand);
		super.onNeighborBlockChange(w, x, y, z, what);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+EnumIaSModule.IAS.prefix+"Invisible";
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_,
			int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canCollideCheck(int meta, boolean p_149678_2_) {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(w.getBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) == NyxBlocks.stone)
				w.setBlock(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, NyxBlocks.stoneGrowing);
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_,
			int p_149655_3_, int p_149655_4_) {
		return false;
	}
}
