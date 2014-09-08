package iceandshadow2.nyx.world;

import iceandshadow2.nyx.NyxBlocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class NyxTeleporter extends Teleporter {
	private final WorldServer field_85192_a;
	private final Random random;

	public NyxTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		this.field_85192_a = par1WorldServer;
		this.random = new Random(par1WorldServer.getSeed());
	}

	public void placeInPortal(Entity par1Entity, double par2, double par4,
			double par6, float par8) {

		int xcoord = MathHelper.floor_double(par1Entity.posX);
		int ycoord = MathHelper.floor_double(par1Entity.posY
				+ MathHelper.sqrt_double(MathHelper
						.sqrt_double(256.0 - par1Entity.posY)
						* (par1Entity.posY))) - 1;
		int zcoord = MathHelper.floor_double(par1Entity.posZ);

		if(this.field_85192_a.provider.dimensionId == 0)
			this.placeInOverworld(par1Entity, xcoord, ycoord, zcoord);
		else if (!placeOnExistingPlatform(par1Entity, xcoord, ycoord, zcoord)) {
			placeInOverworld(par1Entity, xcoord, ycoord, zcoord); //Replace later.
			par1Entity.setLocationAndAngles((double) xcoord, (double) ycoord,
					(double) zcoord, par1Entity.rotationYaw, 0.0F);
		}
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}

	private void placeInOverworld(Entity par1Entity, int x, int y, int z) {
		int ycoord = -10;
		for(int yit = 0; y-yit > 0 || y+yit <= 255; ++yit) {
			ycoord = 0;
			if(y-yit > 0) {
				if(!field_85192_a.isAirBlock(x, y-yit, z))
					ycoord = y-yit;
			}
			if(ycoord == 0 && y+yit <= 255) {
				if(!field_85192_a.isAirBlock(x, y+yit, z))
					ycoord = y+yit;
			}
			if(ycoord != 0) {
				if(field_85192_a.isAirBlock(x, ycoord+1, z) && field_85192_a.isAirBlock(x, ycoord+2, z))
					break;
			}
		}
		if(field_85192_a.getBlock(x, ycoord, z) == Blocks.water)
			field_85192_a.setBlock(x, ycoord, z, Blocks.ice);
		else if(field_85192_a.getBlock(x, ycoord, z) == Blocks.lava)
			field_85192_a.setBlock(x, ycoord, z, Blocks.cobblestone);
		else if(field_85192_a.getBlock(x, ycoord, z) == Blocks.cactus)
			field_85192_a.setBlock(x, ycoord, z, Blocks.sandstone);
		else if(field_85192_a.getBlock(x, ycoord, z) == Blocks.fire)
			field_85192_a.setBlock(x, ycoord, z, Blocks.air);
		
		par1Entity.setLocationAndAngles(((double)x)+0.5, (double)ycoord+1.0,
				((double)z)+0.5, this.field_85192_a.rand.nextFloat()*360.0F, 0.0F);
	}

	public boolean placeOnExistingPlatform(Entity par1Entity, int x, int y,
			int z) {
		for (int xi = 0; xi < 32; ++xi) {
			for (int yi = 0; yi < 32; ++yi) {
				for (int zi = 0; zi < 32; ++zi) {
					for (byte flip = 0; flip < 8; ++flip) {
						int xfactor = (flip | 0x1) > 0 ? -1 : 1;
						int yfactor = (flip | 0x2) > 0 ? -1 : 1;
						int zfactor = (flip | 0x4) > 0 ? -1 : 1;

						int ycalc = (y + yi * yfactor);
						ycalc = ycalc > 255 ? 255 : ycalc;
						ycalc = ycalc < 1 ? 1 : ycalc;

						int xvalue = x + xi * xfactor;
						int yvalue = ycalc;
						int zvalue = z + zi * zfactor;
						Block bid = this.field_85192_a.getBlock(xvalue, yvalue,
								zvalue);
						int bmet = this.field_85192_a.getBlockMetadata(xvalue,
								yvalue, zvalue);

							if (bid == NyxBlocks.cryingObsidian && bmet == 1) {
								bid = this.field_85192_a.getBlock(xvalue,
										yvalue + 1, zvalue);
								Block bid2 = this.field_85192_a.getBlock(xvalue,
										yvalue + 2, zvalue);
								if (bid == Blocks.air && bid2 == Blocks.air) {
									par1Entity.setLocationAndAngles(
											(double) xvalue,
											(double) (yvalue + 1),
											(double) zvalue,
											par1Entity.rotationYaw, 0.0F);
									return true;
								}
							}
						}
					}
				}
			}
		return false;
	}
}
