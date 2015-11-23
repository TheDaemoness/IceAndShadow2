package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;

public class NyxBlockBrickFrozen extends IaSBaseBlockSingle {
	public NyxBlockBrickFrozen(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		setResistance(NyxBlockStone.RESISTANCE);
		setHardness(NyxBlockStone.HARDNESS);
		this.setHarvestLevel("pickaxe", 0);
		setLightOpacity(12);
	}
}
