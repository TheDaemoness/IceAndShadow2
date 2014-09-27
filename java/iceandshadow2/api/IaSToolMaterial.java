package iceandshadow2.api;

import iceandshadow2.ias.items.tools.*;
import iceandshadow2.util.IaSEntityHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;


/**Ice and Shadow 2's own tool material for rapid, modular, and flexible tool creation! Yaaaay!
 */
public abstract class IaSToolMaterial implements IIaSXpAltarSacrifice {
	
	@SideOnly(Side.CLIENT)
	protected IIcon iconTool[], iconBroken[];
	
	/**
	 * Called when a tool breaks to determine whether or not a "broken" tool item should be given.
	 * @param is The tool being used to harvest.
	 * @param user The user of the tool.
	 * @return True if a broken tool item should be returned, false otherwise.
	 */
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return true;
	}
	
	public static IaSToolMaterial extractMaterial(ItemStack is) {
		if(is == null)
			return IaSRegistry.getDefaultMaterial();
		if (is.getTagCompound() == null)
			return IaSRegistry.getDefaultMaterial();
		if(!is.getTagCompound().hasKey("iasMaterial"))
			return IaSRegistry.getDefaultMaterial();
		IaSToolMaterial m  = IaSRegistry.getToolMaterial(is.getTagCompound().getString("iasMaterial"));
		if(m == null)
			IaSRegistry.getDefaultMaterial();
		return m;
	}
	
	/**
	 * Gets the prefix associated with the textures for the tool.
	 * This would be something like "IceAndShadow2:iasTool".
	 * The tool class and tool material name would then be tacked on to that name.
	 * The final string texture ends up looking something like "IceAndShadow2:iasToolEchirPickaxe".
	 */
	public String getTextureNamePrefix() {
		return "IceAndShadow2:tools/iasTool";
	}
	
	/**
	 * Gets the material name associated with a tool, for the purposes of unlocalized names and texture names.
	 * This should be capitalized, but not allcaps.
	 * The tool name will end up something like item.iasToolMaterialClass.name
	 */
	public abstract String getMaterialName();
	
	/**
	 * Gets the item's mine speed against a target block.
	 * Default implementation mimics vanilla tool behavior.
	 * @param is The tool.
	 * @param target The innocent block victim. You monster.
	 * @return The mine speed that the tool has against the target block.
	 */
	public float getHarvestSpeed(ItemStack is, Block target) {
		if(is.getItem().canHarvestBlock(target, is))
			return this.getBaseSpeed();
		return 1.0F;
	}
	
	/**
	 * Gets the item's base mining speed. Used in default implementations of getHarvestSpeed.
	 * @return The number of times faster than hand this tool is.
	 */
	public abstract float getBaseSpeed();

	/**
	 * Gets the item's base attack damage.
	 * This is called by the default implementations of getToolDamage() and getKnifeDamage().
	 * Players are not forced to override it if it won't be used. 
	 * @return 
	 */
	public float getBaseDamage() {
		return 3.0F;
	}
	
	/**
	 * Gets the tool's damage bonus against certain entities.
	 * This is NOT called by in-flight throwing knives, but is called on throwing knife left-click.
	 * Default implementation emulates vanilla damage variances between tools.
	 * @param is The tool.
	 * @param user The user.
	 * @param target The target.
	 * @return The damage that the tool should do. Note that this is pure damage, not bonus damage, so returning 0.0F will cause the tool to do no damage.
	 */
	public float getToolDamage(ItemStack is, EntityLivingBase user, Entity target) {
		EnumIaSToolClass t = ((IIaSTool)is.getItem()).getIaSToolClass();
		if(t == EnumIaSToolClass.AXE)
			return getBaseDamage()+3;
		if(t == EnumIaSToolClass.PICKAXE)
			return getBaseDamage()+2;
		if(t == EnumIaSToolClass.SPADE)
			return getBaseDamage()+1;
		if(t == EnumIaSToolClass.KNIFE)
			return getBaseDamage()+2;
		if(t == EnumIaSToolClass.SWORD)
			return getBaseDamage()+4;
		return getBaseDamage();
	}
	
	/**
	 * Gets a thrown throwing knife's damage bonus against certain entities.
	 * This is NOT called by left-click attacks.
	 * @param knife The in flight knife.
	 * @param user The user.
	 * @param target The target.
	 * @return The damage that the knife should do. Note that this is pure damage, not bonus damage, so returning 0.0F will cause the tool to do no damage.
	 */
	public float getKnifeDamage(IIaSThrowingKnife knife, EntityLivingBase user, Entity target) {
		return getBaseDamage()+2;
	}
	
	/**
	 * Gets the tool's harvest level. This determines what materials it can mine.
	 * Cheat sheet: -1 = fists, 0 = wood, 1 = stone, 2 = iron, 3 = diamond
	 * Default implementation mimics vanilla behavior.
	 * @param is The stack.
	 * @param toolClass The user.
	 * @return The tool's mining level.
	 */
	public int getHarvestLevel(ItemStack is, String toolClass) {
		if(is.getItem().getToolClasses(is).contains(toolClass))
			return this.getBaseLevel();
		return -1;
	}
	
	/**
	 * Gets the tool's base harvest level. Used by the default implementation of getHarvestLevel().
	 * Cheat sheet: -1 = fists, 0 = wood, 1 = stone, 2 = iron, 3 = diamond
	 */
	public int getBaseLevel() {
		return 0;
	}

	/**
	 * Get the tool's maximum durability.
	 * @param is The tool.
	 * @return The tool's durability. Each point of durability is one use.
	 */
	public abstract int getDurability(ItemStack is);
	
	/**
	 * Called when a tool is swung.
	 * @param is The tool being swing
	 * @param user The user of the tool.
	 * @return True if further processing should be canceled.
	 */
	public boolean onSwing(ItemStack is, EntityLivingBase user) {
		return false;
	}
	
	/**
	 * Called when a tool successfully harvests a block.
	 * @param is The tool being used to harvest.
	 * @param user The user of the tool.
	 * @param w The world object for the block being harvested.
	 * @return The number of points of durability that should be deducted by this harvest.
	 */
	public int onHarvest(ItemStack is, EntityLivingBase user, World w, int x, int y, int z) {
		return 1;
	}
	
	/**
	 * Gets whether or not a tool can be repaired in an anvil.
	 */
	public boolean isRepairable(ItemStack tool, ItemStack mat) {
		return false;
	}
	
	/**
	 * Called when a player uses the left click of a tool.
	 * @param is The item stack being used.
	 * @param user The user of the item stack.
	 * @return The number of points of durability that should be deducted by this left-click. This is ignored by throwing knives.
	 */
	public int onLeftClick(ItemStack is, EntityPlayer user) {
		return 0;
	}
	
	/**
	 * Called when a player uses the right click of a tool.
	 * This does NOT get called when using throwing knives. Use onThrowingKnifeThrow() instead.
	 * @param is The item stack being used.
	 * @param user The user of the item stack.
	 * @return The number of points of durability that should be deducted by this left-click.
	 */
	public int onRightClick(ItemStack is, EntityPlayer user) {
		return 0;
	}
	
	/**
	 * Called when an intelligent Nyxian enemy is going to pick up this tool (namely, Winter Skeletons).
	 * Note that onRightClick() will NOT get called while the tool is being used by a intelligent Nyxian enemy.
	 * All other combat-related callbacks work fine, however.
	 * @param is The item stack the entity is going to pick up.
	 * @param user The entity that is going to pick up this tool.
	 * @return True if the Nyxian enemy should pick up and use this tool, false otherwise.
	 */
	public boolean shouldEnemiesUse(ItemStack is, EntityLivingBase user) {
		return false;
	}
	
	/**
	 * Called whenever the tool does is swung at an entity. This is NOT called when a thrown knife hits its target.
	 * @param is The item stack the player used to hit.
	 * @param user The player using the tool.
	 * @param target The entity damaged by the tool.
	 * @return The number of points of durability that should be deducted by this left-click. This is ignored by throwing knives.
	 */
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		if(target instanceof EntityLivingBase) {
			if(user instanceof EntityPlayer)
				target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)user), getToolDamage(is, user, target));
			else
				target.attackEntityFrom(DamageSource.causeMobDamage(user), getToolDamage(is, user, target));
		}
		return damageToolOnAttack(is,user,target);
	}
	
	/**
	 * Called by the default implementation of onAttack to determine how much damage a tool takes from attacking.
	 * @param is
	 * @param user
	 * @param target
	 * @return
	 */
	public int damageToolOnAttack(ItemStack is, EntityLivingBase user, Entity target) {
		return 1;
	}
	
	/**
	 * Called when a throwing knife is thrown, just before the entity is spawned into the world.
	 * @param is The item stack the player was going to throw.
	 * @param user The entity using the throwing knives.
	 */
	public void onThrowingKnifeThrow(ItemStack is, EntityLivingBase user, IIaSThrowingKnife knife) {
		return;
	}
	
	
	/**
	 * Called when a throwing knife collides with an entity or block.
	 * Note that returning false with this may result in this function being called a number of times if knife passes through its target.
	 * @param user The thrower.
	 * @param knife The throwing knife entity.
	 * @return True if the throwing knife should drop as an item stack (or despawn), false if the knife should continue living.
	 */
	public boolean onThrowingKnifeHit(EntityLivingBase user, IIaSThrowingKnife knife, MovingObjectPosition target) {
		return true;
	}
	
	/**
	 * Called when a throwing knife collides with an entity or block and onThrowingKnifeHit returns true.
	 * Used to determine the item stack that the knife should drop.
	 * @param user The thrower.
	 * @param knife The throwing knife entity.
	 * @return The item stack to drop when the knife hits something (if onThrowingKnifeHit is true), or null if no drop.
	 */
	public ItemStack getThrowingKnifeDrop(EntityLivingBase user, IIaSThrowingKnife knife, MovingObjectPosition target) {
		return getDefaultThrowingKnifeDrop(knife);
	}
	
	/**
	 * Helper function to get the default item stack drop of a throwing knife.
	 * @param knife The throwing knife entity.
	 */
	protected static final ItemStack getDefaultThrowingKnifeDrop(IIaSThrowingKnife knife) {
		return null;
	}

	public String getUnlocalizedName(ItemStack is) {
		EnumIaSToolClass t = ((IIaSTool)is.getItem()).getIaSToolClass();
		return "item.iasTool"+this.getMaterialName()+t.toString();
	}

	/**
	 * Get an icon for an item stack.
	 */
	public IIcon getIcon(ItemStack is) {
		EnumIaSToolClass t = ((IIaSTool)is.getItem()).getIaSToolClass();
		if(is.getItem() instanceof IIaSTool)
			return iconTool[t.getClassId()];
		return iconBroken[t.getClassId()];
	}

	/**
	 * Called to register icons for a certain set of tools.
	 */
	public void registerIcons(IIconRegister reg) {
		iconTool = new IIcon[EnumIaSToolClass.values().length];
		for(int i = 0; i < iconTool.length; ++i)
			iconTool[i] = reg.registerIcon(this.getTextureNamePrefix()+this.getMaterialName()+EnumIaSToolClass.fromId(i).toString());
		iconBroken = new IIcon[EnumIaSToolClass.values().length];
		for(int i = 0; i < iconTool.length; ++i)
			iconBroken[i] = reg.registerIcon(this.getTextureNamePrefix()+"Broken"+this.getMaterialName()+EnumIaSToolClass.fromId(i).toString());
	}
}
