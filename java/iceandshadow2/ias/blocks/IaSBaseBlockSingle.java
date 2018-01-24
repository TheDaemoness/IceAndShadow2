package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import net.minecraft.block.material.Material;

public class IaSBaseBlockSingle extends IaSBaseBlock {

	public IaSBaseBlockSingle(EnumIaSModule mod, String texName, Material mat) {
		super(mod, mat);
		setBlockName(mod.prefix + texName);
		setBlockTextureName(getTextureName());
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}
}
