package iceandshadow2.nyx.blocks;

import java.util.ArrayList;
import java.util.Random;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockThermalCore extends IaSBaseBlockMulti {
	public NyxBlockThermalCore(String id) {
		super(EnumIaSModule.NYX, id, Material.fire, 2);
		this.setLuminescence(0.5F);
		this.setLightOpacity(0);
		this.setHardness(0.5F);
		this.setLightColor(1.0F, 0.5F, 0.0F);
		this.setResistance(NyxBlockStone.RESISTANCE);
		this.setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.6F, 0.8F);
		this.setTickRandomly(true);
	}
	
	

	@Override
	public boolean onBlockActivated(World w, int x,
			int y, int z, EntityPlayer ep,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		w.setBlockToAir(x, y, z);
		IaSPlayerHelper.giveItem(ep, new ItemStack(NyxItems.alabaster,1,1));
		return true;
	}



	@Override
	public void onBlockDestroyedByExplosion(World w, int x, int y, int z,
			Explosion e) {
		w.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World w, int x, int y, int z, int meta) {
		if (meta == 0) {
			w.setBlock(x, y, z, this, 1, 0x2);
			IaSFxManager.spawnParticle(w, "shadowSmokeLarge",
					x+0.5, y+0.5, z+0.5, false, true);
			updateTick(w, x, y, z, w.rand);
		}
	}
	
	@Override
	public void updateTick(World w, int x, int y,
			int z, Random r) {
		if(w.getBlockMetadata(x, y, z) != 1)
			return;
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

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		return new ArrayList<ItemStack>();
	}

}
