package iceandshadow2.ias.items;

import iceandshadow2.ias.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.item.Item;

public abstract class IaSBaseItem extends Item implements IIaSModName {
	private final EnumIaSModule MODULE;
	
	protected IaSBaseItem(EnumIaSModule mod) {
		super();
		MODULE = mod;
	}
	
	public final IaSBaseItem register() {
		IaSRegistration.register(this);
		return this;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}
	
}
