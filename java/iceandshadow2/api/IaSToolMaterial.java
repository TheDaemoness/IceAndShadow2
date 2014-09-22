package iceandshadow2.api;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;


/**Ice and Shadow 2's own tool material for rapid, modular, and flexible tool creation! Yaaaay!
 * Different properties can be set for different tool classes by returning different values based on instanceof checks on the item in the ItemStack.
 * Note that throwing knives inherit from ItemSword. Applicable functions have a boolean parameter to narrow down instanceof tool determination.
 */
public abstract class IaSToolMaterial implements IIaSXpAltarSacrifice {
	
	/**
	 * Gets the item's mine speed against a target block.
	 * Throwing knives encouraged to have the player's harvest speed or half the harvest speed of a sword.
	 * @param is The tool.
	 * @param target The innocent block victim. You monster.
	 * @return The mine speed that the tool has against the target block.
	 */
	public abstract float getMineSpeed(ItemStack is, Block target, boolean isThrowingKnife);
	
	/**
	 * Gets the tool's damage bonus against certain entities.
	 * This is NOT called by in-flight throwing knives, but is called on throwing knife left-click.
	 * @param is The tool.
	 * @param user The user.
	 * @param target The target.
	 * @return The damage that the tool should do. Note that this is pure damage, not bonus damage, so returning 0.0F will cause the tool to do no damage.
	 */
	public abstract float getToolDamageBonus(ItemStack is, EntityPlayer user, Entity target, boolean isThrowingKnife);
	
	/**
	 * Gets a thrown throwing knife's damage bonus against certain entities.
	 * This is NOT called by left-click attacks.
	 * @param knife The in flight knife.
	 * @param user The user.
	 * @param target The target.
	 * @return The damage that the knife should do. Note that this is pure damage, not bonus damage, so returning 0.0F will cause the tool to do no damage.
	 */
	public abstract float getKnifeDamageBonus(IIaSThrowingKnife knife, EntityPlayer user, Entity target);
	
	/**
	 * Gets the tool's mining level. This determines what materials it can mine.
	 * This is IGNORED by throwing knives.
	 * Cheat sheet: 0 = stone, 1 = iron, 2 = redstone/diamond, 3 = obsidian, 4 - ???
	 * @param is The stack.
	 * @param user The user.
	 * @return The tool's mining level.
	 */
	public abstract int getMineLevel(ItemStack is, EntityPlayer user);
	
	/**
	 * Get the tool's maximum durability.
	 * @param is The tool.
	 * @return The tool's durability. Each point of durability is one use.
	 */
	public abstract int getDurability(ItemStack is, boolean isThrowingKnife);
	
	/**
	 * Called when a tool successfully harvests a block.
	 * @param is The tool being used to harvest.
	 * @param user The user of the tool.
	 * @param w The world object for the block being harvested.
	 * @return The number of points of durability that should be deducted by this harvest.
	 */
	public int onHarvest(ItemStack is, EntityPlayer user, World w, int x, int y, int z) {
		return 1;
	}
	
	/**
	 * Called when a player uses the left click of a tool.
	 * @param is The item stack being used.
	 * @param user The user of the item stack.
	 * @return The number of points of durability that should be deducted by this left-click.
	 */
	public int onLeftClick(ItemStack is, EntityPlayer user, boolean isThrowingKnife) {
		return 1;
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
	public boolean shouldEnemiesUse(ItemStack is, EntityLivingBase user, boolean isThrowingKnife) {
		return false;
	}
	
	/**
	 * Called whenever the tool does damage. Is also called when a throwing knife hits a target.
	 * @param is The item stack the player used to hit, or null if called by a throwing knife.
	 * @param user The player using the tool.
	 * @param target The entity damaged by the tool.
	 * @return The number of points of durability that should be deducted by this left-click.
	 */
	public int onAttack(ItemStack is, EntityPlayer user, Entity target, boolean isThrowingKnife) {
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
}
