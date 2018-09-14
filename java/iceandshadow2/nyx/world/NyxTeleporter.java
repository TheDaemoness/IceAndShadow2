package iceandshadow2.nyx.world;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsCentral;
import iceandshadow2.styx.Styx;

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
		world = par1WorldServer;
		random = new Random(par1WorldServer.getSeed());
	}

	private void placeInCentralNyx(Entity par1Entity, int x, int y, int z) {
		final int yMax = Math.min(156, y) + GenRuinsCentral.PLATFORM_OFFSET;
		for (int i = 0; i < 16; ++i)
			world.getChunkProvider().loadChunk((i & 3) - 2, (i >> 2) - 2);
		for (y = 64; y < yMax; ++y) {
			Block bid = world.getBlock(x, y, z);
			final int bmet = world.getBlockMetadata(x, y, z);

			if (bid == NyxBlocks.sanguineObsidian && bmet == 1) {
				bid = world.getBlock(x, y + 1, z);
				final Block bid2 = world.getBlock(x, y + 2, z);
				if (bid == Styx.reserved && bid2 == Styx.reserved)
					break;
			}
		}
		par1Entity.setLocationAndAngles(x + 0.5, y + 1.1, z + 0.5, par1Entity.rotationYaw, 0.0F);
	}

	private void placeInOverworld(Entity par1Entity, int x, int z) {
		int y = 5;
		for (; y + 2 <= 255; ++y) {
			if (!world.isAirBlock(x, y, z))
				continue;
			if (!world.isAirBlock(x, y + 1, z))
				continue;
			if (!world.isAirBlock(x, y + 2, z))
				continue;
			break;
		}
		if (y == 253)
			y = 255;
		if (world.getBlock(x, y - 1, z) == Blocks.water)
			world.setBlock(x, y - 1, z, Blocks.ice);
		else if (world.getBlock(x, y - 1, z) == Blocks.lava)
			world.setBlock(x, y - 1, z, Blocks.cobblestone);
		else if (world.getBlock(x, y - 1, z) == Blocks.cactus)
			world.setBlock(x, y - 1, z, Blocks.sandstone);
		else if (world.getBlock(x, y - 1, z) == Blocks.fire)
			world.setBlock(x, y - 1, z, Blocks.air);
		par1Entity.setLocationAndAngles(x + 0.5, y + 1.0, z + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
	}

	@Override
	public void placeInPortal(Entity par1Entity, double x, double y, double z, float par8) {
		if (world.provider.dimensionId == IaSFlags.dim_nyx_id)
			placeInCentralNyx(par1Entity, 0, world.getPrecipitationHeight(0, 0), 0);
		else
			placeInOverworld(par1Entity, (int) x, (int) z);
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}
}
