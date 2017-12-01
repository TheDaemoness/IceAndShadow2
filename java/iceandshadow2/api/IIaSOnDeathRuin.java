package iceandshadow2.api;

/**
 * Marks an item or block as one that should be destroyed upon death. Should be
 * implemented by the item or block. On death, an item stack containing this
 * item or the ItemBlock of the given block will not drop. However, it will not
 * be transferred over to the player's new inventory either. Useful when
 * IIaSApiDeathHook provides too much functionality.
 */
public interface IIaSOnDeathRuin {
}
