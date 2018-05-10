package random;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * 
 * This class defines bomb enemy functionality.
 * 
 * @author Shayan
 *
 */
public class BombEnemy extends GameObject {
	
	protected final static ID id = ID.BombEnemy;							 // initialize ID
	private LinkedList<Bomb> bombList = new LinkedList<Bomb>();              // bomb list
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
	public BombEnemy(int x, int y, int s, GameObject target) {               // 
		                                                                     // 
		super(x, y, s, id);                                                  // pass coordinates, size and ID to superclass constructor
		color = Color.BLACK;                                                 // initialize color
		health = 20 + r.nextInt(81);                                         // initialize health
		this.target = target;                                                // copy target reference
		rVel = r.nextDouble() + 0.5;                                         // initialize random velocity
		seekTime = 20 + health;                                              // initialize seek time
		                                                                     // 
	}                                                                        // 
                                                                             // 
	/**
	 * Update bomb enemy
	 */
	public void tick() {                                                     // 
                                                                             // 
		if(!(hit)) {                                                         // check if hit flag is set
		                                                                     // 
			color = Color.BLACK;                                             // reset color
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
		xDiff = (x + s/2) - (target.getX() + target.getS()/2);               // calculate difference between bomb enemy and target x coordinates
		yDiff = (y + s/2) - (target.getY() + target.getS()/2);               // calculate difference between bomb enemy and target y coordinates
		distance = (double)Point2D.distance(x + s/2, y + s/2, target.getX()  // calculate distance
				+ target.getS()/2, target.getY() + target.getS()/2);         // 
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
		if(r.nextInt(200) == 0) {                                            // randomize
                                                                             // 
			bombList.add(dropBomb(                                           // drop bomb
					10 + r.nextInt(21),                                      // 
					30 + r.nextInt(121),                                     // 
					1));                                                     // 
			                                                                 // 
		}                                                                    // 
		                                                                     // 
		for(int i = 0; i < bombList.size(); i++) {                           // for each bomb in bomb list
			                                                                 // 
			bombList.get(i).tick();                                          // update bomb
			                                                                 // 
			if(bombList.get(i).getHealth() <= 0) {                           // check if bomb is dead
				                                                             // 
				bombList.remove(i);                                          // remove bomb
				                                                             // 
			} else if(bombList.get(i).collides(target)) {                    // check if bomb collides with target
                                                                             // 
				target.setHealth(Math.max(0, target.getHealth() - 1));       // decrease target health by bomb damage (1)
				bombList.get(i).isDead();                                    // detonate bomb
				                                                             // 
			}                                                                // 
			                                                                 // 
		}                                                                    // 
                                                                             // 
		x += velX;                                          				 // update x
		y += velY;                                          				 // update y
																			 // 
		if(r.nextBoolean()) {                               				 // randomize
																			 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);      				 // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50,  Game.HEIGHT - s);  				 // bounce vertical velocity at frame's horizontal edges
																			 // 
		} else {                                            				 // 
																			 // 
			x = flip(x,  0,  Game.WIDTH - s);               				 // flip x at frame's vertical edges
			y = flip(y,  50,  Game.HEIGHT - s);             				 // flip y at frame's horizontal edges
																			 // 
		}                                                   				 // 
																			 // 
	}                                                                        // 
	                                                                         // 
	/**
	 * Draw bomb enemy
	 */
	public void render(Graphics g) {                                         // 
                                                                             // 
		g.setColor(Color.WHITE);                                             // render bomb enemy
		g.fillRect(x - 2, y - 2, s + 4, s + 4);                              // 
		g.setColor(color);                                                   // 
		g.fillRect(x, y, s, s);                                              // 
		g.setColor(Color.GRAY);                                              // 
		g.fillOval(x + 1, y + 1, s - 2, s - 2);                              // 
		g.setColor(color);                                                   // 
		g.fillOval(x + 4, y + 4, s - 8, s - 8);                              // 
		g.setColor(Color.WHITE);                                             // 
		g.fillOval(x + 7, y + 7, s - 14, s - 14);                            // 
                                                                             // 
		for(int i = 0; i < bombList.size(); i++) bombList.get(i).render(g);  // render bombs
			                                                                 // 
	}                                                                        // 
	                                                                         // 
	@Override                                                                // 
	public String[] toInfo() {                                               // 
                                                                             // 
		String[] info = {"Follows the player while ",                        // 
				"dropping bombs along the way."};                            // 
				                                                             // 
		return info;                                                         // 
		                                                                     // 
	}                                                                        // 
	                                                                         // 
}                                                                            // 
