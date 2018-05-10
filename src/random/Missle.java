package random;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/**
 * 
 * This class defines missle functionality.
 * 
 * @author Shayan
 *
 */
public class Missle extends Weapon {
	
	protected final static ID id = ID.Missle;													 // initialize ID
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**                                                                                             
	 * Constructs a missle object without a specified location to display on legend page            
	 */
	public Missle() {                                                                            // 
		                                                                                         // 
		super(10, id);                                                                           // pass size and ID to superclass constructor
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**                                                                                             
	 * Constructs a missle object which travels from the origin object towards                      
	 * the specified target coordinates                                                             
	 *                                                                                              
	 * @param origin	game object which invoked the missle constructor call                       
	 * @param s	missle size                                                                         
	 * @param targetX	target X coordinate                                                         
	 * @param targetY	target Y coordinate                                                         
	 * @param damage	missle damage                                                               
	 * @param velocity	missle velocity                                                             
	 */                                                                                             
	public Missle(GameObject origin, int s, int targetX, int targetY, int damage, int velocity) {// 
		                                                                                         // 
		super(origin, s, 100, damage, id);                                                       // pass origin, size, health, damage, and ID to superclass constructor
		                                                                                         // 
		velX = (targetX - x)/velocity;                                                           // initialize horizontal velocity
		velY = (targetY - y)/velocity;                                                           // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update missle
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		x += velX;                                                                               // update x
		y += velY;                                                                               // update y
		                                                                                         // 
		if(x > Game.WIDTH || y > Game.HEIGHT || x < 0 || y < 50) health = 0; 			         // kill missle if it passes frame edges
		else health--;                                                                           // decrement health
																								 // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw missle
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // render missle
		g.fillOval(x - 1, y - 1, s + 2, s + 2);                                                  // 
		g.setColor(color);                                                                       // 
		g.fillOval(x + 3, y + 3, s - 6, s - 6);													 // 
		g.setColor(Color.RED);                                                                   // 
		g.drawRect(x + 1, y + 1, s - 2, s - 2);                                                  // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {"Collects the first item ",                                             // 
				"or damages the first enemy",                                                    // 
				"it hits.",                                                                      // 
				"Cost = 1 ammo"};                                                                // 
		                                                                                         // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 