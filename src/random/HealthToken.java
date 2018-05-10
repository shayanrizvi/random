package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines health token functionality.
 * 
 * @author Shayan
 *
 */
public class HealthToken extends GameObject {

	protected final static ID id = ID.HealthToken;							 // initialize ID
	private Random r = new Random();                                         // random
	                                                                         // 
	/**
	 * Constructs a health boost object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public HealthToken(int x, int y, int s) {                                // 
		                                                                     // 
		super(x, y, s, id);                                                  // pass coordinates, size, and ID to superclass constructor
		color = Color.GREEN;                                                 // initialize color
		health = 30 + r.nextInt(51);                                         // initialize health
                                                                             // 
		velX = health/8;                                                     // initialize horizontal velocity
		velY = health/8;                                                     // initialize vertical velocity
	                                                                         // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Update health token
	 */
	public void tick() {                                                     // 
                                                                             // 
		x += velX;                                                           // update x
		y += velY;                                                           // update y
                                                                             // 
		if(r.nextBoolean()) {                                                // randomize
			                                                                 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                       // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50,  Game.HEIGHT - s);                   // bounce vertical velocity at frame's horizontal edges
			                                                                 // 
		} else {                                                             // 
                                                                             // 
			x = flip(x, 0, Game.WIDTH - s);                                  // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                                // flip y at frame's horizontal edges
			                                                                 // 
		}                                                                    // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Draw health token
	 */
	public void render(Graphics g) {                                         // 
		                                                                     // 
		g.setColor(color);                                                   // render health token
		g.fillOval(x - 5, y - 5, s + 10, s + 10);                            // 
		g.setColor(Color.BLACK);                                             // 
		g.fillOval(x, y, s, s);                                              // 
		g.setFont(new Font("Consolas", Font.BOLD, s * 17/20));               // 
		g.setColor(Color.WHITE);                                             // 
		g.drawString(Integer.toString(health), x + s/10, y + s - s/5);       // 
		                                                                     // 
	}                                                                        // 
                                                                             // 
	@Override                                                                // 
	public String[] toInfo() {                                               // 
		                                                                     // 
		String[] info = {"Restores health."};                                // 
		return info;                                                         // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
}                                                                            // 
