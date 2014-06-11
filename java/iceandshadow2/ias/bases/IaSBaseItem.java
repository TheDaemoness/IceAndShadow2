package iceandshadow2.ias.bases;

import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public abstract class IaSBaseItem extends Item {
	public final EnumIaSModule MODULE;
	
	protected IaSBaseItem(EnumIaSModule mod) {
		super();
		MODULE = mod;
	}
	
	public final IaSBaseItem register() {
		IaSRegistration.register(this);
		return this;
	}

	public abstract String getModName();
}
