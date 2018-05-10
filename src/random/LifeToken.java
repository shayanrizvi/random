package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * 
 * This class defines life token functionality.
 * 
 * @author Shayan
 *
 */
public class LifeToken extends GameObject {
	
	protected final static ID id = ID.LifeToken;								 // initialize ID
	private GameObject target;                                                   // target object
	private double xDiff;                                                        // difference between x coordinates
	private double yDiff;                                                        // difference between y coordinates
	private double distance;                                                     // distance
	private double rVel;                                                         // random velocity
	private int flee;                                                            // flee
	private Random r = new Random();                                             // random
	                                                                             // 
	/**
	 * Constructs a life token object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public LifeToken(int x, int y, int s, GameObject target) {                   // 
		                                                                         // 
		super(x, y, s, id);                                                      // pass coordinates, size, and ID to superclass constructor
		color = Color.GREEN;                                                     // initialize color
		health = 100;                                                            // initialize health
		rVel = r.nextDouble() + 0.5;                                             // initialize random velocity
		this.target = target;                                                    // copy target reference
		                                                                         // 
		velX = 3 + r.nextInt(8);                                                 // initialize horizontal velocity
		velY = 3 + r.nextInt(8);                                                 // initialize vertical velocity
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Update life token
	 */
	public void tick() {                                                         // 
		                                                                         // 
		xDiff = (x + s/2) - (target.getX() + target.getS()/2);                   // calculate difference between health boost and target x coordinates
		yDiff = (y + s/2) - (target.getY() + target.getS()/2);                   // calculate difference between health boost and target y coordinates
		distance = (double)Point2D.distance(x + s/2, y + s/2, target.getX()      // calculate distance
				+ target.getS()/2, target.getY() + target.getS()/2);             // 
		                                                                         // 
		if(flee > 50) {                                                          // check if flee time has elapsed
			                                                                     // 
			rVel = r.nextDouble() + 0.5;                                         // randomize random velocity
			velX = (int) ((100.0/distance) * xDiff * rVel)/12;                   // update horizontal velocity to flee object
			velY = (int) ((100.0/distance) * yDiff * rVel)/12;                   // update vertical velocity to flee object
			                                                                     // 
			flee = 0;                                                            // reset flee
			                                                                     // 
		}                                                                        // 
		                                                                         // 
		flee++;                                                                  // increment flee
                                                                                 // 
		x += velX;                                            					 // update x
		y += velY;                                           					 // update y
																				 // 
		if(r.nextBoolean()) {                                                    // randomize
			                                                                     // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                           // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);                        // bounce vertical velocity at frame's horizontal edges
			                                                                     // 
		} else {                                                                 // 
                                                                                 // 
			x = flip(x, 0, Game.WIDTH - s);                                      // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                                    // flip y at frame's horizontal edges
			                                                                     // 
		}                                                                        // 
		                                                                         // 
		x = clamp(x, 0, Game.WIDTH - s);                                         // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                                       // clamp y to frame's horizontal edges
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	/**
	 * Draw life token
	 */
	public void render(Graphics g) {                                             // 
		                                                                         // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));   // render life token
		g.fillRect(x - 6, y, s * 3, s);                                          // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));   // 
		g.fillRect(x, y - 6, s, s * 3);                                          // 
		g.setColor(Color.GREEN);                                                 // 
		g.fillRect(x, y, s, s);                                                  // 
		                                                                         // 
	}                                                                            // 
	                                                                             // 
	@Override                                                                    // 
	public String[] toInfo() {                                                   // 
		                                                                         // 
		String[] info = {"Increases lives by 1."};                               // 
		return info;                                                             // 
		                                                                         // 
	}                                                                            // 
	                                                                             // 
}                                                                                // 