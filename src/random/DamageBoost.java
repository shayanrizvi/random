
package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines damage boost functionality.
 * 
 * @author Shayan
 *
 */
public class DamageBoost extends GameObject {

	protected final static ID id = ID.DamageBoost;												 // initialize ID
	protected LinkedList<Missle> missleList = new LinkedList<Missle>();                          // missle list
	private GameObject target;                                                                   // target object
	private int redValue;                                                                        // red value
	private int greenValue;                                                                      // green value
	private Color healthColor;                                                                   // health color
	private int counter;                                                                         // counter
	private Random r = new Random();                                                             // 
	                                                                                             // 
	/**
	 * Constructs a damage boost object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public DamageBoost(int x, int y, int s, GameObject target) {                                 // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size and ID to superclass constructor
		this.target = target;                                                                    // copy target reference
		color = Color.RED;                                                                       // initialize color
		health = 100;                                                                            // initialize health
		                                                                                         // 
		velX = 4;                                                                                // initialize horizontal velocity
		velY = 4;                                                                                // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update damage boost
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		if(!(hit)) {                                                                             // check if hit flag is set
		                                                                                         // 
			color = Color.RED;                                                                   // reset color
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
		greenValue = 255 * health / 100;                                                         // calculate green value
		redValue = 255 - greenValue;                                                             // calculate red value
		healthColor = new Color(redValue, greenValue, 0);                                        // initialize health color
                                                                                                 // 
		if(r.nextInt(60) == 0) {                                                                 // randomize
                                                                                                 // 
			missleList.add(fireMissle(10 + r.nextInt(17), target.getX() + target.getS()/2,       // fire missle at target
					target.getY() + target.getS()/2, 5, 3));                                     // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		for(int i = 0; i < missleList.size(); i++) {                                             // for each missle in missle list
			                                                                                     // 
			missleList.get(i).tick();                                                            // update missle
			                                                                                     // 
			if(missleList.get(i).getHealth() <= 0) {                                             // check if missle is dead
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
	 * Draw damage boost
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		g.setColor(color);                                                                       // render damage boost
		g.fillOval(x - 7, y - 7, s + 14, s + 14);                                                // 
		g.setColor(Color.BLACK);                                                                 // 
		g.fillOval(x - 3, y - 3, s + 6, s + 6);                                                  // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.fillRect(x - 9, y + 9, s + 18, 3);                                                     // 
		g.fillRect(x + 9, y - 9, 3,  s + 18);                                                    // 
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
		g.drawString(Integer.toString(health), x + s/2 - 8, y + s + 20);						 // 
                                                                                                 // 
		for(int i = 0; i < missleList.size(); i++) missleList.get(i).render(g);                  // render missles
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	@Override                                                                                    // 
	public boolean hit() {                                                                       // 
		                                                                                         // 
		color = Color.WHITE;                                                                     // change color to white 
		return hit = true;                                                                       // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {"Must be killed to collect. ",                                          // 
				"Increase weapon ",                                                              // 
				"damage and size."};                                                             // 
				                                                                                 // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
