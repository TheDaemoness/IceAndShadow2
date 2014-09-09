package iceandshadow2.ias.blocks;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.material.Material;

public class IaSBaseBlockSingle extends IaSBaseBlock {
	
	public IaSBaseBlockSingle(EnumIaSModule mod, String texName, Material mat) {
		super(mod, mat);
		this.setBlockName(mod.prefix+texName);
		this.setBlockTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
	}
	
	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+MODULE.prefix+getModName();
	}
}
