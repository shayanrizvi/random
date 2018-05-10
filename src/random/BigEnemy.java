package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines big enemy functionality.
 * 
 * @author Shayan
 *
 */
public class BigEnemy extends GameObject {
	
	protected final static ID id = ID.BigEnemy;				 // initialize ID
	private int counter;                                     // counter
	private Random r = new Random();                         // random
	                                                         // 
	/**
	 * Constructs a big enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public BigEnemy(int x, int y, int s) {                   // 
                                                             // 
		super(x, y, s * 4, id);                              // pass coordinates, size and ID to superclass constructor
		color = Color.GRAY;                                  // initialize color
		health = 200;                                        // initialize health
		                                                     // 
		while(velX == 0 || velY == 0) {                      // ensure horizontal or vertical velocity is not 0
		                                                     // 
			velX = -4 + r.nextInt(9);                        // initialize horizontal velocity
			velY = -4 + r.nextInt(9);                        // initialize vertical velocity
		                                                     // 
		}                                                    // 
		                                                     // 
	}                                                        // 
                                                             // 
	/**
	 * Update big enemy
	 */
	public void tick() {                                     // 
		                                                     // 
		if(!(hit)) {                                         // check if hit flag is set
		                                                     // 
			color = Color.GRAY;                              // reset color
			counter = 0;                                     // reset counter
			                                                 // 
		}                                                    // 
		                                                     // 
		counter++;                                           // increment counter
		                                                     // 
		if(counter > 20) {                                   // check if counter is greater than 20
			                                                 // 
			hit = false;                                     // reset hit flag 
			counter = 0;                                     // reset counter
			                                                 // 
		}                                                    // 
		                                                     // 
		if(r.nextInt(200) == 0) velX = -velX;                // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;                // randomly invert vertical velocity
															 // 
		x += velX;                                           // update x
		y += velY;                                           // update y
															 // 
		if(r.nextBoolean()) {                                // randomize
															 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);       // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50,  Game.HEIGHT - s);   // bounce vertical velocity at frame's horizontal edges
															 // 
		} else {                                             // 
															 // 
			x = flip(x,  0,  Game.WIDTH - s);                // flip x at frame's vertical edges
			y = flip(y,  50,  Game.HEIGHT - s);              // flip y at frame's horizontal edges
															 // 
		}                                                    // 
															 // 
	}                                                        // 
	                                                         // 
	/**
	 * Draw big enemy
	 */
	public void render(Graphics g) {                         // 
		                                                     // 
		g.setColor(color);                                   // render big enemy
		g.fillRect(x, y, s, s);                              // 
		                                                     // 
	}                                                        // 
	                                                         // 
	@Override                                                // 
	public String[] toInfo() {                               // 
                                                             // 
		String[] info = {"Slow and tanky."};                 // 
															 // 
		return info;                                         // 
		                                                     // 
	}                                                        // 
	                                                         // 
}                                                            // 
