package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines bomb functionality.
 * 
 * @author Shayan
 *
 */
public class Bomb extends Weapon {

	protected final static ID id = ID.Bomb;										 // initialize ID
	private boolean detonated;                                                   // detonated
	private Random r = new Random();                                             // random
                                                                                 // 
	/**
	 * Constructs a bomb object without a specified location to display
	 * on legend page
	 */
	public Bomb() {                                                              // 
		                                                                         // 
		super(32, id);                                                           // pass size and ID to superclass constructor
		this.color = Color.WHITE;                                                // initialize color
																				 // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Constructs a bomb object which stays alive for the specified health
	 * duration after which it automatically detonates
	 * 
	 * @param origin	game object which invoked the bomb constructor call
	 * @param s	bomb size
	 * @param health	bomb health
	 * @param damage	bomb damage
	 */
	public Bomb(GameObject origin, int s, int health, int damage) {              // 
		                                                                         // 
		super(origin, s, health, damage, id);                                    // pass origin, size, health, damage, and ID to superclass constructor
		detonated = false;                                                       // initialize detonated flag
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Update bomb
	 */
	public void tick() {                                                         // 
		                                                                         // 
		health--;                                                                // decrement health
		if(health <= 0) detonate();                                              // detonate if health reaches 0
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Draw bomb
	 */
	public void render(Graphics g) {                                             // 
                                                                                 // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));   // render bomb
		g.fillOval(x - 1, y - 1, s + 2, s + 2);                                  // 
		g.setColor(color);                                                       // 
		g.fillOval(x + s/4, y + s/4, s - s/2, s/2);                              // 
		g.setColor(Color.BLUE);                                                  // 
		g.drawOval(x, y, s, s);                                                  // 
		g.setColor(Color.RED);                                                   // 
		g.drawOval(x - 1, y - 1, s + 2, s + 2);                                  // 
		                                                                         // 
	}                                                                            // 
                                                                                 // 
	/**
	 * Increases the bomb's size to 7 times original size for 30 ticks
	 * (half a second)
	 */
	public void detonate() {                                                     // 
		                                                                         // 
		if(!detonated) {                                                     	 // check if bomb is not already detonated
			                                                                     // 
			health = 30;                                                         // set health to 30
			x -= s*3;                                                            // adjust x
			y -= s*3;                                                            // adjust y
			s *= 7;                                                              // multiply size
			detonated = true;                                                    // set detonated flag to true
			                                                                     // 
		}                                                                        // 
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Detonates the bomb
	 */
	@Override                                                                    // 
	public boolean isDead() {                                                    // 
                                                                                 // 
		detonate();                                                              // detonate bomb
		return true;                                                             // 
		                                                                         // 
	}                                                                            // 
                                                                                 // 
	@Override                                                                    // 
	public String[] toInfo() {                                                   // 
                                                                                 // 
		String[] info = {"Collects items and deals damage ",                     // 
				"to all enemies within radius.",                                 // 
				"Cost: 20 ammo"};                                                // 
		                                                                         // 
		return info;                                                             // 
		                                                                         // 
	}                                                                            // 
	                                                                             // 
}                                                                                // 
