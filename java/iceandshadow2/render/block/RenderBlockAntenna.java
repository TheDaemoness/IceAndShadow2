package iceandshadow2.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderBlockAntenna implements ISimpleBlockRenderingHandler {

	public static final int id;

	static {
		id = RenderingRegistry.getNextAvailableRenderId();
		try {
			RenderingRegistry.registerBlockHandler(RenderBlockAntenna.class.newInstance());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getRenderId() {
		return id;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		// Does nothing.
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		renderer.setRenderBounds(0.0, 0.0, 0.45, 1.0, 0.125, 0.55);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.45, 0.0, 0.0, 0.55, 0.125, 1.0);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.2, 0.0, 0.2, 0.8, 0.25, 0.8);
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(0.45, 0.0, 0.45, 0.55, 1.2, 0.55);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

}
