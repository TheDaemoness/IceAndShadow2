package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.nyx.entities.projectile.EntityPoisonBall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemToxicCore extends IaSBaseItemMultiGlow {

	@SideOnly(Side.CLIENT)
	protected IIcon smallIcon;

	public NyxItemToxicCore(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		setMaxStackSize(4);
		setFull3D();
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1),
				new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return this.smallIcon;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.smallIcon = reg.registerIcon(getTexName() + "Small");
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1Stack, World par2World,
			EntityPlayer player) {
		if (!par2World.isRemote && par1Stack.getItemDamage() == 1) {
			par2World.spawnEntityInWorld(new EntityPoisonBall(par2World,
					player));
			par1Stack.stackSize -= 1;
		}
		return par1Stack;
	}
}
