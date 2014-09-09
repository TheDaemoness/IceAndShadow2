package iceandshadow2.ias.items;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IIaSModName;
import iceandshadow2.util.EnumIaSModule;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemShears;

public class IaSItemShears extends ItemShears implements IIaSModName {
	
	private final String itemNaem;
	public final EnumIaSModule MODULE;

	public IaSItemShears(EnumIaSModule mod, String texName) {
		super();
		this.setUnlocalizedName(mod.prefix+"Item"+texName);
		this.setTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		MODULE = mod;
		itemNaem = texName;
	}

	@Override
	public String getRegName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getModName() {
		return itemNaem;
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID+':'+MODULE.prefix+itemNaem;
	}

}
