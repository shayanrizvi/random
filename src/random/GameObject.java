package random;

import java.awt.*;

/**
 * 
 * This class provides common methods among objects in the game.
 * 
 * @author Shayan
 *
 */
public abstract class GameObject implements Comparable<GameObject> {
	
	// Create variables
	protected int x;																			 // x
	protected int y;                                                                             // y
	protected int s;                                                                             // size
	protected ID id;                                                                             // ID
	protected int velX;                                                                          // horizontal velocity
	protected int velY;                                                                          // vertical velocity
	protected int health;                                                                        // health
	protected boolean hit;                                                                       // hit
	protected Color color;                                                                       // color
	                                                                                             // 
	/**
	 * Constructs a new game object
	 * 
	 * @param x	X coordinate
	 * @param y Y coordinate
	 * @param s	size
	 * @param id	ID tag
	 */
	public GameObject(int x, int y, int s, ID id) {                                              // 
		                                                                                         // 
		this.x = x;                                                                              // set x
		this.y = y;                                                                              // set y
		this.s = s;                                                                              // set size
		this.id = id;                                                                            // set ID
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update object
	 */
	public abstract void tick();                                                                 // 
	                                                                                             // 
	/**
	 * Draw object
	 * @param g	graphics context
	 */
	public abstract void render(Graphics g);                                                     // 
	                                                                                             // 
	@Override																					 // 
	public String toString() {																	 // 
																								 // 
		return id.name();																		 // 
																								 // 
	}																							 // 
                                                                            					 // 
	/**
	 * Returns a representation of the object formatted as a list of strings 
	 * for legend page display
	 * 
	 * @return	a representation of the object formmatted as a list of strings
	 */
	public abstract String[] toInfo();                                                           // 
	                                                                                             // 
	public int compareTo(GameObject object) {                                                    // 
		                                                                                         // 
		return this.id.compareTo(object.getId());                    							 // lexicographically compare object's ID with specified object's ID
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	                                                                                             // 
	                                                                                             // 
	/**
	 * Restricts the given variable to a range of values
	 * 
	 * @param var	the variable to restrict
	 * @param min	the minimum value for the variable
	 * @param max	the maximum value for the variable
	 * @return	the restricted variable value
	 */
	public static int clamp(int var, int min, int max) {                                         // 
                                                                                                 // 
		if(var <= min) return var = min;                                                         // 
		else if(var >= max) return var = max;                                                    // 
		else return var;                                                                         // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Toggles between the minimum and maximum values if the limit of the given 
	 * variable is reached
	 * 
	 * @param var	the variable to toggle
	 * @param min	the minimum value for the variable
	 * @param max	the maximum value for the variable
	 * @return	the toggled variable value
	 */
	public static int flip(int var, int min, int max) {                                          // 
		                                                                                         // 
		if(var <= min) return var = max - 1;                                                     // 
		else if(var >= max) return var = min + 1;                                                // 
		else return var;                                                                         // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Inverts the velocity of the object if the limit of the given variable is reached
	 * 
	 * @param vel	the velocity to invert
	 * @param var	the variable to measure
	 * @param min	the minimum value for the variable
	 * @param max	the maximum value for the variable
	 * @return	the inverted velocity value
	 */
	public static int bounce(int vel, int var, int min, int max) {                               // 
		                                                                                         // 
		if(var <= min) return vel * -1;                                                          // 
		else if(var >= max) return vel * -1;                                                     // 
		else return vel;                                                                         // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Detects if the object bounds intersects with the given object bounds
	 * 
	 * @param object	the object with which to check for collision
	 * @return	true if the objects intersects and false otherwise
	 */
	public boolean collides(GameObject object) {                                                 // 
		                                                                                         // 
		if(this.getBounds().intersects(object.getBounds())) return true;                         // 
		else return false;                                                                       // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Fires a missle object from the object towards the specified target coordinates
	 * 
	 * @param s	missle size
	 * @param targetX	target X coordinate
	 * @param targetY	target Y coordinate
	 * @param damage	missle damage
	 * @param velocity	missle velocity
	 * @return	the fired missle
	 */
	public Missle fireMissle(int s, int targetX, int targetY, int damage, int velocity) {        // 
		                                                                                         // 
		return new Missle(this, s, targetX, targetY, damage, 100/velocity);                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Fires a seeking missle object from the object towards the specified target
	 * 
	 * @param s	missle size
	 * @param target	the target object
	 * @param damage	missle damage
	 * @return	the fired missle
	 */
	public SeekMissle fireSeekMissle(int s, GameObject target, int damage) {    			     // 
		                                                                                         // 
		return new SeekMissle(this, s, target, damage);                         			     // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Fires a seeking missle object from the object towards the specified target coordinates
	 * 
	 * @param s	missle size
	 * @param targetX	target X coordinate
	 * @param targetY	target Y coordinate
	 * @param damage	missle damage
	 * @param velocity	missle velocity
	 * @return	the fired missle
	 */
	public SeekMissle fireSeekMissle(int s, int targetX, int targetY, int damage, int velocity) {// 
		                                                                                         // 
		return new SeekMissle(this, s, targetX, targetY, damage, 100/velocity);                  // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Drops a bomb at the object location
	 * 
	 * @param s	bomb size
	 * @param health	bomb health
	 * @param damage	bomb damage
	 * @return	the dropped bomb
	 */
	public Bomb dropBomb(int s, int health, int damage) {                                        // 
		                                                                                         // 
		return new Bomb(this, s, health, damage);                                                // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Checks if the object has health
	 * 
	 * @return	true if the object's health is greater than 0 and false otherwise
	 */
	public boolean isAlive() {                                                                   // 
		                                                                                         // 
		return health > 0 ? true: false;                                                         // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Checks if the object has no health
	 * 
	 * @return	true if the object's health is less than 1 and false otherwise
	 */
	public boolean isDead() {                                                                    // 
		                                                                                         // 
		return health > 0 ? false: true;                                                         // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Sets the object's hit variable to true and changes the color of the object to red, 
	 * indicating damage taken
	 * 
	 * @return hit
	 */
	public boolean hit() {                                                                       // 
		                                                                                         // 
		color = Color.RED;                                                                       // change color to red
		return hit = true;                                                                       // 
		                                                                                         // 
	}                                                                                            // 
																								 // 
	/**
	 * Setts the object's hit variable to true and changes the color of the object to the 
	 * specified color, indicating item collection
	 * 
	 * @param color	the color to change the object's color to
	 * @return	hit
	 */
	public boolean hit(Color color) {                                                            // 
		                                                                                         // 
		this.color = color;                                                                      // change color to specified color
		return hit = true;                                                                       // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setX(int x) {                                                                    // 
		                                                                                         // 
		this.x = x;                                                                              // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getX() {                                                                          // 
		                                                                                         // 
		return x;                                                                                // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setY(int y) {                                                                    // 
		                                                                                         // 
		this.y = y;                                                                              // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getY() {                                                                          // 
		                                                                                         // 
		return y;                                                                                // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setS(int s) {                                                                    // 
		                                                                                         // 
		this.s = s;                                                                              // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getS() {                                                                          // 
		                                                                                         // 
		return s;                                                                                // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setId(ID id) {                                                                   // 
		                                                                                         // 
		this.id = id;                                                                            // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public ID getId() {                                                                          // 
		                                                                                         // 
		return id;                                                                               // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setVelX(int velX) {                                                              // 
		                                                                                         // 
		this.velX = velX;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getVelX() {                                                                       // 
		                                                                                         // 
		return velX;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setVelY(int velY) {                                                              // 
		                                                                                         // 
		this.velY = velY;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getVelY() {                                                                       // 
		                                                                                         // 
		return velY;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setHealth(int health) {                                                          // 
																								 // 
		this.health = health;                                                                    // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getHealth() {                                                                     // 
		                                                                                         // 
		return health;                                                                           // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setColor(Color color) {                                                          // 
		                                                                                         // 
		this.color = color;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public Color getColor() {                                                                    // 
		                                                                                         // 
		return color;                                                                            // 
		                                                                                         // 
	}                                                                                            // 
	public Rectangle getBounds() {                                                               // 
		                                                                                         // 
		return new Rectangle(x, y, s, s);                                                        // 
		                                                                                         // 
	}                                                                                            // 
																								 // 
}																								 // 

