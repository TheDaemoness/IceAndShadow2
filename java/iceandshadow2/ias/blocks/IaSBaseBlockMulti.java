package iceandshadow2.ias.blocks;

import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.material.Material;

public abstract class IaSBaseBlockMulti extends IaSBaseBlock {

	public IaSBaseBlockMulti(EnumIaSModule mod, Material mat) {
		super(mod, mat);
	}

	public abstract int getSubtypeCount();
	
	public boolean getHasSubtypes() {
		return true;
	}
	
    public String getUnlocalizedName(int val) {
		return super.getUnlocalizedName()+val;
    }

	public abstract String getHumanReadableName(int i);

}
