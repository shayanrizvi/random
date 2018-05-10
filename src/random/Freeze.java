package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines freeze functionality.
 * 
 * @author Shayan
 *
 */
public class Freeze extends GameObject {

	protected final static ID id = ID.Freeze;							 // initialize ID
	private int counter;                                                 // counter
	private Random r = new Random();                                     // random
	                                                                     // 
	public Freeze(int x, int y, int s) {                                 // 
		                                                                 // 
		super(x, y, s, id);                                              // pass coordinates, size and ID to superclass constructor
		color = Color.CYAN;                                              // initialize color
		health = 4 + r.nextInt(7);                                       // initialize health
		                                                                 // 
	}                                                                    // 
	                                                                     // 
	/**
	 * Update freeze
	 */
	public void tick() {                                                 // 
		                                                                 // 
		if(counter > (health * -1 + 11) * 50) {                          // check if counter is greater than freeze time
			                                                             // 
			x = 100 + r.nextInt(Game.WIDTH - 100);                        // set x to random coordinate
			y = 100 + r.nextInt(Game.HEIGHT/2 - 100);                    // set y to random coordinate
			                                                             // 
			counter = 0;                                                 // reset counter
			                                                             // 
		}                                                                // 
		                                                                 // 
		counter++;                                                       // increment counter
		                                                                 // 
		x = clamp(x, 0, Game.WIDTH - s);                                 // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                               // clamp y to frame's horizontal edges
		                                                                 // 
	}                                                                    // 
	                                                                     // 
	/**
	 * Draw freeze
	 */
	public void render(Graphics g) {                                     // 
		                                                                 // 
		g.setColor(color);                                               // render freeze
		g.fillOval(x - 5, y - 5, s + 10, s + 10);                        // 
		g.setColor(Color.BLACK);                                         // 
		g.fillOval(x, y, s, s);                                          // 
		g.setFont(new Font("Consolas", Font.BOLD, s * 17/20));           // 
		g.setColor(Color.WHITE);                                         // 
		g.drawString(Integer.toString(health), x + 4, y + s - s/5);      // 
		                                                                 // 
	}                                                                    // 
                                                                         // 
	@Override                                                            // 
	public String[] toInfo() {                                           // 
                                                                         // 
		String[] info = {"Freezes all items and enemies ",               // 
				"for the duration."};                                    // 
		                                                                 // 
		return info;                                                     // 
		                                                                 // 
	}                                                                    // 
	                                                                     // 
}                                                                        // 
