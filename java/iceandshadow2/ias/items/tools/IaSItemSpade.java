package iceandshadow2.ias.items.tools;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemSpade;

public class IaSItemSpade extends ItemSpade implements IIaSModName {
	
	private final EnumIaSModule MODULE;

	public IaSItemSpade(EnumIaSModule m, String id, ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setUnlocalizedName(m.prefix+id);
		MODULE = m;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+getModName();
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

}