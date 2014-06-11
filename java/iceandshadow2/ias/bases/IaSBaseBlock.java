package iceandshadow2.ias.bases;

import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class IaSBaseBlock extends Block {
	public final EnumIaSModule MODULE;
	
	protected IaSBaseBlock(EnumIaSModule mod, Material mat) {
		super(mat);
		MODULE = mod;
	}
	
	public final IaSBaseBlock register() {
		IaSRegistration.register(this);
		return this;
	}
	
	public abstract String getModName();
}
