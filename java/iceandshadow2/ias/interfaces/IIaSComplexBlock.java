package iceandshadow2.ias.interfaces;

import iceandshadow2.boilerplate.Cuboid;

public interface IIaSComplexBlock {
	public Cuboid getSubCuboid(int meta, int pass);

	public boolean usesDefaultComplexBlockRenderer();
}
