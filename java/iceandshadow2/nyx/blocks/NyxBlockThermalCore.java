package iceandshadow2.nyx.blocks;

import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockThermalCore extends IaSBaseBlockSingle {
	public NyxBlockThermalCore(String id) {
		super(EnumIaSModule.NYX, id, Material.clay);
		this.setLuminescence(0.5F);
		this.setLightOpacity(0);
		this.setLightColor(1.0F, 0.5F, 0.0F);
		this.setResistance(NyxBlockStone.RESISTANCE);
		this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
		this.setTickRandomly(false);
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		return canBlockStay(w,x,y,z);
	}

	@Override
	public boolean canBlockStay(World w, int x, int y, int z) {
		return w.isSideSolid(x, y-1, z, ForgeDirection.UP);
	}

	@Override
	public void onBlockAdded(World w, int x, int y, int z) {
		updateTick(w,x,y,z,w.rand);
	}

	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		for(int xit = -1; xit<=1; ++xit) {
			for(int zit = -1; zit<=1; ++zit) {
				if(NyxBlockThermalAir.canReplace(w, x+xit, y+1, z+zit))
					w.setBlock(x+xit, y+1, z+zit, NyxBlocks.thermalAir, 7, 0x3);
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getMobilityFlag() {
		return 0;
	}

}
