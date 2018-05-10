package random;

import java.awt.*;

/**
 * 
 * This is a convenience class for operations combining multiple weapon types.
 * 
 * @author Shayan
 *
 */
public abstract class Weapon extends GameObject {
	
	protected int damage;                                                                            // damage
	                                                                                                 // 
	/**
	 * Constructs a weapon object without a specified location to display on legend page
	 * 
	 * @param s	size
	 * @param id	ID tag
	 */
	public Weapon(int s, ID id) {                                                                    // 
		                                                                                             // 
		super(0,0,s,id);                                                                             // 
		                                                                                             // 
	}                                                                                                // 
	                                                                                                 // 
	/**
	 * Constructs a weapon object at origin object location
	 * 
	 * @param origin	game object which invoked the weapon constructor call
	 * @param s	size
	 * @param health	weapon health
	 * @param damage	weapon damage
	 * @param id	ID tag
	 */
	public Weapon(GameObject origin, int s, int health, int damage, ID id) {                         // 
		                                                                                             // 
		super(origin.getX() + origin.getS()/2 - s/2, origin.getY() + origin.getS()/2 - s/2, s, id);  // pass coordinates, size, and ID to superclass constructor
		this.color = origin.getColor();                                                              // initialize color to origin object color
		this.health = health;                                                                        // initialize health to specified value
		this.damage = damage;                                                                        // initialize damage to specified value
		                                                                                             // 
	}                                                                                                // 
	                                                                                                 // 
	/**
	 * Constructs a weapon object at origin object location
	 * 
	 * @param origin	game object which invoked the weapon constructor call
	 * @param s	size
	 * @param color	weapon color
	 * @param health	weapon health
	 * @param damage	weapon damage
	 * @param id	ID tag
	 */
	public Weapon(GameObject origin, int s, Color color, int health, int damage, ID id) {            // 
		                                                                                             // 
		super(origin.getX() + origin.getS()/2 - s/2, origin.getY() + origin.getS()/2 - s/2, s, id);  // pass coordinates, size, and ID to superclass constructor
		this.color = color;                                                                          // initialize color to specified color
		this.health = health;                                                                        // initialize health to specified value
		this.damage = damage;                                                                        // initialize damage to specified value
		                                                                                             // 
	}                                                                                                // 
	                                                                                                 // 
	public int getDamage() {                                                                         // 
		                                                                                             // 
		return damage;                                                                               // 
		                                                                                             // 
	}                                                                                                // 
	                                                                                                 // 
	public void setDamage(int damage) {                                                              // 
		                                                                                             // 
		this.damage = damage;                                                                        // 
		                                                                                             // 
	}                                                                                                // 
	                                                                                                 // 
}                                                                                                    // 
