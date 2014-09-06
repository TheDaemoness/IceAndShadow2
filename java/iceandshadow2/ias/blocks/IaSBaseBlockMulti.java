package iceandshadow2.ias.blocks;

import iceandshadow2.ias.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.material.Material;

public abstract class IaSBaseBlockMulti extends IaSBaseBlock implements IIaSModName {
	
	public IaSBaseBlockMulti(EnumIaSModule mod, String id, Material mat) {
		super(mod, mat);
		this.setBlockName(mod.prefix+"MBlock"+id);
	}

	public abstract int getSubtypeCount();
	
	public boolean getHasSubtypes() {
		return true;
	}
	
    public String getUnlocalizedName(int val) {
		return super.getUnlocalizedName()+val;
    }

}
