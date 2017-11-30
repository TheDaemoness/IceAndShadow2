package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class IaSBaseBlockFalling extends BlockFalling implements IIaSModName {
	public final EnumIaSModule MODULE;

	public IaSBaseBlockFalling(EnumIaSModule mod, String texName, Material mat) {
		super(mat);
		setBlockName(mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix
				+ texName);
		this.MODULE = mod;
		this.setCreativeTab(IaSCreativeTabs.blocks);
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return this.MODULE;
	}

	@Override
	public String getModName() {
		return getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + this.MODULE.prefix + getModName();
	}

	public final IaSBaseBlockFalling register() {
		IaSRegistration.register(this);
		return this;
	}

}
