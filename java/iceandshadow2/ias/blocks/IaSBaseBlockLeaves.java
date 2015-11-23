package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.util.IaSRegistration;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class IaSBaseBlockLeaves extends BlockLeavesBase implements
IIaSModName, IShearable {

	int[] field_150128_a; // Dammit Mojo.

	@SideOnly(Side.CLIENT)
	IIcon iconFancy, iconFast;

	private final EnumIaSModule MODULE;

	public IaSBaseBlockLeaves(EnumIaSModule mod, String texName) {
		super(Material.leaves, false);
		this.MODULE = mod;
		setBlockName(mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix
				+ texName);
		setTickRandomly(true);
		setCreativeTab(IaSCreativeTabs.blocks);
		setHardness(0.3F);
		setLightOpacity(2);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z) {

		final int i2 = world.getBlockMetadata(x, y, z);

		if ((i2 & 8) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, i2 | 8, 4);
		}
		world.setBlockMetadataWithNotify(x, y, z,
				world.getBlockMetadata(x, y, z) | 8, 4);
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
		final byte b0 = 1;
		final int i1 = b0 + 1;

		if (w.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
			for (int j1 = -b0; j1 <= b0; ++j1) {
				for (int k1 = -b0; k1 <= b0; ++k1) {
					for (int l1 = -b0; l1 <= b0; ++l1) {
						final Block block = w.getBlock(x + j1, y + k1, z + l1);
						if (block.isLeaves(w, x + j1, y + k1, z + l1)) {
							block.beginLeavesDecay(w, x + j1, y + k1, z + l1);
						}
					}
				}
			}
		}
	}

	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(this, 1, meta & 3);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		final float var5 = 0.0125F;
		return AxisAlignedBB.getBoundingBox(par2 + var5, par3 + var5, par4
				+ var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
	}

	@Override
	public abstract ArrayList<ItemStack> getDrops(World world, int x, int y,
			int z, int meta, int fortune);

	@Override
	public EnumIaSModule getIaSModule() {
		return this.MODULE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		Minecraft.getMinecraft();
		if (Minecraft.isFancyGraphicsEnabled())
			return this.iconFancy;
		else
			return this.iconFast;
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + getModName();
	}

	@Override
	public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube() {
		Minecraft.getMinecraft();
		return !Minecraft.isFancyGraphicsEnabled();
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x,
			int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world,
			int x, int y, int z, int fortune) {
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}

	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_,
			int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		// Narf.
	}

	public final Block register() {
		IaSRegistration.register(this);
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.iconFancy = reg.registerIcon(getTexName() + "Fancy");
		this.iconFast = reg.registerIcon(getTexName() + "Fast");
		this.blockIcon = this.iconFast;
	}

	private void removeLeaves(World p_150126_1_, int p_150126_2_,
			int p_150126_3_, int p_150126_4_) {
		this.dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_,
				p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_,
						p_150126_3_, p_150126_4_), 0);
		p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_);
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if (!w.isRemote) {
			final int l = w.getBlockMetadata(x, y, z);

			if ((l & 8) != 0 && (l & 4) == 0) {
				final byte b0 = 4;
				final int i1 = b0 + 1;
				final byte b1 = 32;
				final int j1 = b1 * b1;
				final int k1 = b1 / 2;

				if (this.field_150128_a == null) {
					this.field_150128_a = new int[b1 * b1 * b1];
				}

				int l1;

				if (w.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1,
						z + i1)) {
					int i2;
					int j2;

					for (l1 = -b0; l1 <= b0; ++l1) {
						for (i2 = -b0; i2 <= b0; ++i2) {
							for (j2 = -b0; j2 <= b0; ++j2) {
								final Block block = w.getBlock(x + l1, y + i2,
										z + j2);

								if (!block.canSustainLeaves(w, x + l1, y + i2,
										z + j2)) {
									if (block.isLeaves(w, x + l1, y + i2, z
											+ j2)) {
										this.field_150128_a[(l1 + k1) * j1
										                    + (i2 + k1) * b1 + j2 + k1] = -2;
									} else {
										this.field_150128_a[(l1 + k1) * j1
										                    + (i2 + k1) * b1 + j2 + k1] = -1;
									}
								} else {
									this.field_150128_a[(l1 + k1) * j1
									                    + (i2 + k1) * b1 + j2 + k1] = 0;
								}
							}
						}
					}

					for (l1 = 1; l1 <= 4; ++l1) {
						for (i2 = -b0; i2 <= b0; ++i2) {
							for (j2 = -b0; j2 <= b0; ++j2) {
								for (int k2 = -b0; k2 <= b0; ++k2) {
									if (this.field_150128_a[(i2 + k1) * j1
									                        + (j2 + k1) * b1 + k2 + k1] == l1 - 1) {
										if (this.field_150128_a[(i2 + k1 - 1)
										                        * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.field_150128_a[(i2 + k1 - 1)
											                    * j1 + (j2 + k1) * b1 + k2
											                    + k1] = l1;
										}

										if (this.field_150128_a[(i2 + k1 + 1)
										                        * j1 + (j2 + k1) * b1 + k2 + k1] == -2) {
											this.field_150128_a[(i2 + k1 + 1)
											                    * j1 + (j2 + k1) * b1 + k2
											                    + k1] = l1;
										}

										if (this.field_150128_a[(i2 + k1) * j1
										                        + (j2 + k1 - 1) * b1 + k2 + k1] == -2) {
											this.field_150128_a[(i2 + k1) * j1
											                    + (j2 + k1 - 1) * b1 + k2
											                    + k1] = l1;
										}

										if (this.field_150128_a[(i2 + k1) * j1
										                        + (j2 + k1 + 1) * b1 + k2 + k1] == -2) {
											this.field_150128_a[(i2 + k1) * j1
											                    + (j2 + k1 + 1) * b1 + k2
											                    + k1] = l1;
										}

										if (this.field_150128_a[(i2 + k1) * j1
										                        + (j2 + k1) * b1 + k2 + k1 - 1] == -2) {
											this.field_150128_a[(i2 + k1) * j1
											                    + (j2 + k1) * b1 + k2 + k1
											                    - 1] = l1;
										}

										if (this.field_150128_a[(i2 + k1) * j1
										                        + (j2 + k1) * b1 + k2 + k1 + 1] == -2) {
											this.field_150128_a[(i2 + k1) * j1
											                    + (j2 + k1) * b1 + k2 + k1
											                    + 1] = l1;
										}
									}
								}
							}
						}
					}
				}

				l1 = this.field_150128_a[k1 * j1 + k1 * b1 + k1];

				if (l1 >= 0) {
					w.setBlockMetadataWithNotify(x, y, z, l & -9, 4);
				} else {
					removeLeaves(w, x, y, z);
				}
			}
		}
	}

}
