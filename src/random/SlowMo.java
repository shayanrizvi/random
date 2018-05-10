package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines slow mo functionality.
 * 
 * @author Shayan
 *
 */
public class SlowMo extends GameObject {

	protected final static ID id = ID.SlowMo;								 // initialize ID
	private int counter;                                                     // counter
	private Random r = new Random();                                         // random
	                                                                         // 
	/**
	 * Constructs slow mo object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public SlowMo(int x, int y, int s) {                                     // 
		                                                                     // 
		super(x, y, s, id);                                                  // pass coordinates, size, and ID to superclass constructor
		color = Color.MAGENTA;                                               // initialize color
		health = 10 + r.nextInt(11);                                         // initialize health
		                                                                     // 
		velX = 5;                                                            // initialize horizontal velocity
		velY = 5;                                                            // initialize vertical velocity
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Update slow mo
	 */
	public void tick() {                                                     // 
                                                                             // 
		if(r.nextInt(50) ==0) velX = -velX;                                  // randomize horizontal velocity
		if(r.nextInt(50) == 0) velY = -velY;                                 // randomize vertical velocity
		                                                                     // 
		if(r.nextInt(50) == 0) velX = Math.max(7, velX++);                   // randomize horizontal velocity
		else if(r.nextInt(50) == 0) velX = Math.min(2, velX--);              // randomize vertical velocity
		                                                                     // 
		if(r.nextInt(50) == 0) velY = Math.max(7, velY++);                   // randomize horizontal velocity
		else if(r.nextInt(50) == 0) velY = Math.min(2, velY--);              // randomize vertical velocity
		                                                                     // 
		x += velX;                                                           // update x
		y += velY;                                                           // update y
		                                                                     // 
		if(counter > (health/-10 + 10) * 15) {                               // check counter for elapsed time
			                                                                 // 
			x = 100 + r.nextInt(Game.WIDTH - 100);                           // set x to random coordinate
			y = 100 + r.nextInt(Game.HEIGHT - 100);                          // set y to random coordinate
			                                                                 // 
			counter = 0;                                                     // reset counter
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		counter++;                                                           // increment counter
                                                                             // 
		if(r.nextBoolean()) {                                                // randomize
			                                                                 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                       // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y, 50, Game.HEIGHT - s);                     // bounce vertical velocity at frame's horizontal edges
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
	 * Draw slow mo
	 */
	public void render(Graphics g) {                                         // 
		                                                                     // 
		g.setColor(color);                                                   // render slow mo
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
		String[] info = {"Slows all items and enemies ",                     // 
				"to half speed ",                                            // 
				"for the duration."};                                        // 
		                                                                     // 
		return info;                                                         // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
}                                                                            // 
