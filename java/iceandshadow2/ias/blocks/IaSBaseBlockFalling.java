package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class IaSBaseBlockFalling extends BlockFalling implements IIaSModName {
	public final EnumIaSModule MODULE;

	public IaSBaseBlockFalling(EnumIaSModule mod, String texName, Material mat) {
		super(mat);
		this.setBlockName(mod.prefix + texName);
		this.setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix
				+ texName);
		MODULE = mod;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public String getModName() {
		return this.getUnlocalizedName().substring(5);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + MODULE.prefix + getModName();
	}

	public final IaSBaseBlockFalling register() {
		IaSRegistration.register(this);
		return this;
	}

}
