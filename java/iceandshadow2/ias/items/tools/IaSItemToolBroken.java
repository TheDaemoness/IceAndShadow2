package iceandshadow2.ias.items.tools;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSModName;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemAxe;

public class IaSItemToolBroken extends IaSBaseItemMulti {
	
	public IaSItemToolBroken(String id) {
		super(EnumIaSModule.NYX,id,4);
	}

}