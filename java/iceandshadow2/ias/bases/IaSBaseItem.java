package iceandshadow2.ias.bases;

import iceandshadow2.util.EnumIaSModule;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public abstract class IaSBaseItem extends Item {
	public final EnumIaSModule MODULE;
	
	protected IaSBaseItem(EnumIaSModule mod) {
		super();
		MODULE = mod;
	}

	public abstract String getModName();
}
