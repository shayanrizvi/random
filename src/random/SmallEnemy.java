package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines Enemy functionality.
 * 
 * @author Shayan
 *
 */
public class SmallEnemy extends GameObject {
	
	protected final static ID id = ID.SmallEnemy;				 // initialize ID
	private int counter;                                 		 // counter
	private Random r = new Random();                    		 // random
																 // 
	/**
	 * Constructs a small enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public SmallEnemy(int x, int y, int s) {                     // 
                                                                 // 
		super(x, y, s * 3/4, id);                                // pass coordinates, size, and ID to superclass constructor
		color = Color.WHITE;                                     // initialize color
		health = 10;                                             // initialize health
		                                                         // 
		velX = 6 + r.nextInt(6);                                 // initialize horizontal velocity
		velY = 6 + r.nextInt(6);                                 // initialize vertical velocity
		                                                         // 
	}                                                            // 
                                                                 // 
	/**
	 * Update small enemy
	 */
	public void tick() {                                         // 
                                                                 // 
		if(!(hit)) {                                             // check if hit flag is set
		                                                         // 
			color = Color.WHITE;                                 // reset color
			counter = 0;                                         // reset counter
			                                                     // 
		}                                                        // 
		                                                         // 
		counter++;                                               // increment counter
		                                                         // 
		if(counter > 20) {                                       // check if counter is greater than 20
			                                                     // 
			hit = false;                                         // reset hit flag 
			counter = 0;                                         // reset counter
			                                                     // 
		}                                                        // 
		                                                         // 
		if(r.nextInt(200) == 0) velX = -velX;                    // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;               	 // randomly invert vertical velocity
																 // 
		x += velX;                                            	 // update x
		y += velY;                                           	 // update y
																 // 
		if(r.nextBoolean()) {                                    // randomize
			                                                     // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);           // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);        // bounce vertical velocity at frame's horizontal edges
			                                                     // 
		} else {                                                 // 
                                                                 // 
			x = flip(x, 0, Game.WIDTH - s);                      // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                    // flip y at frame's horizontal edges
			                                                     // 
		}                                                        // 
		                                                         // 
		x = clamp(x, 0, Game.WIDTH - s);                         // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                       // clamp y to frame's horizontal edges
		                                                         // 
	}                                                            // 
	                                                             // 
	/**
	 * Draw small enemy
	 */
	public void render(Graphics g) {                             // 
		                                                         // 
		g.setColor(color);                                       // render small enemy
		g.fillRect(x, y, s, s);                                  // 
		                                                         // 
	}                                                            // 
	                                                             // 
	@Override                                                    // 
	public String[] toInfo() {                                   // 
                                                                 // 
		String[] info = {"Fast and squishy."};                   // 
		return info;                                             // 
		                                                         // 
	}                                                            // 
	                                                             // 
}                                                                // 
