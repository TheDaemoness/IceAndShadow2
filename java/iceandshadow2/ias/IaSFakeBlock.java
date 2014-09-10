package iceandshadow2.ias;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.EnumIaSModule;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IaSFakeBlock extends Block {
	
	public IaSFakeBlock(EnumIaSModule mod, String texName) {
		super(Material.dragonEgg);
		this.setBlockName("fake"+mod.prefix+texName);
		this.setBlockTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		IaSRegistration.register(this);
	}
	
	public Item getItem() {
		return this.getItem(null, 0, 0, 0);
	}
}