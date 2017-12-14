package iceandshadow2.nyx.world.gen;

import iceandshadow2.nyx.NyxBlocks;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GenInfestedTrees extends WorldGenerator {
	/**
	 * Contains three sets of two values that provide complimentary indices for
	 * a given 'major' index - 1 and 2 for 0, 0 and 2 for 1, and 0 and 1 for 2.
	 */
	static final byte[] otherCoordPairs = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };

	/** random seed for GenBigTree */
	Random rand = new Random();

	/** Reference to the World object. */
	World worldObj;
	int[] basePos = new int[] { 0, 0, 0 };
	int heightLimit;
	int height;
	double heightAttenuation = 0.618D;
	double branchDensity = 1.0D;
	double branchSlope = 0.381D;
	double scaleWidth = 1.0D;
	double leafDensity = 1.0D;

	/**
	 * Currently always 1, can be set to 2 in the class constructor to generate
	 * a double-sized tree trunk for big trees.
	 */
	int trunkSize = 1;

	/**
	 * Sets the limit of the random value used to initialize the height limit.
	 */
	int heightLimitLimit = 12;

	/**
	 * Sets the distance limit for how far away the generator will populate
	 * leaves from the base leaf node.
	 */
	int leafDistanceLimit = 4;

	/** Contains a list of a points at which to generate groups of leaves. */
	int[][] leafNodes;

	public GenInfestedTrees() {
		super();
	}

	/**
	 * Checks a line of blocks in the world from the first coordinate to triplet
	 * to the second, returning the distance (in blocks) before a non-air,
	 * non-leaf block is encountered and/or the end is encountered.
	 */
	int checkBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger) {
		final int[] aint2 = new int[] { 0, 0, 0 };
		byte b0 = 0;
		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];

			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}

		if (aint2[b1] == 0)
			return -1;
		else {
			final byte b2 = GenInfestedTrees.otherCoordPairs[b1];
			final byte b3 = GenInfestedTrees.otherCoordPairs[b1 + 3];
			byte b4;

			if (aint2[b1] > 0)
				b4 = 1;
			else
				b4 = -1;

			final double d0 = (double) aint2[b2] / (double) aint2[b1];
			final double d1 = (double) aint2[b3] / (double) aint2[b1];
			final int[] aint3 = new int[] { 0, 0, 0 };
			int i = 0;
			int j;

			for (j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = par1ArrayOfInteger[b1] + i;
				aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + i * d0);
				aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + i * d1);
				final Block block = worldObj.getBlock(aint3[0], aint3[1], aint3[2]);

				if (block != null && !block.isAir(worldObj, aint3[0], aint3[1], aint3[2])
						&& !block.isLeaves(worldObj, aint3[0], aint3[1], aint3[2]) && !(block != Blocks.snow)
						&& !(block != Blocks.snow_layer))
					break;
			}

			return i == j ? -1 : Math.abs(i);
		}
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int x, int y, int z) {
		worldObj = par1World;
		final long l = par2Random.nextLong();
		rand.setSeed(l);
		basePos[0] = x;
		basePos[1] = y;
		basePos[2] = z;

		if (heightLimit == 0)
			heightLimit = 6 + rand.nextInt(heightLimitLimit);

		if (!validTreeLocation())
			return false;
		else {
			generateLeafNodeList();
			generateLeaves();
			generateTrunk();
			generateLeafNodeBases();
			if (par2Random.nextInt(7) == 0)
				generateBerryPod();
			return true;
		}
	}

	private void generateBerryPod() {
		final int h = 2 + rand.nextInt(3);
		final int dir = rand.nextInt(4);
		int x = basePos[0];
		final int y = basePos[1] + h;
		int z = basePos[2];
		int meta = dir;
		if (dir == 0)
			--z; // North
		else if (dir == 1)
			++x; // East
		else if (dir == 2)
			++z; // South
		else if (dir == 3)
			--x; // West
		meta |= rand.nextInt(3) == 0 ? 0x8 : 0x4;
		if (worldObj.isAirBlock(x, y, z) || worldObj.getBlock(x, y, z) == NyxBlocks.infestLeaves)
			worldObj.setBlock(x, y, z, NyxBlocks.silkBerryPod, meta, 0x2);
	}

	/**
	 * Generates the leaves surrounding an individual entry in the leafNodes
	 * list.
	 */
	void generateLeafNode(int par1, int par2, int par3) {
		int l = par2;

		for (final int i1 = par2 + leafDistanceLimit; l < i1; ++l) {
			final float f = leafSize(l - par2);
			genTreeLayer(par1, l, par3, f, (byte) 1, NyxBlocks.infestLeaves);
		}
	}

	/**
	 * Generates additional wood blocks to fill out the bases of different leaf
	 * nodes that would otherwise degrade.
	 */
	void generateLeafNodeBases() {
		int i = 0;
		final int j = leafNodes.length;

		for (final int[] aint = new int[] { basePos[0], basePos[1], basePos[2] }; i < j; ++i) {
			final int[] aint1 = leafNodes[i];
			final int[] aint2 = new int[] { aint1[0], aint1[1], aint1[2] };
			aint[1] = aint1[3];
			final int k = aint[1] - basePos[1];

			if (leafNodeNeedsBase(k))
				placeBlockLine(aint, aint2, NyxBlocks.infestLog);
		}
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by
	 * generateLeaves.
	 */
	void generateLeafNodeList() {
		height = (int) (heightLimit * heightAttenuation);

		if (height >= heightLimit)
			height = heightLimit - 1;

		int i = (int) (1.382D + Math.pow(leafDensity * heightLimit / 13.0D, 2.0D));

		if (i < 1)
			i = 1;

		final int[][] aint = new int[i * heightLimit][4];
		int j = basePos[1] + heightLimit - leafDistanceLimit;
		int k = 1;
		final int l = basePos[1] + height;
		int i1 = j - basePos[1];
		aint[0][0] = basePos[0];
		aint[0][1] = j;
		aint[0][2] = basePos[2];
		aint[0][3] = l;
		--j;

		while (i1 >= 0) {
			int j1 = 0;
			final float f = layerSize(i1);

			if (f < 0.0F) {
				--j;
				--i1;
			} else {
				for (final double d0 = 0.5D; j1 < i; ++j1) {
					final double d1 = scaleWidth * f * (rand.nextFloat() + 0.328D);
					final double d2 = rand.nextFloat() * 2.0D * Math.PI;
					final int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + basePos[0] + d0);
					final int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + basePos[2] + d0);
					final int[] aint1 = new int[] { k1, j, l1 };
					final int[] aint2 = new int[] { k1, j + leafDistanceLimit, l1 };

					if (checkBlockLine(aint1, aint2) == -1) {
						final int[] aint3 = new int[] { basePos[0], basePos[1], basePos[2] };
						final double d3 = Math.sqrt(Math.pow(Math.abs(basePos[0] - aint1[0]), 2.0D)
								+ Math.pow(Math.abs(basePos[2] - aint1[2]), 2.0D));
						final double d4 = d3 * branchSlope;

						if (aint1[1] - d4 > l)
							aint3[1] = l;
						else
							aint3[1] = (int) (aint1[1] - d4);

						if (checkBlockLine(aint3, aint1) == -1) {
							aint[k][0] = k1;
							aint[k][1] = j;
							aint[k][2] = l1;
							aint[k][3] = aint3[1];
							++k;
						}
					}
				}

				--j;
				--i1;
			}
		}

		leafNodes = new int[k][4];
		System.arraycopy(aint, 0, leafNodes, 0, k);
	}

	/**
	 * Generates the leaf portion of the tree as specified by the leafNodes
	 * list.
	 */
	void generateLeaves() {
		int i = 0;

		for (final int j = leafNodes.length; i < j; ++i) {
			final int k = leafNodes[i][0];
			final int l = leafNodes[i][1];
			final int i1 = leafNodes[i][2];
			generateLeafNode(k, l, i1);
		}
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to
	 * generate double-sized trunks by changing a field that is always 1 to 2.
	 */
	void generateTrunk() {
		final int i = basePos[0];
		final int j = basePos[1];
		final int k = basePos[1] + height;
		final int l = basePos[2];
		final int[] aint = new int[] { i, j, l };
		final int[] aint1 = new int[] { i, k, l };
		placeBlockLine(aint, aint1, NyxBlocks.infestLog);

		if (trunkSize == 2) {
			++aint[0];
			++aint1[0];
			placeBlockLine(aint, aint1, NyxBlocks.infestLog);
			++aint[2];
			++aint1[2];
			placeBlockLine(aint, aint1, NyxBlocks.infestLog);
			aint[0] += -1;
			aint1[0] += -1;
			placeBlockLine(aint, aint1, NyxBlocks.infestLog);
		}
	}

	void genTreeLayer(int par1, int par2, int par3, float par4, byte par5, Block par6) {
		final int i1 = (int) (par4 + 0.618D);
		final byte b1 = GenInfestedTrees.otherCoordPairs[par5];
		final byte b2 = GenInfestedTrees.otherCoordPairs[par5 + 3];
		final int[] aint = new int[] { par1, par2, par3 };
		final int[] aint1 = new int[] { 0, 0, 0 };
		int j1 = -i1;
		int k1 = -i1;

		for (aint1[par5] = aint[par5]; j1 <= i1; ++j1) {
			aint1[b1] = aint[b1] + j1;
			k1 = -i1;

			while (k1 <= i1) {
				final double d0 = Math.pow(Math.abs(j1) + 0.5D, 2.0D) + Math.pow(Math.abs(k1) + 0.5D, 2.0D);

				if (d0 > par4 * par4)
					++k1;
				else {
					aint1[b2] = aint[b2] + k1;
					final Block block = worldObj.getBlock(aint1[0], aint1[1], aint1[2]);

					if (block != null && !block.isAir(worldObj, aint1[0], aint1[1], aint1[2])
							&& !block.isLeaves(worldObj, aint1[0], aint1[1], aint1[2]))
						++k1;
					else {
						worldObj.setBlock(aint1[0], aint1[1], aint1[2], par6, 0, 0x8);
						++k1;
					}
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	float layerSize(int par1) {
		if (par1 < heightLimit * 0.3D)
			return -1.618F;
		else {
			final float f = heightLimit / 2.0F;
			final float f1 = heightLimit / 2.0F - par1;
			float f2;

			if (f1 == 0.0F)
				f2 = f;
			else if (Math.abs(f1) >= f)
				f2 = 0.0F;
			else
				f2 = (float) Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));

			f2 *= 0.5F;
			return f2;
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added
	 * to preserve integrity.
	 */
	boolean leafNodeNeedsBase(int par1) {
		return par1 >= heightLimit * 0.2D;
	}

	float leafSize(int par1) {
		return par1 >= 0 && par1 < leafDistanceLimit ? par1 != 0 && par1 != leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
	}

	/**
	 * Places a line of the specified block ID into the world from the first
	 * coordinate triplet to the second.
	 */
	void placeBlockLine(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, Block par3) {
		final int[] aint2 = new int[] { 0, 0, 0 };
		byte b0 = 0;
		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = par2ArrayOfInteger[b0] - par1ArrayOfInteger[b0];

			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1]))
				b1 = b0;
		}

		if (aint2[b1] != 0) {
			final byte b2 = GenInfestedTrees.otherCoordPairs[b1];
			final byte b3 = GenInfestedTrees.otherCoordPairs[b1 + 3];
			byte b4;

			if (aint2[b1] > 0)
				b4 = 1;
			else
				b4 = -1;

			final double d0 = (double) aint2[b2] / (double) aint2[b1];
			final double d1 = (double) aint2[b3] / (double) aint2[b1];
			final int[] aint3 = new int[] { 0, 0, 0 };
			int j = 0;

			for (final int k = aint2[b1] + b4; j != k; j += b4) {
				aint3[b1] = MathHelper.floor_double(par1ArrayOfInteger[b1] + j + 0.5D);
				aint3[b2] = MathHelper.floor_double(par1ArrayOfInteger[b2] + j * d0 + 0.5D);
				aint3[b3] = MathHelper.floor_double(par1ArrayOfInteger[b3] + j * d1 + 0.5D);
				byte b5 = 0;
				final int l = Math.abs(aint3[0] - par1ArrayOfInteger[0]);
				final int i1 = Math.abs(aint3[2] - par1ArrayOfInteger[2]);
				final int j1 = Math.max(l, i1);

				if (j1 > 0)
					if (l == j1)
						b5 = 0x4;
					else if (i1 == j1)
						b5 = 0x8;

				worldObj.setBlock(aint3[0], aint3[1], aint3[2], par3, b5, 0x2);
			}
		}
	}

	/**
	 * Rescales the generator settings, only used in WorldGenBigTree
	 */
	@Override
	public void setScale(double par1, double par3, double par5) {
		heightLimitLimit = (int) (par1 * 12.0D);

		if (par1 > 0.5D)
			leafDistanceLimit = 5;

		scaleWidth = par3;
		leafDensity = par5;
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the
	 * tree, spanning basePos to to the height limit, is valid.
	 */
	boolean validTreeLocation() {
		final int[] aint = new int[] { basePos[0], basePos[1] + 2, basePos[2] };
		final int[] aint1 = new int[] { basePos[0], basePos[1] + heightLimit - 1, basePos[2] };

		final Block soil = worldObj.getBlock(basePos[0], basePos[1] - 1, basePos[2]);
		final boolean isValidSoil = soil != null && soil == Blocks.snow;
		if (!isValidSoil)
			return false;
		else {
			final int j = checkBlockLine(aint, aint1);

			if (j == -1)
				return true;
			else if (j < 6)
				return false;
			else {
				heightLimit = j;
				return true;
			}
		}
	}

}
