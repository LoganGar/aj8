package org.apollo.game.model.inv;

import org.apollo.game.model.Player;

/**
 * A supplier for an {@link Inventory}.
 *
 * <p>
 * This is a functional interface whose functional method is
 * {@link #getInventory(Player)}
 *
 * @author Major
 */
@FunctionalInterface
public interface InventorySupplier {

	/**
	 * Gets the appropriate {@link Inventory}.
	 *
	 * @param player The {@link Player} who prompted the verification call.
	 * @return The inventory. Must not be {@code null}.
	 */
	Inventory getInventory(Player player);

}