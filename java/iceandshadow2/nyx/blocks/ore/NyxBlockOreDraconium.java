package iceandshadow2.nyx.blocks.ore;

import net.minecraft.world.IBlockAccess;

public class NyxBlockOreDraconium extends NyxBlockOre {

	public NyxBlockOreDraconium(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 3);
		this.setHardness(10.0F);
		this.setLuminescence(0.3F);
		this.setLightColor(0.5F, 0.0F, 0.0F);
		this.setResistance(7.5F);
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 0;
	}
}
