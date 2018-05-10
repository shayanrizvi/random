package random;

import java.awt.*;
import java.util.*;
/**
 * 
 * This class defines ammo token functionality.
 * 
 * @author Shayan
 *
 */
public class AmmoToken extends GameObject {

	protected final static ID id = ID.AmmoToken;												 // initialize ID
	private LinkedList<Bomb> bombList = new LinkedList<Bomb>();                                  // bomb list
	private GameObject target;                                                                   // target object
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs an ammo token object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 * @param target target object
	 */
	public AmmoToken(int x, int y, int s, GameObject target) {                                   // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size and ID to superclass constructor
		color = Color.BLUE;                                                                      // copy target reference
		this.target = target;                                                                    // initialize color
		health = 30 + r.nextInt(51);                                                             // initialize health
                                                                                                 // 
		velX = health/10;                                                                        // initialize horizontal velocity
		velY = health/10;                                                                        // initialize vertical velocity
	                                                                                             // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update ammo token
	 */
	public void tick() {                                                                         // 
                                                                                                 // 
		if(r.nextInt(200) == 0) {                                                                // randomize
                                                                                                 // 
			bombList.add(dropBomb(                                                               // drop bomb
					10 + r.nextInt(21),                                                          // 
					30 + r.nextInt(121),                                                         // 
					1));                                                                         // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		for(int i = 0; i < bombList.size(); i++) {                                               // for each bomb in bomb list
			                                                                                     // 
			bombList.get(i).tick();                                                              // update bomb
			                                                                                     // 
			if(bombList.get(i).getHealth() <= 0) {                                               // check if bomb is dead
				                                                                                 // 
				bombList.remove(i);                                                              // remove bomb
				                                                                                 // 
			} else if(bombList.get(i).collides(target)) {                                        // check if bomb collides with target
                                                                                                 // 
				target.setHealth(Math.max(0, target.getHealth() - bombList.get(i).getDamage())); // decrease target health by bomb damage (1)
				bombList.get(i).isDead();                                                        // detonate bomb
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
		if(r.nextBoolean()) {                                   								 // randomize
																								 // 
			velX = bounce(velX, x, 0, Game.WIDTH - s);        								     // bounce horizontal velocity at frame's vertical edges
			velY = bounce(velY, y,  50,  Game.HEIGHT - s);      								 // bounce vertical velocity at frame's horizontal edges
																								 // 
		} else {                                                								 // 
																								 // 
			x = flip(x,  0,  Game.WIDTH - s);                   								 // flip x at frame's vertical edges
			y = flip(y,  50,  Game.HEIGHT - s);                 								 // flip y at frame's horizontal edges
																								 // 
		}                                                       								 // 
																								 // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update ammo token
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		g.setColor(color);                                                                       // render ammo token
		g.fillOval(x - 5, y - 5, s + 10, s + 10);                                                // 
		g.setColor(Color.BLACK);                                                                 // 
		g.fillOval(x, y, s, s);                                                                  // 
		g.setFont(new Font("Consolas", Font.BOLD, s * 17/20));                                   // 
		g.setColor(Color.WHITE);                                                                 // 
		g.drawString(Integer.toString(health), x + s/10, y + s - s/5);                           // 
		                                                                                         // 
		for(int i = 0; i < bombList.size(); i++) bombList.get(i).render(g);                      // render bombs
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
		                                                                                         // 
		String[] info = {"Restores ammo."};                                                      // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}																								 // 
