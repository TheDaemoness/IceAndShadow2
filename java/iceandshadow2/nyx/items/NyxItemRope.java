package iceandshadow2.nyx.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;

public class NyxItemRope extends IaSBaseItemSingle {

	public NyxItemRope(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer pl,
			World w, int x, int y, int z,
			int meta, float lx, float ly,
			float lz) {
		//TODO: Implement and stuff.
		return false;
	}
	
	

}
