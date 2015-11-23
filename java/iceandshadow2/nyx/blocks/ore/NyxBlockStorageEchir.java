package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import net.minecraft.block.material.Material;

public class NyxBlockStorageEchir extends IaSBaseBlockMulti {

	public NyxBlockStorageEchir(String texName) {
		super(EnumIaSModule.NYX, texName, Material.iron, 6);
		this.setHarvestLevel("pickaxe", 0);
		setHardness(2.0F);
		setLuminescence(0.2F);
		setLightColor(0.75F, 1.0F, 0.75F);
		setResistance(20.0F);
	}

}
