package iceandshadow2.api;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.ias.ai.IIaSMobGetters;

/**
 * Ice and Shadow 2's own tool material for rapid, modular, and flexible tool
 * creation! Yaaaay!
 */
public abstract class IaSToolMaterial implements IIaSApiSacrificeXp, IIaSAspect {

	public static IaSToolMaterial extractMaterial(ItemStack is) {
		if (is == null)
			return IaSRegistry.getDefaultMaterial();
		if (is.getTagCompound() == null)
			return IaSRegistry.getDefaultMaterial();
		if (!is.getTagCompound().hasKey("iasMaterial"))
			return IaSRegistry.getDefaultMaterial();
		final IaSToolMaterial m = IaSRegistry.getToolMaterial(is.getTagCompound().getString("iasMaterial"));
		if (m == null) {
			IaSRegistry.getDefaultMaterial();
		}
		return m;
	}

	@SideOnly(Side.CLIENT)
	protected IIcon iconTool[], iconWeapon[], iconTBroken[], iconWBroken[];

	/**
	 * Called by the default implementation of onAttack to determine how much
	 * damage a tool takes from attacking.
	 *
	 * @param is
	 * @param user
	 * @param target
	 * @return
	 */
	public int damageToolOnAttack(ItemStack is, EntityLivingBase user, Entity target) {
		return 1;
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.getAspect(getMaterialItem());
	}

	/**
	 * Gets the item's base attack damage. This is called by the default
	 * implementations of getToolDamage() and getKnifeDamage(). Players are not
	 * forced to override it if it won't be used. Cheat sheet: 0 = wood, 1 =
	 * stone, 2 = iron, 3 = diamond/echir
	 *
	 * @return
	 */
	public float getBaseDamage() {
		return 3.0F;
	}

	/**
	 * Gets the tool's base harvest level. Used by the default implementation of
	 * getHarvestLevel(). Cheat sheet: -1 = fists, 0 = wood, 1 = stone, 2 =
	 * iron, 3 = diamond
	 */
	public int getBaseLevel() {
		return 0;
	}

	/**
	 * Gets the item's base mining speed. Used in default implementations of
	 * getHarvestSpeed.
	 *
	 * @return The number of times faster than hand this tool is.
	 */
	public abstract float getBaseSpeed();

	/**
	 * Called when a tool breaks to determine whether or not a "broken" tool
	 * item should be given.
	 *
	 * @param is
	 *            The tool being used to harvest.
	 * @param user
	 *            The user of the tool.
	 * @return True if a broken tool item should be returned, false otherwise.
	 */
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return true;
	}

	/**
	 * Get the tool's maximum durability.
	 *
	 * @param is
	 *            The tool.
	 * @return The tool's durability. Each point of durability is one use.
	 */
	public abstract int getDurability(ItemStack is);

	/**
	 * Called to determine harvest effectiveness. In the future, will be used to
	 * add fortune-like effects to harvesting.
	 *
	 * @param bl
	 * @param is
	 * @return -1 if this tool cannot harvest a given block, 0 if the tool
	 *         should behave as normal, and anything greater than 0 if the tool
	 *         can harvest the specified block.
	 */
	public int getHarvestEffectiveness(Block bl, ItemStack is) {
		return 0;
	}

	/**
	 * Gets the tool's harvest level. This determines what materials it can
	 * mine. Cheat sheet: -1 = fists, 0 = wood, 1 = stone, 2 = iron, 3 = diamond
	 * Default implementation mimics vanilla behavior.
	 *
	 * @param is
	 *            The stack.
	 * @param toolClass
	 *            The user.
	 * @return The tool's mining level.
	 */
	public int getHarvestLevel(ItemStack is, String toolClass) {
		if (is.getItem().getToolClasses(is).contains(toolClass))
			return getBaseLevel();
		return -1;
	}

	/**
	 * Gets the item's mine speed against a target block. Default implementation
	 * mimics vanilla tool behavior.
	 *
	 * @param is
	 *            The tool.
	 * @param target
	 *            The innocent block victim. You monster.
	 * @return The mine speed that the tool has against the target block.
	 */
	public float getHarvestSpeed(ItemStack is, Block target) {
		if (is.getItem().canHarvestBlock(target, is))
			return getBaseSpeed();
		return 1.0F;
	}

	/**
	 * Get an icon for an item stack.
	 */
	public IIcon getIcon(ItemStack is) {
		final EnumIaSToolClass t = ((IIaSTool) is.getItem()).getIaSToolClass();
		if (t.isWeapon())
			return iconWeapon[t.getClassId()];
		else
			return iconTool[t.getClassId()];
	}

	/**
	 * Called to determine the cooldown time of the throwing knife.
	 *
	 * @param par1ItemStack
	 * @param par2World
	 * @param entityNyxSkeleton
	 * @return
	 */
	public int getKnifeCooldown(ItemStack par1ItemStack, World par2World, EntityLivingBase elb) {
		return 12;
	}

	/**
	 * Gets a thrown throwing knife's damage bonus against certain entities.
	 * This is NOT called by left-click attacks.
	 *
	 * @param knife
	 *            The in flight knife.
	 * @param user
	 *            The user.
	 * @param target
	 *            The target.
	 * @return The damage that the knife should do. Note that this is the full
	 *         physical damage, not bonus damage, so returning 0.0F will cause
	 *         the tool to do no damage.
	 */
	public float getKnifeDamage(IaSEntityKnifeBase knife, EntityLivingBase user, Entity target) {
		return getBaseDamage() + 2;
	}

	/**
	 * Called to get the damage source for a throwing knife. Useful for doing
	 * things like making a throwing knife's damage pierce armor.
	 *
	 * @param knife
	 *            The throwing knife entity
	 * @param thrower
	 *            The thrower, or null if there was no throwing entity.
	 * @return A damage source.
	 */
	public DamageSource getKnifeDamageSource(IaSEntityKnifeBase knife, Entity thrower) {
		if (thrower == null)
			return DamageSource.causeThrownDamage(knife, knife);
		else
			return DamageSource.causeThrownDamage(knife, thrower);
	}

	/**
	 * Called when a throwing knife collides with an entity or block and
	 * onKnifeHit returns true. Used to determine the item stack that the knife
	 * should drop.
	 *
	 * @param user
	 *            The thrower.
	 * @param knife
	 *            The throwing knife entity.
	 * @return The item stack to drop when the knife hits something, or null if
	 *         no drop.
	 */
	public ItemStack getKnifeDrop(EntityLivingBase user, IaSEntityKnifeBase knife) {
		return knife.getItemStack();
	}

	public String getKnifeMissSound() {
		return "random.anvil_land";
	}

	/**
	 * Gets the base texture for the throwing knife entity.
	 *
	 * @param knife
	 *            The throwing knife entity.
	 * @return The resource location for a texture to render the throwing knife
	 *         with.
	 */
	public abstract ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife);

	/**
	 * Gets an item associated with this material (ex Echir Ingot with Echir,
	 * Cortra with Cortra).
	 */
	protected abstract Item getMaterialItem();

	/**
	 * Gets the material name associated with a tool, for the purposes of
	 * unlocalized names and texture names. This should be capitalized, but not
	 * allcaps. The tool name will end up something like
	 * item.iasToolMaterialClass.name
	 */
	public abstract String getMaterialName();

	/**
	 * Called to get tool material rarity.
	 */
	public EnumRarity getRarity() {
		return EnumRarity.common;
	}

	/**
	 * Gets the prefix associated with the textures for the tool. This would be
	 * something like "IceAndShadow2:iasTool". The tool class and tool material
	 * name would then be tacked on to that name. The final string texture ends
	 * up looking something like "IceAndShadow2:iasToolEchirPickaxe".
	 */
	public String getTextureNamePrefix() {
		return "IceAndShadow2:tools/iasTool";
	}

	/**
	 * Gets the tool's damage bonus against certain entities. This is NOT called
	 * by in-flight throwing knives, but is called on throwing knife left-click.
	 * Default implementation emulates vanilla damage variances between tools.
	 *
	 * @param is
	 *            The tool.
	 * @param user
	 *            The user.
	 * @param target
	 *            The target.
	 * @return The damage that the tool should do. Note that this is pure
	 *         damage, not bonus damage, so returning 0.0F will cause the tool
	 *         to do no damage.
	 */
	public float getToolDamage(ItemStack is, EntityLivingBase user, Entity target) {
		final EnumIaSToolClass t = ((IIaSTool) is.getItem()).getIaSToolClass();
		float baseDmg = getBaseDamage();
		if (user instanceof IIaSMobGetters) {
			baseDmg += ((IIaSMobGetters)user).getAttackStrength(target);
		}
		if (target instanceof EntityLivingBase) {
			baseDmg += EnchantmentHelper.getEnchantmentModifierLiving(user, (EntityLivingBase) target);
		}
		if (t == EnumIaSToolClass.AXE)
			return baseDmg + 3;
		if (t == EnumIaSToolClass.PICKAXE)
			return baseDmg + 2;
		if (t == EnumIaSToolClass.SPADE)
			return baseDmg + 1;
		if (t == EnumIaSToolClass.KNIFE)
			return baseDmg + 2;
		if (t == EnumIaSToolClass.SWORD)
			return baseDmg + 4;
		return getBaseDamage();
	}

	/**
	 * Gets the item required in the transmutation altar to transmute a
	 * tool/weapon to this. Used for cases where the transmutation itself is
	 * quite basic.
	 *
	 * @return The item, or null if transmutation to this material should be
	 *         disabled. A separate handler can still be put in place for
	 *         transmutation under more special circumstances;
	 */
	public ItemStack getTransmutationCatalyst() {
		return new ItemStack(getMaterialItem());
	}

	public String getUnlocalizedName(ItemStack is) {
		final EnumIaSToolClass t = ((IIaSTool) is.getItem()).getIaSToolClass();
		return "item.iasTool" + getMaterialName() + t.toString();
	}

	@Override
	public float getXpValue(ItemStack is, Random rand) {
		return 0;
	}

	/**
	 * Indicates whether or not this tool glows.
	 */
	public boolean glows(EnumIaSToolClass mat) {
		return false;
	}

	/**
	 * Gets whether or not a tool can be repaired in an anvil.
	 */
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return false;
	}

	/**
	 * Called whenever the tool does is swung at an entity. This is NOT called
	 * when a thrown knife hits its target.
	 *
	 * @param is
	 *            The item stack the player used to hit.
	 * @param user
	 *            The player using the tool.
	 * @param target
	 *            The entity damaged by the tool.
	 * @return The number of points of durability that should be deducted by
	 *         this left-click. This is ignored by throwing knives.
	 */
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if (target instanceof EntityLivingBase)
			if (user instanceof EntityPlayer) {
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) user),
						getToolDamage(is, user, target));
			} else {
				target.attackEntityFrom(DamageSource.causeMobDamage(user), getToolDamage(is, user, target));
			}
		return damageToolOnAttack(is, user, target);
	}

	/**
	 * Called when a throwing knife collides with a block.
	 *
	 * @param user
	 *            The thrower.
	 * @param knife
	 *            The throwing knife entity.
	 * @param block
	 *            The block hit.
	 * @return If the knife should drop as an item or not.
	 */
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, ChunkCoordinates block) {
		if (user instanceof EntityMob)
			return false;
		if (user instanceof EntityPlayer)
			return !((EntityPlayer) user).capabilities.isCreativeMode;
		return true;
	}

	/**
	 * Called when a throwing knife collides with an entity.
	 *
	 * @param user
	 *            The thrower.
	 * @param knife
	 *            The throwing knife entity.
	 * @param target
	 *            The entity hit.
	 * @return If the knife should drop as an item or not.
	 */
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife, Entity target) {
		if (user instanceof EntityMob)
			return false;
		if (user instanceof EntityPlayer)
			return !((EntityPlayer) user).capabilities.isCreativeMode;
		return true;
	}

	/**
	 * Called when a throwing knife is thrown, just before the entity is spawned
	 * into the world.
	 *
	 * @param is
	 *            The item stack the player was going to throw.
	 * @param user
	 *            The entity using the throwing knives.
	 * @param knife
	 *            The knife entity that will be spawned.
	 */
	public void onKnifeThrow(ItemStack is, EntityLivingBase user, IaSEntityKnifeBase knife) {
		return;
	}

	/**
	 * Called when a player uses the left click of a tool.
	 *
	 * @param is
	 *            The item stack being used.
	 * @param user
	 *            The user of the item stack.
	 * @return The number of points of durability that should be deducted by
	 *         this left-click. This is ignored by throwing knives.
	 */
	public int onLeftClick(ItemStack is, EntityPlayer user) {
		return 0;
	}

	/**
	 * Called when a tool successfully harvests a block.
	 *
	 * @param is
	 *            The tool being used to harvest.
	 * @param user
	 *            The user of the tool.
	 * @param w
	 *            The world object for the block being harvested.
	 * @param block
	 * @return The number of points of durability that should be deducted by
	 *         this harvest.
	 */
	public int onPostHarvest(ItemStack is, EntityLivingBase user, World w, int x, int y, int z, Block block) {
		return 1;
	}

	/**
	 * Called before a tool harvests a block.
	 *
	 * @param is
	 * @param user
	 * @param block
	 * @return True if the tool should harvest, false otherwise.
	 */
	public boolean onPreHarvest(ItemStack is, EntityPlayer user, World worldObj, int x, int y, int z, Block block) {
		return true;
	}

	/**
	 * Called when a player uses the right click of a tool. This does NOT get
	 * called when using throwing knives. Use onThrowingKnifeThrow() instead.
	 *
	 * @param is
	 *            The item stack being used.
	 * @param user
	 *            The user of the item stack.
	 * @return The number of points of durability that should be deducted by
	 *         this left-click.
	 */
	public int onRightClick(ItemStack is, EntityPlayer user) {
		return 0;
	}

	/**
	 * Called when a tool is swung.
	 *
	 * @param is
	 *            The tool being swing
	 * @param user
	 *            The user of the tool.
	 * @return True if further processing should be canceled.
	 */
	public boolean onSwing(ItemStack is, EntityLivingBase user) {
		return false;
	}

	/**
	 * Called to register icons for a certain set of tools.
	 */
	public void registerIcons(IIconRegister reg) {
		int lTool = 0, lWeapon = 0;
		for (final EnumIaSToolClass cl : EnumIaSToolClass.values())
			if (cl.isWeapon() && cl.getClassId() >= lWeapon) {
				lWeapon = cl.getClassId() + 1;
			} else if (!cl.isWeapon() && cl.getClassId() >= lTool) {
				lTool = cl.getClassId() + 1;
			}
		iconTool = new IIcon[lTool];
		iconWeapon = new IIcon[lWeapon];
		for (int i = 0; i < iconTool.length; ++i) {
			iconTool[i] = reg.registerIcon(
					getTextureNamePrefix() + getMaterialName() + EnumIaSToolClass.fromId(i, false).toString());
		}
		for (int i = 0; i < iconWeapon.length; ++i) {
			iconWeapon[i] = reg.registerIcon(
					getTextureNamePrefix() + getMaterialName() + EnumIaSToolClass.fromId(i, true).toString());
		}

	}
}
