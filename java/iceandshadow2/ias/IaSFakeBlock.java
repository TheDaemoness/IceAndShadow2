package iceandshadow2.ias;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class IaSFakeBlock extends Block {
	
	public IaSFakeBlock(EnumIaSModule mod, String texName) {
		super(Material.dragonEgg);
		this.setBlockName("fake"+mod.prefix+texName);
		this.setBlockTextureName(IceAndShadow2.MODID+':'+mod.prefix+texName);
		IaSRegistration.register(this);
	}
	
	public Item getItem() {
		return Item.getItemFromBlock(this);
	}
}