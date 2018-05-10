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
public class HorizontalEnemy extends GameObject {
	
	protected final static ID id = ID.HorizontalEnemy;	 // initialize ID
	private int counter;                                 // counter
	private Random r = new Random();                     // random
	                                                     // 
	/**
	 * Constructs a horizontal enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public HorizontalEnemy(int x, int y, int s) {        // 
                                                         // 
		super(x, y, s, id);                              // pass coordinates, size, and ID to superclass constructor
		color = Color.LIGHT_GRAY;                        // initialize color
		health = 100;                                    // initialize health
		                                                 // 
		while(velX == 0) velX = -5 + r.nextInt(11);      // ensure horizontal velocity is not 0
		                                                 //
	}                                                    // 
                                                         // 
	/**
	 * Update horizontal enemy
	 */
	public void tick() {                                 // 
                                                         // 
		if(!(hit)) {                                     // check if hit flag is set
		                                                 // 
			color = Color.LIGHT_GRAY;                    // reset color
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
		if(r.nextInt(100) == 0) velX = -velX;            // randomly invert horizontal velocity
			                                             // 
		x += velX;                                       // update x
		                                                 // 
		if(r.nextBoolean()) {                            // randomize
			                                             // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);   // bounce horizontal velocity at frame's vertical edges
			                                             // 
		} else {                                         // 
                                                         // 
			x = flip(x, 0, Game.WIDTH - s);              // flip x at frame's vertical edges
			                                             // 
		}                                                // 
		                                                 // 
	}                                                    // 
	                                                     // 
	/**
	 * Draw horizontal enemy
	 */
	public void render(Graphics g) {                     // 
                                                         // 
		g.setColor(color);                               // render horizontal enemy
		g.fillRect(x, y, s, s/2);                        // 
		g.setColor(Color.DARK_GRAY);                     // 
		g.fillRect(x, y + s/2, s, s/2);                  // 
		                                                 // 
	}                                                    // 
                                                         // 
	@Override                                            // 
	public String[] toInfo() {                           // 
                                                         // 
		String[] info = {"Only moves horizontally."};    // 
		return info;                                     // 
		                                                 // 
	}                                                    // 
	                                                     // 
}                                                        // 
