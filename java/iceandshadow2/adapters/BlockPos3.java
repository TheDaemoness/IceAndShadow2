package iceandshadow2.adapters;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockPos3 extends BlockPos2 {
	protected short y;

	public BlockPos3(BlockPos3 other) {
		this(other, other.y);
	}
	public BlockPos3(BlockPos2 base, int y) {
		super(base);
		this.y = (short)y;
	}
	public BlockPos3(long x, int y, long z) {
		super(x, z);
		this.y = (short)y;
	}
	public BlockPos3(Entity e) {
		super(e);
		y = (short)e.posY;
	}
	

	public double distEuclid2(BlockPos3 b) {
		final int dy = b.y - y;
		return distEuclid2((BlockPos2)b) + dy*dy;
	}
	public double distEuclid(BlockPos3 b) {
		return Math.sqrt(distEuclid2(b));
	}
	public double distManhattan(BlockPos3 b) {
		final int dy = b.y - y;
		return distManhattan((BlockPos2)b) + Math.abs(dy);
	}

	public int y() {return y;}

	public void plus(BlockPos3 b) {
		plus((BlockPos2)b);
		y = (short)(b.y+y);
	}
	public Block block(World w) {return block(w, y);}
	public Block block(World w, Block newBlock) {return block(w, y, newBlock);}
	public Block block(World w, Block newBlock, int newMeta) {return block(w, y, newBlock, newMeta);}
	
	public int variant(World w) {
		return variant(w, y);
	}
}
