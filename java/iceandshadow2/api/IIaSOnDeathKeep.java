package iceandshadow2.api;

/**
 * Marks an item or block as one that should be kept upon death.
 * Should be implemented by the item or block. Unnecessary for armor.
 * On death, an item stack containing this item or the ItemBlock will be transferred when the player respawns.
 * It will not drop as an entity in the world.
 * Useful when IIaSApiDeathHook provides too much functionality.
 */
public interface IIaSOnDeathKeep {
}
