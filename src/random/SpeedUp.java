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
public class SpeedUp extends GameObject {

	protected final static ID id = ID.SpeedUp;							 // initialize ID
	private Random r = new Random();                                     // random
	                                                                     // 
	/**
	 * Constructs speed up object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public SpeedUp(int x, int y, int s) {                                // 
		                                                                 // 
		super(x, y, s, id);                                              // pass coordinates, size and ID to superclass constructor
		color = Color.ORANGE;                                            // initialize color
		health = 10 + r.nextInt(11);                                     // initialize health
		                                                                 // 
		velX = health/2 + 3;                                             // initialize horizontal velocity
		velY = health/2 + 3;                                             // initialize vertical velocity
		                                                                 // 
	}                                                                    // 
	                                                                     // 
	/**
	 * Update speed up
	 */
	public void tick() {                                                 // 
                                                                         // 
		if(r.nextInt(200) == 0) velX = -velX;                   		 // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;               			 // randomly invert vertical velocity
																		 // 
		x += velX;                                            			 // update x
		y += velY;                                           			 // update y
																		 // 
		if(r.nextBoolean()) {                                            // randomize
			                                                             // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                   // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);                // bounce vertical velocity at frame's horizontal edges
			                                                             // 
		} else {                                                         // 
                                                                         // 
			x = flip(x, 0, Game.WIDTH - s);                              // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                            // flip y at frame's horizontal edges
			                                                             // 
		}                                                                // 
		                                                                 // 
		x = clamp(x, 0, Game.WIDTH - s);                                 // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                               // clamp y to frame's horizontal edges
		                                                                 // 
	}                                                                    // 
	                                                                     // 
	/**
	 * Draw speed up
	 */
	public void render(Graphics g) {                                     // 
		                                                                 // 
		g.setColor(color);                                               // render speed up
		g.fillOval(x - 5, y - 5, s + 10, s + 10);                        // 
		g.setColor(Color.BLACK);                                         // 
		g.fillOval(x, y, s, s);                                          // 
		g.setFont(new Font("Consolas", Font.BOLD, s * 17/20));           // 
		g.setColor(Color.WHITE);                                         // 
		g.drawString(Integer.toString(health), x + s/10, y + s - s/5);   // 
		                                                                 // 
	}                                                                    // 
	                                                                     // 
	@Override                                                            // 
	public String[] toInfo() {                                           // 
		                                                                 // 
		String[] info = {"Increases player speed ",                      // 
				"for the duration."};                                    // 
																		 // 
		return info;                                                     // 
		                                                                 // 
	}                                                                    // 
	                                                                     // 
}                                                                        // 
