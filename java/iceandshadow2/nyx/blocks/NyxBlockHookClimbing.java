package iceandshadow2.nyx.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;

public class NyxBlockHookClimbing extends BlockFence implements IIaSTechnicalBlock {

	public NyxBlockHookClimbing(String texName) {
		super(IceAndShadow2.MODID+':'+EnumIaSModule.NYX.prefix+texName, Material.iron);
		this.setBlockName(EnumIaSModule.NYX.prefix+texName);
		this.setStepSound(soundTypeMetal);
	}

}
