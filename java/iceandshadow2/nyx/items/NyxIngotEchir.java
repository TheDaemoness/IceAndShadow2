package iceandshadow2.nyx.items;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.util.EnumIaSModule;

public class NyxIngotEchir extends IaSBaseItemSingle {

	@SideOnly(Side.CLIENT)
	protected IIcon active;
	
	public NyxIngotEchir(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setHasSubtypes(true);
		GameRegistry.addSmelting(this, new ItemStack(this,1,1), 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if(dmg > 0)
			return active;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		active = reg.registerIcon(this.getTexName()+"Active");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order,
			EntityPlayer pwai) {
		if(pwai.isSneaking())
			heap.setItemDamage(0);
		return heap;
	}
}
