package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
/**
 * 
 * This class defines health boost functionality.
 * 
 * @author Shayan
 *
 */
public class HealthBoost extends GameObject {

	protected final static ID id = ID.HealthBoost;												 // initialize ID
	private GameObject target;                                                                   // target object
	private double xDiff;                                                                        // difference between x coordinates
	private double yDiff;                                                                        // difference between y coordinates
	private double distance;                                                                     // distance
	private double rVel;                                                                         // random velocity
	private int flee;                                                                            // flee
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs a health boost object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public HealthBoost(int x, int y, int s, GameObject target) {                                 // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size, and ID to superclass constructor
		color = Color.GREEN;                                                                     // initialize color
		health = 100;                                                                            // initialize health
		rVel = r.nextDouble() + 0.5;                                                             // initialize random velocity
		this.target = target;                                                                    // copy target reference
		                                                                                         // 
		velX = 3 + health/10;                                                                    // initialize horizontal velocity
		velY = 3 + health/10;                                                                    // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update health boost
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		xDiff = (x + s/2) - (target.getX() + target.getS()/2);                                   // calculate difference between health boost and target x coordinates
		yDiff = (y + s/2) - (target.getY() + target.getS()/2);                                   // calculate difference between health boost and target y coordinates
		distance = (double)Point2D.distance(x + s/2, y + s/2, target.getX()                      // calculate distance
				+ target.getS()/2, target.getY() + target.getS()/2);                             // 
		                                                                                         // 
		if(flee >= 30) {                                                                         // check if flee time has elapsed
			                                                                                     // 
			velX = (int) ((100.0/distance) * xDiff * rVel)/15;                                   // update horizontal velocity to flee object
			velY = (int) ((100.0/distance) * yDiff * rVel)/15;                                   // update vertical velocity to flee object
			                                                                                     // 
			flee = 0;                                                                            // reset flee
			                                                                                     // 
			if(r.nextBoolean()) {                                                                // randomize
				                                                                                 // 
				x = 100 + r.nextInt(Game.WIDTH - 100);                                           // set x to random coordinate
				y = 100 + r.nextInt(Game.HEIGHT - 100);                                          // set y to random coordinate
                                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		flee++;                                                                                  // increment flee
                                                                                                 // 
		if(r.nextInt(200) == 0) velX = -velX;                   								 // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;               								     // randomly invert vertical velocity
																								 // 
		x += velX;                                            								     // update x
		y += velY;                                           								     // update y
																								 // 
		if(r.nextBoolean()) {                                                                    // randomize
			                                                                                     // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                                           // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);                                        // bounce vertical velocity at frame's horizontal edges
			                                                                                     // 
		} else {                                                                                 // 
                                                                                                 // 
			x = flip(x, 0, Game.WIDTH - s);                                                      // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                                                    // flip y at frame's horizontal edges
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		x = clamp(x, 0, Game.WIDTH - s);                                                         // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                                                       // clamp y to frame's horizontal edges
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw health boost
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		g.setColor(color);                                                                       // render health boost
		g.fillOval(x - 1, y - 1, s + 2, s + 2);                                                  // 
		g.fillRect(x - 10, y + 6, s/2, s/2 - 2);                                                 // 
		g.fillRect(x + s + 1, y + 6, s/2, s/2 - 2);                                              // 
		g.fillRect(x + 6, y - 10, s/2 - 2, s/2);                                                 // 
		g.fillRect(x + 6, y + s + 1, s/2 - 2, s/2);                                              // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.fillOval(x + 3, y + 3, s - 6, s - 6);                                                  // 
		g.fillRect(x - 8, y + 9, s + 16, 2);                                                     // 
		g.fillRect(x + 9, y - 8, 2,  s + 16);                                                    // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {"Increases health capacity by 50. ",                                    // 
				"Maximum health capacity = 500"};                                                // 
				                                                                                 // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
