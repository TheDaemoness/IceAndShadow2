package iceandshadow2.ias.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.util.EnumIaSModule;

public class IaSItemEchirToolActive extends IaSBaseItemSingleGlow {

	protected int slot;
	
	public IaSItemEchirToolActive(String texName, int tab) {
		super(EnumIaSModule.IAS, texName);
		slot = tab; //FtM sex change, wot?
		this.setMaxStackSize(1);
		this.setFull3D();
	}

	@Override
	public int getMaxDamage() {
		return IaSRegistry.getDefaultMaterial().getDurability(new ItemStack(IaSTools.tools[slot]));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer ninja) {
		if(ninja.isSneaking()) //Do NOT remove this if statement, it actually isn't redundant.
			par1 = new ItemStack(IaSTools.tools[slot],1,par1.getItemDamage());
		return par1;
	}

}
