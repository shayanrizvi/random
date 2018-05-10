package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines missle enemy functionality.
 * 
 * @author Shayan
 *
 */
public class MissleEnemy extends GameObject {
	
	protected final static ID id = ID.MissleEnemy;												 // initialize ID
	protected LinkedList<Missle> missleList = new LinkedList<Missle>();                          // missle list
	private GameObject target;                                                                   // target object
	private int seek;                                                                            // seek
	private int seekTime;                                                                        // seek time
	private int counter;                                                                         // counter
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs a missle enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public MissleEnemy(int x, int y, int s, GameObject target) {                                 // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size, and ID to superclass constructor
		color = Color.BLACK;                                                                     // initialize color
		health = 100;                                                                            // initialize health
		this.target = target;                                                                    // copy target reference
		seekTime = 40 + r.nextInt(121);                                                          // initialize seek time
																								 // 
		velX = seekTime/20;                                                                      // initialize horizontal velocity
		velY = seekTime/20;                                                                      // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	/**
	 * Update missle enemy
	 */
	public void tick() {                                                                         // 
                                                                                                 // 
		if(!(hit)) {                                                                             // check if hit flag is set
		                                                                                         // 
			color = Color.BLACK;                                                                 // reset color
			counter = 0;                                                                         // reset counter
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		counter++;                                                                               // increment counter
		                                                                                         // 
		if(counter > 20) {                                                                       // check if counter is greater than 20
			                                                                                     // 
			hit = false;                                                                         // reset hit flag 
			counter = 0;                                                                         // reset counter
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(seek > seekTime) {                                                                    // check if seek time has elapsed
			                                                                                     // 
			missleList.add(fireMissle(10 + r.nextInt(17), target.getX() + target.getS()/2,       // fire missle at target object
					target.getY() + target.getS()/2, 5, 3));                                    // 
			                                                                                     // 
			seek = 0;                                                                            // reset seek
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		seek++;                                                                                  // increment seek
                                                                                                 // 
		for(int i = 0; i < missleList.size(); i++) {                                             // for each missle in missle list
			                                                                                     // 
			missleList.get(i).tick();                                                            // update missle
			                                                                                     // 
			if(missleList.get(i).getHealth() <= 0) {                                             // check if missle is dead
				                                                                                 // 
				missleList.remove(i);                                                            // remove missle
				                                                                                 // 
			} else if(missleList.get(i).collides(target)) {                                      // check if missle collides with target
				                                                                                 // 
				target.setHealth(Math.max(0, target.getHealth()                                  // decrease target health by missle damage (5)
						- missleList.get(i).getDamage()));                                       // 
				                                                                                 // 
				missleList.remove(i);                                                            // remove missle from missle list
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(r.nextInt(200) == 0) velX = -velX;            										 // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;            										 // randomly invert vertical velocity
																								 // 
		x += velX;                                                                               // update x
		y += velY;                                                                               // update y
		                                                                                         // 
		velX = bounce(velX, x, 0, Game.WIDTH - s);                                               // bounce horizontal velocity at frame's vertical edges
		velY = bounce(velY, y, 50, Game.HEIGHT - s);                                             // bounce vertical velocity at frame's horizontal edges
		                                                                                         // 
		x = clamp(x, 0, Game.WIDTH - s);                                                         // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                                                       // clamp y to frame's horizontal edges
                                                                                                 // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw missle enemy
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		g.setColor(Color.GRAY);                                                                  // render missle enemy
		g.fillRect(x - 3, y - 3, s + 6, s + 6);                                                  // 
		g.setColor(color);                                                                       // 
		g.fillRect(x, y, s, s);                                                                  // 
		g.setColor(Color.WHITE);                                                                 // 
		g.fillRect(x - 10, y + s/2 - 2, s + 20, 4);                                              // 
		g.fillRect(x + s/2 - 2, y - 10, 4, s + 20);                                              // 
		                                                                                         // 
		for(int i = 0; i < missleList.size(); i++) missleList.get(i).render(g);                  // 
																								 // 
	}                                                                                            // 
																								 // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {"Fires missles at the player."};                                        // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
