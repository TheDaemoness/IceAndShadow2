package iceandshadow2.ias.items;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IaSBaseItemSingle extends IaSBaseItem {
	
	public IaSBaseItemSingle(EnumIaSModule mod, String texName) {
		super(mod);
		this.setUnlocalizedName(mod.prefix+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+getModName();
	}
}
