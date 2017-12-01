package iceandshadow2.nyx.blocks.technical;

import java.util.Random;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.blocks.NyxBlockGravel;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockStoneGrowing extends NyxBlockStone implements IIaSTechnicalBlock {

	public NyxBlockStoneGrowing(String id) {
		super(id);
		this.setTickRandomly(true);
		this.setLuminescence(0.1F);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return NyxBlocks.stone.getPickBlock(target, world, x, y, z, player);
	}

	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		if (!w.isRemote) {
			boolean finished = true;
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				// TODO: Pushing mechanics.
				final int
					i = x + dir.offsetX,
					j = y + dir.offsetY,
					k = z + dir.offsetZ;
				final Block bl = w.getBlock(i, j, k);
				if (bl instanceof NyxBlockStone)
					continue;
				finished = false;
				if (bl.isReplaceable(w, i, j, k) || (bl.getMobilityFlag() == 0 && !(bl instanceof NyxBlockGravel))) {
					IaSBlockHelper.breakBlock(w, i, j, k, true);
					w.setBlock(i, j, k, NyxBlocks.gravel);
				}
			}
			if (finished)
				w.setBlock(x, y, z, NyxBlocks.stone);
		}
		super.updateTick(w, x, y, z, r);
	}

	@Override
	public String getTexName() {
		return IceAndShadow2.MODID + ':' + ((IaSBaseBlockSingle) NyxBlocks.stone).getModName();
	}

}
