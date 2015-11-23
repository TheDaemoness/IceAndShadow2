package iceandshadow2.ias.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class IaSBaseBlock extends Block implements IIaSModName {
	public final EnumIaSModule MODULE;

	private float lum;
	private float lightRed, lightGreen, lightBlue;

	protected IaSBaseBlock(EnumIaSModule mod, Material mat) {
		super(mat);
		this.MODULE = mod;
		if(mod == EnumIaSModule.NYX && !(this instanceof IIaSTechnicalBlock))
			setCreativeTab(IaSCreativeTabs.blocks);
	}

	public IaSBaseBlock setLuminescence(float lum) {
		this.lum = lum;
		if(this.getLightOpacity() >= 15)
			setLightOpacity(14);
		setLightLevel(lum);
		return this;
	}

	public IaSBaseBlock setLightColor(float r, float g, float b) {
		this.lightRed = r;
		this.lightGreen = g;
		this.lightBlue = b;
		return this;
	}

	public IaSBaseBlock register() {
		IaSRegistration.register(this);
		return this;
	}

	@Override
	public int getMobilityFlag() {
		return 1;
	}

	@Override
	public EnumIaSModule getIaSModule() {return this.MODULE;}
}
