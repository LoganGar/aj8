package org.apollo.game.model;

/**
 * Contains equipment-related constants.
 *
 * @author Graham
 */
public final class EquipmentConstants {

	/**
	 * The hat slot.
	 */
	public static final int HAT = 0;

	/**
	 * The cape slot.
	 */
	public static final int CAPE = 1;

	/**
	 * The amulet slot.
	 */
	public static final int AMULET = 2;

	/**
	 * The weapon slot.
	 */
	public static final int WEAPON = 3;

	/**
	 * The chest slot.
	 */
	public static final int CHEST = 4;

	/**
	 * The shield slot.
	 */
	public static final int SHIELD = 5;

	/**
	 * The legs slot.
	 */
	public static final int LEGS = 7;

	/**
	 * The hands slot.
	 */
	public static final int HANDS = 9;

	/**
	 * The feet slot.
	 */
	public static final int FEET = 10;

	/**
	 * The ring slot.
	 */
	public static final int RING = 12;

	/**
	 * The arrows slot.
	 */
	public static final int ARROWS = 13;

	/**
	 * Suppresses the default-public constructor preventing this class from
	 * being instantiated by other classes.
	 *
	 * @throws UnsupportedOperationException If this class is instantiated within itself.
	 */
	private EquipmentConstants() {
		throw new UnsupportedOperationException("constant-container classes may not be instantiated.");
	}

}