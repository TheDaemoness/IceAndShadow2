package iceandshadow2.adapters;

public interface IWorldBlocks {
	public BlockInstance getBlock(BlockPos3 pos);

	public void setBlock(BlockPos3 pos, BlockInstance bl);
}
