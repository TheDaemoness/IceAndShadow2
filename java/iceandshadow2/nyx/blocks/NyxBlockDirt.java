package iceandshadow2.nyx.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.BonemealEvent;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSBlockThawable;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.NyxChunkManager;

public class NyxBlockDirt extends IaSBaseBlockMulti implements IIaSBlockThawable {
	
	public static float HARDNESS = 0.5F;
	public static float RESISTANCE = 1.0F;

	public NyxBlockDirt(String texName) {
		super(EnumIaSModule.NYX, texName, Material.ground, 2);
		this.setHardness(HARDNESS);
		this.setResistance(RESISTANCE);
		this.setHarvestLevel("spade", 0);
		this.setStepSound(soundTypeGravel);
		this.setTickRandomly(true);
	}
	
	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z,
			ForgeDirection direction, IPlantable plantable) {
		return world.getBlockMetadata(x, y, z) != 1;
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		int meta = w.getBlockMetadata(x,y,z);
		if(meta == 1)
			return;
		if(!(w.getWorldChunkManager() instanceof NyxChunkManager)) {
			w.setBlock(x, y, z, Blocks.dirt, 1, 0x3);
			return;
		}
		Block bl = w.getBlock(x, y+1, z);
		if(bl instanceof IGrowable)
			((IGrowable)bl).func_149853_b(w, r, x, y+1, z);
		else
			MinecraftForge.EVENT_BUS.post(new BonemealEvent(null, w, bl, x,y+1,z));
		boolean foundThermal = false;
		for(int xit = -1; xit<=1; ++xit) {
			for(int yit = -1; yit<=1; ++yit) {
				for(int zit = -1; zit<=1; ++zit) {
					if(w.getBlock(x+xit, y+yit, z+zit).getMaterial() != Material.fire)
						foundThermal = true;
				}
			}
		}
		if(!foundThermal)
			w.setBlockMetadataWithNotify(x, y, z, 1, 0x3);
	}

	@Override
	public Block onThaw(World w, int x, int y, int z) {
		if(w.getBlockMetadata(x, y, z) != 0)
			return this;
		return null;
	}

}
