package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * 
 * This class defines seek enemy functionality.
 * 
 * @author Shayan
 *
 */
public class SeekEnemy extends GameObject {
	
	protected final static ID id = ID.SeekEnemy;							 // initialize ID
	private GameObject target;                                               // target
	private double xDiff;                                                    // difference between x coordinates
	private double yDiff;                                                    // difference between y coordinates
	private double distance;                                                 // distance
	private double rVel;                                                     // random velocity
	private int seek;                                                        // seek
	private int seekTime;                                                    // seek time
	private int counter;                                                     // counter
	private Random r = new Random();                                         // random
	                                                                         // 
	/**
	 * Constructs a bomb enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public SeekEnemy(int x, int y, int s, GameObject target) {               // 
		                                                                     // 
		super(x, y, s, id);                                                  // pass coordinates, size and ID to superclass constructor
		color = Color.GRAY;                                                  // initialize color
		health = 20 + r.nextInt(81);                                         // initialize health
		this.target = target;                                                // copy target reference
		rVel = r.nextDouble() + 0.5;                                         // initialize random velocity
		seekTime = 20 + health;                                              // initialize seek time
		                                                                     // 
	}                                                                        // 
                                                                             // 
	/**
	 * Update seek enemy
	 */
	public void tick() {                                                     // 
                                                                             // 
		if(!(hit)) {                                                         // check if hit flag is set
		                                                                     // 
			color = Color.GRAY;                                              // reset color
			counter = 0;                                                     // reset counter
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		counter++;                                                           // increment counter
		                                                                     // 
		if(counter > 20) {                                                   // check if counter is greater than 20
			                                                                 // 
			hit = false;                                                     // reset hit flag 
			counter = 0;                                                     // reset counter
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		xDiff = (x + s/2) - (target.x + target.s/2);                         // calculate difference between seeking missle and object x coordinates
		yDiff = (y + s/2) - (target.y + target.s/2);                         // calculate difference between seeking missle and object y coordinates
		distance = (double)Point2D.distance(x + s/2, y + s/2, target.x       // calculate distance
				+ target.s/2, target.y + target.s/2);                        // 
		                                                                     // 
		if(seek > seekTime) {                                                // check if seek time has elapsed
			                                                                 // 
			velX = (int) ((-100.0/distance) * xDiff * rVel)/20;              // update horizontal velocity to seek object
			velY = (int) ((-100.0/distance) * yDiff * rVel)/20;              // update vertical velocity to seek object
			                                                                 // 
			seek = 0;                                                        // reset seek
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		seek++;                                                              // increment seek
		                                                                     // 
		x += velX;                                                           // update x
		y += velY;                                                           // update y
		                                                                     // 
		velX = bounce(velX, x, 0, Game.WIDTH - s);                           // bounce horizontal velocity at frame's vertical edges
		velY = bounce(velY, y,  50,  Game.HEIGHT - s);                       // bounce vertical velocity at frame's horizontal edges
		                                                                     // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Draw seek enemy
	 */
	public void render(Graphics g) {                                         // 
                                                                             // 
		g.setColor(Color.WHITE);                                             // render seek enemy
		g.fillRect(x - 2, y - 2, s + 4, s + 4);                              // 
		g.setColor(color);                                                   // 
		g.fillRect(x, y, s, s);                                              // 
		g.setColor(Color.BLACK);                                             // 
		g.fillRect(x + 4, y + 4, s - 8, s - 8);                              // 
		                                                                     // 
	}                                                                        // 
                                                                             // 
	@Override                                                                // 
	public String[] toInfo() {                                               // 
                                                                             // 
		String[] info = {"Follows the player."};                             // 
		return info;                                                         // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
}                                                                            // 
