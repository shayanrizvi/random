
package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines ammo boost functionality.
 * 
 * @author Shayan
 *
 */
public class AmmoBoost extends GameObject {

	protected final static ID id = ID.AmmoBoost;												 // initialize ID
	protected LinkedList<Missle> missleList = new LinkedList<Missle>();                          // missle list
	private GameObject target;                                                                   // target target
	private int redValue;                                                                        // red value
	private int greenValue;                                                                      // green value
	private Color healthColor;                                                                   // health color
	private int counter;                                                                         // counter
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs an ammo boost object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public AmmoBoost(int x, int y, int s, GameObject target) {                                   // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size and ID to superclass constructor
		this.target = target;                                                                    // copy target reference
		color = Color.BLUE;                                                                      // initialize color
		health = 100;                                                                            // initialize health
		                                                                                         // 
		velX = 4;                                                                                // initialize horizontal velocity
		velY = 4;                                                                                // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update ammo boost
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		if(!(hit)) {                                                                             // check if hit flag is set
		                                                                                         // 
			color = Color.BLUE;                                                                  // reset color
			counter = 0;                                                                         // reset counter
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		counter++;                                                                               // increment counter
		                                                                                         // 
		if(counter > 20) {                                                                       // check if counter is greater than 20
			                                                                                     // 
			hit = false;                                                                         // reset hit flag 
			counter = 0;                                                                         // reset counter
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(r.nextInt(50) == 0) {                                                                 // randomize
                                                                                                 // 
			int mH = 1;                                                                          // initialize missle horizontal direction
			int mV = 1;                                                                          // initialize missle vertical direction
			                                                                                     // 
			if(r.nextBoolean()) mH = -1;                                                         // randomize missle horizontal direction
			if(r.nextBoolean()) mV = -1;                                                         // randomize missle vertical direction
			                                                                                     // 
			missleList.add(fireMissle(18,                                                        // fire missle at random coordinates
					Math.max(x + s/2 - 9 + 200 * mH,                                             // 
							r.nextInt(Math.max(1, (x + s/2 - 9 + (300 * mH))))),                 // 
					Math.max(y + s/2 - 9 + 200 * mV,                                             // 
							r.nextInt(Math.max(1, (y + s/2 - 9 + (300 * mV))))),                 // 
					5,                                                                           // 
					3));                                                                         // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		greenValue = 255 * health / 100;                                                         // calculate green value
		redValue = 255 - greenValue;                                                             // calculate red value
		healthColor = new Color(redValue, greenValue, 0);                                        // initialize health color
		                                                                                         // 
		for(int i = 0; i < missleList.size(); i++) {                                             // for each missle in missle list
			                                                                                     // 
			missleList.get(i).tick();                                                            // update missle
			                                                                                     // 
			if(missleList.get(i).isDead()) {                                          		     // check if missle is dead
				                                                                                 // 
				missleList.remove(i);                                                            // remove missle from missle list
				                                                                                 // 
			} else if(missleList.get(i).collides(target)) {                                      // check if missle collides with the target
                                                                                                 // 
				target.setHealth(Math.max(0, target.getHealth()                                  // decrease the target's  health by missle damage (5)
						- missleList.get(i).getDamage()));                                       // 
				                                                                                 // 
				missleList.remove(i);                                                            // remove missle from missle list
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(r.nextInt(200) == 0) velX = -velX;                   								 // randomly invert horizontal velocity
		if(r.nextInt(200) == 0) velY = -velY;               								     // randomly invert vertical velocity
																								 // 
		x += velX;                                            								     // update x
		y += velY;                                           								     // update y
																								 // 
		velX = bounce(velX, x, 0, Game.WIDTH - s);                                               // bounce horizontal velocity at frame's vertical edges
		velY = bounce(velY, y, 50, Game.HEIGHT - s);                                             // bounce vertical velocity at frame's horizontal edges
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw ammo boost
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		g.setColor(color);                                                                       // render ammo boost
		g.fillOval(x - 7, y - 7, s + 14, s + 14);                                                // 
		g.setColor(Color.BLACK);                                                                 // 
		g.fillOval(x - 3, y - 3, s + 6, s + 6);                                                  // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.fillRect(x - 9, y + 9, s + 18, 3);                                                     // 
		g.fillRect(x + 9, y - 9, 3,  s + 18);													 // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.fillOval(x + 4, y + 4, s - 8, s - 8);                                                  // 
                                                                                                 // 
		g.setColor(Color.BLACK);                                                                 // render health bar
		g.drawRect(x - 1, y + s - 1, s + 2, 7);                                                  // 
		g.setColor(Color.WHITE);                                                                 // 
		g.drawRect(x, y + s, s, 5);                                                              // 
		g.setColor(healthColor);                                                                 // 
		g.fillRect(x + 1, y + s + 1, s * health/100 - 1, 4);                                     // 
		g.setFont(new Font("Consolas", Font.BOLD, 16));                                          // 
		g.drawString(Integer.toString(health), x + s/2 - 8, y + s + 20);                         // 
                                                                                                 // 
		for(int i = 0; i < missleList.size(); i++) missleList.get(i).render(g);                  // render missles
			                                                                                     // 
	}                                                                                            // 
	                                                                                             // 
	public String[] toInfo() {                                                                   // 
		                                                                                         // 
		String[] info = {"Must be killed to collect. ",                                          // 
				"Increases ammo capacity by 50. ",                                               // 
				"Maximum ammo capacity = 500"};                                                  // 
				                                                                                 // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
