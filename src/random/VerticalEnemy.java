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
public class VerticalEnemy extends GameObject {
	
	protected final static ID id = ID.VerticalEnemy;	 // initialize ID
	private int counter;                                 // counter
	private Random r = new Random();                     // random
	                                                     // 
	/**                                                  
	 * Constructs a vertical enemy object                
	 *                                                   
	 * @param x	X coordinate                             
	 * @param y	Y coordinate                             
	 * @param s	size                                     
	 */                                                  
	public VerticalEnemy(int x, int y, int s) {          // 
                                                         // 
		super(x, y, s, id);                              // pass coordinates, size, and ID to superclass constructor
		color = Color.DARK_GRAY;                         // initialize color
		health = 100;                                    // initialize health
		                                                 // 
		while(velY == 0) velY = -5 + r.nextInt(11);      // initialize vertical velocity
		                                                 //
	}                                                    // 
                                                         // 
	/**                                                  
	 * Update vertical enemy                           
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
		if(r.nextInt(100) == 0) velY = -velY;            // randomly invert vertical velocity
		                                                 // 
		y += velY;                                       // update y
		                                                 // 
		if(r.nextBoolean()) {                            // randomize
			                                             // 
			velY = bounce(velY, y, 50, Game.HEIGHT - s); // bounce vertical velocity at frame's hotizontal edges
			                                             // 
		} else {                                         // 
                                                         // 
			y = flip(y, 50, Game.HEIGHT - s);            // flip x at frame's horizontal edges
			                                             // 
		}                                                // 
		                                                 // 
	}                                                    // 
	                                                     // 
	/**                                                  
	 * Draw vertical enemy                             
	 */                                                  
	public void render(Graphics g) {                     // 
		                                                 // 
		g.setColor(color);                               // render vertical enemy
		g.fillRect(x, y, s/2, s);                        // 
		g.setColor(Color.LIGHT_GRAY);                    // 
		g.fillRect(x + s/2, y, s/2, s);                  // 
		                                                 // 
	}                                                    // 
	                                                     // 
	@Override                                            // 
	public String[] toInfo() {                           // 
                                                         // 
		String[] info = {"Only moves vertically."};      // 
		return info;                                     // 
		                                                 // 
	}                                                    // 
	                                                     // 
}                                                        // 
