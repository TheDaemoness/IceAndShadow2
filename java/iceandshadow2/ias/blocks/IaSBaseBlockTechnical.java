package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.material.Material;

public class IaSBaseBlockTechnical extends IaSBaseBlockSingle implements IIaSTechnicalBlock {

	public IaSBaseBlockTechnical(EnumIaSModule mod, String texName, Material mat) {
		super(mod, texName, mat);
		disableStats();
	}

	@Override
	public final IaSBaseBlockTechnical register() {
		IaSRegistration.register(this);
		return this;
	}
}
