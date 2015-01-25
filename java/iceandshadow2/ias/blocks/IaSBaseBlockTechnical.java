package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.material.Material;

public class IaSBaseBlockTechnical extends IaSBaseBlockSingle implements IIaSTechnicalBlock {

	public IaSBaseBlockTechnical(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
	}
	
	public final IaSBaseBlockTechnical register() {
		IaSRegistration.register(this);
		return this;
	}
}
