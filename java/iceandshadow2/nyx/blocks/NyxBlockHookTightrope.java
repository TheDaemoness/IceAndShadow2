package iceandshadow2.nyx.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.ias.interfaces.IIaSTechnicalBlock;
import iceandshadow2.nyx.NyxBlocks;

public class NyxBlockHookTightrope extends IaSBaseBlockSingle implements IIaSTechnicalBlock {

	public NyxBlockHookTightrope(String texName) {
		super(EnumIaSModule.NYX, texName, Material.iron);
		this.setStepSound(soundTypeMetal);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> lilili = new ArrayList<ItemStack>();
		lilili.add(new ItemStack(NyxBlocks.hookClimbing));
		return lilili;
	}

	
	
}
