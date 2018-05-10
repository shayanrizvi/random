package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines flip enemy functionality.
 * 
 * @author Shayan
 *
 */
public class FlipEnemy extends GameObject {
	
	protected final static ID id = ID.FlipEnemy;		 // initialize ID
	private int counter;                                 // counter
	private Random r = new Random();                     // random
	                                                     // 
	/**
	 * Constructs a flip enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public FlipEnemy(int x, int y, int s) {              // 
                                                         // 
		super(x, y, s, id);                              // pass coordinates, size and ID to superclass constructor
		color = Color.DARK_GRAY;                         // initialize color
		health = 100;                                    // initialize health
		                                                 // 
		while(velX == 0 || velY == 0) {                  // ensure horizontal or vertical velocity is not 0
			                                             // 
			velX = -5 + r.nextInt(11);                   // initialize horizontal velocity
			velY = -5 + r.nextInt(11);                   // initialize vertical velocity
			                                             // 
		}                                                // 
		                                                 // 
	}                                                    // 
	                                                     // 
	/**
	 * Update flip enemy
	 */
	public void tick() {                                 // 
		                                                 // 
		if(!(hit)) {                                     // check if hit flag is set
		                                                 // 
			color = Color.DARK_GRAY;                     // reset color
			counter = 0;                                 // reset counter
			                                             // 
		}                                                // 
		                                                 // 
		counter++;                                       // increment counter
		                                                 // 
		if(counter > 20) {                               // check if counter is greater than 20
			                                             // 
			hit = false;                                 // reset hit flag 
			counter = 0;                                 // reset counter
			                                             // 
		}                                                // 
		                                                 // 
		if(r.nextInt(200) == 0) velX = -velX;            // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;            // randomly invert vertical velocity
											             // 
		x += velX;                                       // update x
		y += velY;                                       // update y
											             // 
		x = flip(x, 0, Game.WIDTH - s);                  // flip horizontal velocity at frame's vertical edges
		y = flip(y, 50, Game.HEIGHT - s);                // flip vertical velocity at frame's horizontal edges
                                                         // 
	}                                                    // 
	                                                     // 
	/**
	 * Draw flip enemy
	 */
	public void render(Graphics g) {                     // 
		                                                 // 
		g.setColor(color);                               // render flip enemy
		g.fillRect(x, y, s, s);                          // 
		                                                 // 
	}                                                    // 
                                                         // 
	@Override                                            // 
	public String[] toInfo() {                           // 
                                                         // 
		String[] info = {"Flips at edges."};			 // 
		                                                 // 
		return info;                                     // 
		                                                 // 
	}                                                    // 
	                                                     // 
}                                                        // 

