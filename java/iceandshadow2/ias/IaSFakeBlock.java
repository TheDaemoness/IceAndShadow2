package iceandshadow2.ias;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class IaSFakeBlock extends Block {

	public IaSFakeBlock(EnumIaSModule mod, String texName) {
		super(Material.dragonEgg);
		setBlockName("fake" + mod.prefix + texName);
		setBlockTextureName(IceAndShadow2.MODID + ':' + mod.prefix + texName);
		IaSRegistration.register(this);
	}

	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
}