package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.nyx.entities.projectile.EntityPoisonBall;
import iceandshadow2.util.IaSPlayerHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemToxicCore extends IaSBaseItemMultiGlow {

	@SideOnly(Side.CLIENT)
	protected IIcon smallIcon;

	public NyxItemToxicCore(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		setMaxStackSize(16);
		setFull3D();
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1), new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return this.smallIcon;
		return this.itemIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1Stack, World par2World, EntityPlayer player) {
		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(new EntityPoisonBall(par2World, player));
			if (par1Stack.getItemDamage() == 0) {
				List<Entity> l = par2World.getEntitiesWithinAABBExcludingEntity(player,
						AxisAlignedBB.getBoundingBox(player.posX - 16, player.posY - 16, player.posZ - 16,
								player.posX + 16, player.posY + 24, player.posZ + 16));
				for (Entity ent : l) {
					if (ent instanceof EntityMob) {
						EntityPoisonBall pb = new EntityPoisonBall(par2World, player);
						pb.setThrowableHeading(ent.posX - player.posX, ent.posY - player.posY
								+ ((EntityMob) ent).getEyeHeight() * 1.25 - player.getEyeHeight(),
								ent.posZ - player.posZ, 1.0F, 1.0F);
						par2World.spawnEntityInWorld(pb);
					}
				}
				IaSPlayerHelper.giveItem(player, new ItemStack(this, 1, 1));
			}
			par1Stack.stackSize -= 1;
		}
		return par1Stack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(getTexName());
		this.smallIcon = reg.registerIcon(getTexName() + "Small");
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
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}
}
