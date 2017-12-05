package iceandshadow2.nyx.world;

import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsCentral;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class NyxTeleporter extends Teleporter {
	private final WorldServer world;
	private final Random random;

	public NyxTeleporter(WorldServer par1WorldServer) {
		super(par1WorldServer);
		this.world = par1WorldServer;
		this.random = new Random(par1WorldServer.getSeed());
	}

	private void placeInNyx(Entity par1Entity, int x, int z) {
		for (int i = 0; i <= 3; ++i)
			this.world.getChunkProvider().loadChunk(-(i >> 1), -(i & 1));
		final int y = GenRuinsCentral.getGenHeight(this.world, 0, 0) + 3;
		par1Entity.setLocationAndAngles(0.5, y + 1, 0.5, this.world.rand.nextFloat() * 360.0F, 0.0F);
	}

	private void placeInOverworld(Entity par1Entity, int x, int z) {
		int y = 5;
		for (; y + 2 <= 255; ++y) {
			if (!this.world.isAirBlock(x, y, z))
				continue;
			if (!this.world.isAirBlock(x, y + 1, z))
				continue;
			if (!this.world.isAirBlock(x, y + 2, z))
				continue;
			break;
		}
		if (y == 253)
			y = 255;
		if (this.world.getBlock(x, y - 1, z) == Blocks.water)
			this.world.setBlock(x, y - 1, z, Blocks.ice);
		else if (this.world.getBlock(x, y - 1, z) == Blocks.lava)
			this.world.setBlock(x, y - 1, z, Blocks.cobblestone);
		else if (this.world.getBlock(x, y - 1, z) == Blocks.cactus)
			this.world.setBlock(x, y - 1, z, Blocks.sandstone);
		else if (this.world.getBlock(x, y - 1, z) == Blocks.fire)
			this.world.setBlock(x, y - 1, z, Blocks.air);
		par1Entity.setLocationAndAngles(x + 0.5, y + 1.0, z + 0.5, this.world.rand.nextFloat() * 360.0F, 0.0F);
	}

	@Override
	public void placeInPortal(Entity par1Entity, double x, double y, double z, float par8) {

		if (this.world.provider.dimensionId == 0)
			placeInOverworld(par1Entity, (int) x, (int) z);
		else if (!placeOnExistingPlatform(par1Entity, (int) x, (int) y, (int) z)) {
			if (!placeOnExistingPlatform(par1Entity, 0, this.world.getPrecipitationHeight((int) x, (int) z), 0))
				placeInNyx(par1Entity, (int) x, (int) z);
		}
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}

	public boolean placeOnExistingPlatform(Entity par1Entity, int x, int y, int z) {
		for (int xi = 0; xi < 32; ++xi) {
			for (int yi = 0; yi < 32; ++yi) {
				for (int zi = 0; zi < 32; ++zi) {
					for (byte flip = 0; flip < 8; ++flip) {
						final int xfactor = (flip & 0x1) > 0 ? -1 : 1;
						final int yfactor = (flip & 0x2) > 0 ? -1 : 1;
						final int zfactor = (flip & 0x4) > 0 ? -1 : 1;

						int ycalc = y + yi * yfactor;
						ycalc = ycalc > 255 ? 255 : ycalc;
						ycalc = ycalc < 1 ? 1 : ycalc;

						final int xvalue = x + xi * xfactor;
						final int yvalue = ycalc;
						final int zvalue = z + zi * zfactor;
						Block bid = this.world.getBlock(xvalue, yvalue, zvalue);
						final int bmet = this.world.getBlockMetadata(xvalue, yvalue, zvalue);

						if (bid == NyxBlocks.cryingObsidian && bmet == 1) {
							bid = this.world.getBlock(xvalue, yvalue + 1, zvalue);
							final Block bid2 = this.world.getBlock(xvalue, yvalue + 2, zvalue);
							if (bid == Blocks.air && bid2 == Blocks.air) {
								par1Entity.setLocationAndAngles(xvalue, yvalue + 1, zvalue, par1Entity.rotationYaw,
										0.0F);
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
