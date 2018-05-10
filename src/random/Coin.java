package random;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
/**
 * 
 * This class defines Coin functionality.
 * 
 * @author Shayan
 *
 */
public class Coin extends GameObject {

	protected final static ID id = ID.Coin;						 // initialize ID
	private Random r = new Random();                             // random
	                                                             // 
	/**
	 * Constructs a coin object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public Coin(int x, int y, int s) {                           // 
		                                                         // 
		super(x, y, s, id);                                      // pass coordinates, size, and ID to superclass constructor
		color = Color.YELLOW;                                    // initialize color
		health = 20 + r.nextInt(80);                             // initialize health to random value between 20 and 99
                                                                 // 
		velX = 1 + health/20;                                    // initialize horizontal velocity
		velY = 1 + health/20;                                    // initialize vertical velocity
		                                                         // 
	}                                                            // 
	                                                             // 
	/**
	 * Update coin
	 */
	public void tick() {                                         // 
		                                                         // 
		if(r.nextInt(200) == 0) velX = -velX;                    // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;                    // randomly invert vertical velocity
			                                                     // 
		x += velX;                                               // update x
		y += velY;                                               // update y
		                                                         // 
		if(r.nextBoolean()) {                                    // randomize
			                                                     // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);           // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50,  Game.HEIGHT - s);       // bounce vertical velocity at frame's horizontal edges
			                                                     // 
		} else {                                                 // 
                                                                 // 
			x = flip(x,  0,  Game.WIDTH - s);                    // flip x at frame's vertical edges
			y = flip(y,  50,  Game.HEIGHT - s);                  // flip y at frame's horizontal edges
			                                                     // 
		}                                                        // 
		                                                         // 
	}                                                            // 
	                                                             // 
	/**
	 * Draw coin
	 */
	public void render(Graphics g) {                             // 
                                                                 // 
		g.setColor(Color.WHITE);                                 // render coin
		g.fillOval(x - 2, y - 2, s + 4, s + 4);                  // 
		g.setColor(color);                                       // 
		g.fillOval(x, y, s, s);                                  // 
		g.setColor(Color.BLACK);                                 // 
		g.setFont(new Font("Impact", Font.BOLD, 18));            // 
		g.drawString(Integer.toString(health), x, y + s - 3);    // 
		                                                         // 
	}                                                            // 
	                                                             // 
	public String[] toInfo() {                                   // 
		                                                         // 
		String[] info = {"Collect them all to advance ",         // 
				"to the next level."};                           // 
																 // 
		return info;                                             // 
		                                                         // 
	}                                                            // 
	                                                             // 
}                                                                // 
