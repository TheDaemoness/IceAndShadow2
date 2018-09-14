package iceandshadow2.ias.util.gen;

import iceandshadow2.adapters.BlockPos3;

public class SupplierBlockPosCuboid extends SupplierBlockPos {
	protected final long xL, zL, xR, zR;
	protected final int yL, yR;
	protected long i; // Iterator variable.
	protected final long iLim;

	public SupplierBlockPosCuboid(int xL, int yL, int zL, int xH, int yH, int zH) {
		i = 0;
		this.xL = xL;
		xR = xH - xL + 1;
		this.yL = yL;
		yR = yH - yL + 1;
		this.zL = zL;
		zR = zH - zL + 1;
		iLim = xR * yR * zR;
	}

	// Convert i to a block position. May eventually use something fancier for this.
	protected BlockPos3 convertI(long i) {
		final int yr = y + yL + (int) (i / (xR * zR));
		final long layerPos = i % (xR * zR), xr = x + xL + (layerPos % xR), zr = z + zL + (layerPos / xR);
		return new BlockPos3(xr, yr, zr);
	}

	@Override
	public boolean reset() {
		i = 0;
		return true;
	}

	@Override
	protected boolean thisHasNext() {
		return i < iLim;
	}

	@Override
	protected BlockPos3 thisNext() {
		return convertI(i++);
	}

}
