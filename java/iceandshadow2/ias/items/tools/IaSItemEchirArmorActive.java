package iceandshadow2.ias.items.tools;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.util.EnumIaSModule;

public class IaSItemEchirArmorActive extends IaSBaseItemSingleGlow {
 
	protected int slot;
	
	public IaSItemEchirArmorActive(String texName, int slut) { //Wat?
		super(EnumIaSModule.IAS, texName);
		slot = slut; //Oh...
		this.setMaxStackSize(1);
	}

	@Override
	public int getMaxDamage() {
		return IaSTools.armorEchir[slot].getMaxDamage();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer ep) {
		par1 = new ItemStack(IaSTools.armorEchir[slot],1,par1.getItemDamage());
		return par1;
	}
}
