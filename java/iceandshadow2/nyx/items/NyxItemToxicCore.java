package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSOnDeathDrop;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import iceandshadow2.nyx.entities.util.EntityWightTeleport;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemToxicCore extends IaSBaseItemMultiGlow implements IIaSOnDeathDrop {

	@SideOnly(Side.CLIENT)
	protected IIcon smallIcon;
	
	public NyxItemToxicCore(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		this.setMaxStackSize(16);
		this.setFull3D();
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1),
				new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return smallIcon;
		return this.itemIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getTexName());
		this.smallIcon = reg.registerIcon(this.getTexName() + "Small");
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
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityWightTeleport(par2World,
					player));
		if (!player.capabilities.isCreativeMode) {
			par1Stack.stackSize -= 1;
			player.attackEntityFrom(DamageSource.magic,
					Math.max(0, player.worldObj.difficultySetting.getDifficultyId()-1));
		}
		return par1Stack;
	}
}
