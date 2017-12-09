package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockObsidianSanctified extends IaSBlockDirectional {
	public NyxBlockObsidianSanctified(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setLightColor(0.9F, 0.8F, 1.0F);
		setResistance(2000.0F);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return type != EnumCreatureType.monster;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.PURE;
	}

	@Override
	public float getBlockHardness(World w, int x, int y, int z) {
		return (w.getBlockMetadata(x, y, z) != 0 ? -1 : Blocks.obsidian.getBlockHardness(w, x, y, z));
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 8;
	}

	@Override
	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_) {
		return p_149677_1_.getLightBrightnessForSkyBlocks(p_149677_2_, p_149677_3_, p_149677_4_, 15);
	}

	@Override
	public int getMobilityFlag() {
		return 2;
	}

}
