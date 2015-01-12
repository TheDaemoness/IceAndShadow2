package iceandshadow2.nyx.blocks;

import net.minecraft.block.material.Material;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;

public class NyxBlockHookTightrope extends IaSBaseBlockSingle implements IIaSTechnicalBlock {

	public NyxBlockHookTightrope(String texName) {
		super(EnumIaSModule.NYX, texName, Material.iron);
		this.setStepSound(soundTypeMetal);
	}

}
