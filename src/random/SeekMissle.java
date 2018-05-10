package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * 
 * This class defines seeking missle functionality.
 * 
 * @author Shayan
 *
 */
public class SeekMissle extends Weapon {

	protected final static ID id = ID.SeekMissle;														 // initialize ID
	private GameObject target;                                                                           // target object
	private double xDiff;                                                                                // difference between x coordinates
	private double yDiff;                                                                                // difference between y coordinates
	private double distance;                                                                             // distance
	private Random r = new Random();                                                                     // random
	                                                                                                     // 
	/**                                                                                             
	 * Constructs a seeking missle object without a specified location to display on legend page            
	 */
	public SeekMissle() {                                                                                // 
		                                                                                                 // 
		super(16, id);	                                                                                 // pass size and ID to superclass constructor
		                                                                                                 // 
	}                                                                                                    // 
	                                                                                                     // 
	/**                                                                                             	
	 * Constructs a missle object which travels from the origin object towards                      	
	 * the specified target object                                                             			
	 *                                                                                              	
	 * @param origin	game object which invoked the seeking missle constructor call                   
	 * @param s	missle size                                                                         	
	 * @param target	target object																	
	 * @param damage	missle damage                                                               	
	 * @param velocity	missle velocity                                                             	
	 */                                                                                             	
	public SeekMissle(GameObject origin, int s, GameObject target, int damage) {       				     // 
		                                                                                                 // 
		super(origin, s, 100, damage, id);                                                               // 
		this.target = target;                                                                            // 
		                                                                                                 // 
	}                                                                                                    // 
																										 // 
	/**                                                                                             	
	 * Constructs a seeking missle object which travels from the origin object towards                      	
	 * the specified target coordinates                                                             	
	 *                                                                                              	
	 * @param origin	game object which invoked the seeking missle constructor call                   
	 * @param s	missle size                                                                         	
	 * @param targetX	target X coordinate                                                         	
	 * @param targetY	target Y coordinate                                                         	
	 * @param damage	missle damage                                                               	
	 * @param velocity	missle velocity                                                             	
	 */                                                                                             	
	public SeekMissle(GameObject origin, int s, int targetX, int targetY, int damage, int velocity) {    // 
		                                                                                                 // 
		super(origin, s, 100, damage, id);                                                               // pass origin, size, health, damage, and ID to superclass constructor
		                                                                                                 // 
		velX = (targetX - x)/velocity;                                                                   // initialize horizontal velocity
		velY = (targetY - y)/velocity;                                                                   // initialize vertical velocity
		                                                                                                 // 
	}                                                                                                    // 
	                                                                                                     // 
	/**
	 * Update seeking missle
	 */
	public void tick() {                                                                                 // 
		                                                                                                 // 
		if(target != null) {                                                                             // check if target is not null
			                                                                                             // 
			if(target.isDead()) {                                                                        // check if target is dead
                                                                                                         // 
				velX = Integer.valueOf(velX);                                                            // maintain horizontal velocity
				velY = Integer.valueOf(velY);                                                            // maintain vertical velocity
				                                                                                         // 
			} else {                                                                                     // 
				                                                                                         // 
				xDiff = (x + s/2) - (target.getX() + target.getS()/2);                                   // calculate difference between seeking missle and object x coordinates
				yDiff = (y + s/2) - (target.getY() + target.getS()/2);                                   // calculate difference between seeking missle and object y coordinates
				distance = (double) Point2D.distance(x + s/2, y + s/2, target.getX()                     // calculate distance
						+ target.getS()/2, target.getY() + target.getS()/2);                             // 
				                                                                                         // 
				velX = (int) ((-100.0/distance) * xDiff)/10;                                             // update horizontal velocity to seek object
				velY = (int) ((-100.0/distance) * yDiff)/10;                                             // update vertical velocity to seek object
				                                                                                         // 
			}                                                                                            // 
			                                                                                             // 
		}                                                                                                // 
		                                                                                                 // 
		x += velX;                                                                                       // update x
		y += velY;                                                                                       // update y
		                                                                                                 // 
		if(x > Game.WIDTH || y > Game.HEIGHT || x < 0 || y < 50) health = 0; 			    		     // kill seeking missle if it passes frame edges
		else health--;                                                                    		         // decrement health
																								         // 
	}                                                                                                    // 
	                                                                                                     // 
	/**
	 * Draw seeking missle
	 */
	public void render(Graphics g) {                                                                     // 
		                                                                                                 // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                           // render seeking missle
		g.fillOval(x - 1, y - 1, s + 2, s + 2);                                                          // 
		g.setColor(color);                                                                               // 
		g.fillRect(x + 3, y + 3, s - 6, s - 6);                                                          // 
		g.setColor(Color.BLUE);                                                                          // 
		g.drawRect(x + 1, y + 1, s - 2, s - 2);                                                          // 
		g.setColor(Color.WHITE);                                                                         // 
		g.drawRect(x, y, s, s);                                                                          // 
		g.setColor(Color.RED);                                                                           // 
		g.drawOval(x - 2, y - 2, s + 4, s + 4);                                                          // 
		                                                                                                 // 
	}                                                                                                    // 
                                                                                                         // 
	@Override                                                                                            // 
	public String[] toInfo() {                                                                           // 
                                                                                                         // 
		String[] info = {"Follows the targeted ",                                                        // 
				"item or enemy.",                                                                        // 
				"Cost = 5 ammo"};                                                                        // 
		                                                                                                 // 
		return info;                                                                                     // 
		                                                                                                 // 
	}                                                                                                    // 
	                                                                                                     // 
}                                                                                                        // 
