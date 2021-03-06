package iceandshadow2.ias.util;

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

	public int y() {return y;}

	public void plus(BlockPos3 b) {
		plus((BlockPos2)b);
		y = (short)(b.y+y);
	}
	public Block block(World w) {return block(w, y);}
	public Block block(World w, Block newBlock) {return block(w, y, newBlock);}
	public Block block(World w, Block newBlock, int newMeta) {return block(w, y, newBlock, newMeta);}
}
