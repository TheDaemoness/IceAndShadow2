package iceandshadow2.ias.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;

public class IaSItemGuidebook extends IaSBaseItemSingle {

	public IaSItemGuidebook(String texName) {
		super(EnumIaSModule.IAS, texName);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer ep,
			World w, int x, int y, int z,
			int side, float subX, float subY,
			float subZ) {
		if(!w.isRemote)
			return false;
		final ItemStack wb = new ItemStack(Items.written_book);
		//TODO: Get text.
        Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(ep, wb, false));
		return false;
	}


}
