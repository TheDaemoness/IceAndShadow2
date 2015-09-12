package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import net.minecraft.block.material.Material;

public class NyxBlockBrickFrozen extends IaSBaseBlockSingle {
	public NyxBlockBrickFrozen(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setResistance(NyxBlockStone.RESISTANCE);
		this.setHardness(NyxBlockStone.HARDNESS);
		this.setHarvestLevel("pickaxe", 0);
		this.setLightOpacity(12);
	}
}
