package iceandshadow2.nyx.blocks.ore;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.blocks.NyxBlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public abstract class NyxBlockStorage extends IaSBaseBlockSingle {
	
	public NyxBlockStorage(String texName) {
		super(EnumIaSModule.NYX, texName, Material.rock);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(1.0F);
		this.setLuminescence(0.2F);
		this.setResistance(5.0F);
	}
	
	public abstract ItemStack getItems();
}
