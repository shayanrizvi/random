package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines HealthToken functionality.
 * 
 * @author Shayan
 *
 */
public class SpeedBoost extends GameObject {

	protected final static ID id = ID.SpeedBoost;									 // initialize ID
	private int counter;                                                             // counter
	private Random r = new Random();                                                 // random
	                                                                                 // 
	/**
	 * Constructs a speed boost object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public SpeedBoost(int x, int y, int s) {                                         // 
		                                                                             // 
		super(x, y, s, id);                                                          // pass coordinates, size and ID to superclass constructor
		color = Color.ORANGE;                                                        // initialize color
		health = 12;                                                                 // initialize health
		                                                                             // 
		velX = 10;                                                             	     // initialize horizontal velocity
		velY = 10;                                                              	 // initialize vertical velocity
		                                                                             // 
	}                                                                                // 
	                                                                                 // 
	/**
	 * Update speed boost
	 */
	public void tick() {                                                             // 
                                                                                     // 
		if(counter > 150) {                                                          // check counter for elapsed time
			                                                                         // 
			x = 100 + r.nextInt(Game.WIDTH - 100);                                   // set x to random coordinate
			y = 100 + r.nextInt(Game.HEIGHT - 100);                                  // set y to random coordinate
			                                                                         // 
			counter = 0;                                                             // reset counter
			                                                                         // 
		}                                                                            // 
		                                                                             // 
		counter++;                                                                   // increment counter
                                                                                     // 
		x += velX;                                            						 // update x
		y += velY;                                           						 // update y
																					 // 
		if(r.nextBoolean()) {                                                        // randomize
			                                                                         // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                               // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);                            // bounce vertical velocity at frame's horizontal edges
			                                                                         // 
		} else {                                                                     // 
                                                                                     // 
			x = flip(x, 0, Game.WIDTH - s);                                          // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                                        // flip y at frame's horizontal edges
			                                                                         // 
		}                                                                            // 
		                                                                             // 
		x = clamp(x, 0, Game.WIDTH - s);                                             // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                                           // clamp y to frame's horizontal edges
		                                                                             // 
	}                                                                                // 
	                                                                                 // 
	/**
	 * Draw speed boost
	 */
	public void render(Graphics g) {                                                 // 
                                                                                     // 
		g.setColor(color);                                                           // render speed boost
		g.fillOval(x - 1, y - 1, s + 2, s + 2);                                      // 
		g.fillRect(x - 10, y + 6, s/2, s/2 - 2);                                     // 
		g.fillRect(x + s + 1, y + 6, s/2, s/2 - 2);                                  // 
		g.fillRect(x + 6, y - 10, s/2 - 2, s/2);                                     // 
		g.fillRect(x + 6, y + s + 1, s/2 - 2, s/2);                                  // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));       // 
		g.fillOval(x + 3, y + 3, s - 6, s - 6);                                      // 
		g.fillRect(x - 8, y + 9, s + 16, 2);                                         // 
		g.fillRect(x + 9, y - 8, 2,  s + 16);                                        // 
		                                                                             // 
	}                                                                                // 
	                                                                                 // 
	@Override                                                                        // 
	public String[] toInfo() {                                                       // 
                                                                                     // 
		String[] info = {"Increases player speed."};                                 // 
		return info;                                                                 // 
		                                                                             // 
	}                                                                                // 
	                                                                                 // 
}                                                                                    // 
