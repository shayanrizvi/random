package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * 
 * This class defines invincibility functionality.
 * 
 * @author Shayan
 *
 */
public class Invincibility extends GameObject {
	
	protected final static ID id = ID.Invincibility;						 // initialize ID
	private GameObject target;                                               // target object
	private double xDiff;                                                    // difference between x coordinates
	private double yDiff;                                                    // difference between y coordinates
	private double distance;                                                 // distance
	private double rVel;                                                     // random velocity
	private int flee;                                                        // flee
	private Random r = new Random();                                         // random
	                                                                         // 
	/**
	 * Constructs an invincibility object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public Invincibility(int x, int y, int s, GameObject target) {           // 
		                                                                     // 
		super(x, y, s, id);                                                  // pass coordinates, size, and ID to superclass constructor
		health = 10 + r.nextInt(11);                                         // initialize health
		rVel = r.nextDouble() + 0.5;                                         // initialize random velocity
		this.target = target;                                                // copy target reference
		                                                                     // 
		velX = 3 + r.nextInt(8);                                             // initialize horizontal velocity
		velY = 3 + r.nextInt(8);                                             // initialize vertical velocity
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Update invincibility
	 */
	public void tick() {                                                     // 
		                                                                     // 
		xDiff = (x + s/2) - (target.getX() + target.getS()/2);               // calculate difference between invincibility and target x coordinates
		yDiff = (y + s/2) - (target.getY() + target.getS()/2);               // calculate difference between invincibility and target y coordinates
		distance = (double)Point2D.distance(x + s/2, y + s/2, target.getX()  // calculate distance
				+ target.getS()/2, target.getY() + target.getS()/2);         // 
		                                                                     // 
		if(flee > 50) {                                                      // check if flee time has elapsed
			                                                                 // 
			velX = (int) ((100.0/distance) * xDiff * rVel)/20;               // update horizontal velocity to flee object
			velY = (int) ((100.0/distance) * yDiff * rVel)/20;               // update vertical velocity to flee object
			                                                                 // 
			flee = 0;                                                        // reset flee
			                                                                 // 
			if(r.nextBoolean()) {                                            // randomize
				                                                             // 
				x = 100 + r.nextInt(Game.WIDTH - 100);                       // set x to random coordinate
				y = 100 + r.nextInt(Game.HEIGHT - 100);                      // set y to random coordinate
                                                                             // 
			}                                                                // 
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		flee++;                                                              // increment flee
                                                                             // 
		if(r.nextInt(200) == 0) velX = -velX;                   			 // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;               				 // randomly invert vertical velocity
																			 //
		x += velX;                                            				 // update x
		y += velY;                                           				 // update y
																			 // 
		if(r.nextBoolean()) {                                                // randomize
			                                                                 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);                       // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50, Game.HEIGHT - s);                    // bounce vertical velocity at frame's horizontal edges
			                                                                 // 
		} else {                                                             // 
                                                                             // 
			x = flip(x, 0, Game.WIDTH - s);                                  // flip x at frame's vertical edges
			y = flip(y, 50, Game.HEIGHT - s);                                // flip y at frame's horizontal edges
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		x = clamp(x, 0, Game.WIDTH - s);                                     // clamp x to frame's vertical edges
		y = clamp(y, 50, Game.HEIGHT - s);                                   // clamp y to frame's horizontal edges
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Draw invincibility
	 */
	public void render(Graphics g) {                                         // 
		                                                                     // 
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));   // render invincibility
		g.setColor(color);                                                   // 
		g.drawOval(x - 12, y - 6, s * 5, s * 5);                             // 
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));   // 
		g.setColor(color);                                                   // 
		g.drawOval(x - 6, y, s * 3, s * 3);                                  // 
		color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));   // 
		g.setColor(color);                                                   // 
		g.fillRect(x, y - 12, s, s * 7);                                     // 
                                                                             // 
		g.setFont(new Font("Consolas", Font.BOLD, 20 * 17/20));              // 
		g.setColor(Color.WHITE);                                             // 
		g.drawString(Integer.toString(health), x - 6, y + 15);               // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	@Override                                                                // 
	public String[] toInfo() {                                               // 
                                                                             // 
		String[] info = {"Unlimited health and ammo ",                       // 
				"for the duration."};                                        // 
		return info;                                                         // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
}                                                                            // 
                                                                             // 
                                                                             // 