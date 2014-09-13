package iceandshadow2.nyx.blocks.ore;

import net.minecraft.world.IBlockAccess;

public class NyxBlockOreEchir extends NyxBlockOre {

	public NyxBlockOreEchir(String texName) {
		super(texName);
		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(10.0F);
		this.setLuminescence(0.2F);
		this.setLightColor(0.75F, 1.0F, 0.75F);
		this.setResistance(20.0F);
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 0;
	}
}
