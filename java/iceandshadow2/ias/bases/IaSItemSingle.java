package iceandshadow2.ias.bases;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.compat.IaSRegistration;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IaSItemSingle extends IaSBaseItem {
	
	public IaSItemSingle(EnumIaSModule mod, String texName) {
		super(mod);
		String naem = mod.prefix+"Item"+texName;
		this.setUnlocalizedName(naem);
		this.setTextureName(IceAndShadow2.MODID+':'+naem);
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
}
