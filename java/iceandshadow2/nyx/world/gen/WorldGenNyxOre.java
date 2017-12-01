package iceandshadow2.nyx.world.gen;

import iceandshadow2.nyx.NyxBlocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenNyxOre extends WorldGenerator {

	private final Block ore;
	private final int numberOfBlocks;
	private final Block target;
	private int mineableBlockMeta;

	public WorldGenNyxOre(Block ore, int count) {
		this(ore, count, NyxBlocks.stone);
	}

	public WorldGenNyxOre(Block ore, int count, Block target) {
		this.ore = ore;
		this.numberOfBlocks = count;
		this.target = target;
	}

	public WorldGenNyxOre(Block ore, int count, int meta) {
		this(ore, count);
		this.mineableBlockMeta = meta;
	}

	public WorldGenNyxOre(Block ore, int count, int meta, Block target) {
		this(ore, count, target);
		this.mineableBlockMeta = meta;
	}

	@Override
	public boolean generate(World w, Random r, int x, int y, int z) {
		final float f = r.nextFloat() * (float) Math.PI;
		final double d0 = x + 8 + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		final double d1 = x + 8 - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		final double d2 = z + 8 + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		final double d3 = z + 8 - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		final double d4 = y + r.nextInt(3) - 2;
		final double d5 = y + r.nextInt(3) - 2;

		for (int i = 0; i <= this.numberOfBlocks; ++i) {
			final double d6 = d0 + (d1 - d0) * i / this.numberOfBlocks;
			final double d7 = d4 + (d5 - d4) * i / this.numberOfBlocks;
			final double d8 = d2 + (d3 - d2) * i / this.numberOfBlocks;
			final double d9 = r.nextDouble() * this.numberOfBlocks / 16.0D;
			final double d10 = (MathHelper.sin(i * (float) Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			final double d11 = (MathHelper.sin(i * (float) Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			final int x1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			final int y1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			final int z1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			final int x2 = MathHelper.floor_double(d6 + d10 / 2.0D);
			final int y2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			final int z2 = MathHelper.floor_double(d8 + d10 / 2.0D);

			for (int xit = x1; xit <= x2; ++xit) {
				final double d12 = (xit + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D) {
					for (int yit = y1; yit <= y2; ++yit) {
						final double d13 = (yit + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D) {
							for (int zit = z1; zit <= z2; ++zit) {
								final double d14 = (zit + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
									if (w.getBlock(xit, yit, zit).isReplaceableOreGen(w, xit, yit, zit, this.target)) {
										w.setBlock(xit, yit, zit, this.ore, this.mineableBlockMeta, 2);
										w.updateLightByType(EnumSkyBlock.Block, xit, yit, zit);
										w.updateLightByType(EnumSkyBlock.Sky, xit, yit, zit);
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
