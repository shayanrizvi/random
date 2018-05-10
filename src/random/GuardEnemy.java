package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines guard enemy functionality.
 * 
 * @author Shayan
 *
 */
public class GuardEnemy extends GameObject {
	
	protected final static ID id = ID.GuardEnemy;												 // initialize ID
	protected static LinkedList<GameObject> guardedList = new LinkedList<GameObject>();          // guarded list
	private GameObject guarded;                                                                  // guarded item
	private int redValue;                                                                        // red value
	private int greenValue;                                                                      // green value
	private Color healthColor;                                                                   // health color
	private int counter;                                                                         // counter
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs a guarded enemy object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param guarded guarded object
	 */
	public GuardEnemy(int x, int y, int s, GameObject guarded) {                                 // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size and ID to superclass constructor
		color = Color.LIGHT_GRAY;                                                                // initialize color
		health = 20 + r.nextInt(81);                                                             // initialize health
		this.guarded = guarded;                                                                  // copy guarded item reference
		guardedList.add(guarded);                                                                // add guarded item to guarded list
		                                                                                         // 
		velX = Math.min(6, 2 + (100/health));                                                    // initialize horizontal velocity
		velY = Math.min(6, 2 + (100/health));                                                    // initialize vertical velocity
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update guarded enemy
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		if(!(hit)) {                                                                             // check if hit flag is set
		                                                                                         // 
			color = Color.LIGHT_GRAY;                                                            // reset color
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
		if(guardedList.contains(guarded)) {                                                      // check if guarded item
			                                                                                     // 
			guarded.setX(x + 15);                                                                // update guarded item x
			guarded.setY(y + 15);                                                                // update guarded item y
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
	 * Draw guarded enemy
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		g.setColor(Color.DARK_GRAY);                                                             // render guard enemy
		g.fillRect(x + s/4 - 2, y, 4, s);                                                        // 
		g.fillRect(x + s * 1/2 - 2, y, 4, s);                                                    // 
		g.fillRect(x + s * 3/4 - 2, y, 4, s);                                                    // 
		g.setColor(Color.WHITE);                                                                 // 
		g.drawOval(x - 2, y - 2, s + 4, s + 4);                                                  // 
		g.drawOval(x - 1, y - 1, s + 2, s + 2);                                                  // 
		g.drawOval(x, y, s, s);                                                                  // 
		g.setColor(color);                                                                       // 
		g.drawRect(x + 2, y + 2, s - 4, s - 4);                                                  // 
		g.drawRect(x + 3, y + 3, s - 6, s - 6);                                                  // 
		g.drawRect(x + 4, y + 4, s - 8, s - 8);                                                  // 
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
	}                                                                                            // 
	                                                                                             // 
	@Override																					 // 
	public boolean isDead() {                                                                    // 
		                                                                                         // 
		if(health <= 0) {                                                                        // 
			                                                                                     // 
			guardedList.remove(guarded);                                                         // remove guarded item from guarded list
			return true;                                                                         // 
			                                                                                     // 
		} else return false;                                                                     // 
			                                                                                     // 
	}                                                                                            // 
                                                                                                 // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {"Must be killed to collect ",                                           // 
				"the guarded item."};                                                            // 
				                                                                                 // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public static LinkedList<GameObject> getGuardedList() {                                      // 
		                                                                                         // 
		return guardedList;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public GameObject getGuarded() {                                                             // 
		                                                                                         // 
		return guarded;                                                                          // 
		                                                                                         // 
	}                                                                                            // 
																								 // 
}                                                                                                // 
