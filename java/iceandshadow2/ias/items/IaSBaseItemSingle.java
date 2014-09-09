package iceandshadow2.ias.items;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IaSBaseItemSingle extends IaSBaseItem {
	
	private final String itemNaem;
	
	public IaSBaseItemSingle(EnumIaSModule mod, String texName) {
		super(mod);
		this.setUnlocalizedName(mod.prefix+"Item"+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		this.itemNaem = texName;
	}

	@Override
	public String getRegName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+MODULE.prefix+itemNaem;
	}

	@Override
	public String getModName() {
		return itemNaem;
	}
}
