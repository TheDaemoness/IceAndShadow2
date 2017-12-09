package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class IaSBaseBlock extends Block implements IIaSModName, IIaSAspect {
	public final EnumIaSModule MODULE;

	private float lum;
	private float lightRed, lightGreen, lightBlue;

	protected IaSBaseBlock(EnumIaSModule mod, Material mat) {
		super(mat);
		MODULE = mod;
		if (mod == EnumIaSModule.NYX && !(this instanceof IIaSTechnicalBlock))
			setCreativeTab(IaSCreativeTabs.blocks);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return null;
	}

	@Override
	public EnumIaSModule getIaSModule() {
		return MODULE;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	public IaSBaseBlock register() {
		IaSRegistration.register(this);
		return this;
	}

	public IaSBaseBlock setLightColor(float r, float g, float b) {
		lightRed = r;
		lightGreen = g;
		lightBlue = b;
		return this;
	}

	public IaSBaseBlock setLuminescence(float lum) {
		this.lum = lum;
		if (this.getLightOpacity() >= 15)
			setLightOpacity(14);
		setLightLevel(lum);
		return this;
	}
}
