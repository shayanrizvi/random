package random;

import java.awt.*;
import java.util.*;

/**
 * 
 * This class defines Player functionality.
 * 
 * @author Shayan
 *
 */
public class Player extends GameObject {
	
	protected final static ID id = ID.Player;													 // Initialize ID
	protected int lives;                                                                         // lives
	protected int ammo;                                                                          // ammo
	protected int maxHealth;                                                                     // max health
	protected int maxAmmo;                                                                       // max ammo
	protected int speedUp;                                                                       // speed up counter
	protected int slowMo;                                                                        // slow mo counter
	protected int freeze;                                                                        // freeze counter
	protected int invincibility;                                                                 // invincibility counter
	protected int healthBoost;                                                                   // health boost
	protected int speedBoost;                                                                    // speed boost
	protected int damageBoost;                                                                   // damage boost
	protected int ammoBoost;                                                                     // ammo boost
                                                                                                 // 
	protected LinkedHashSet<GameObject> collected = new LinkedHashSet<GameObject>();             // collected list
	protected LinkedHashSet<ID> powerUp = new LinkedHashSet<ID>();                               // power up list
	                                                                                             // 
	private int counter;                                                                         // counter
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs the player object
	 * 
	 * @param x	X coordinate
	 * @param y	Y coordinate
	 * @param s	size
	 */
	public Player(int x, int y, int s) {                                                         // 
		                                                                                         // 
		super(x, y, s, id);                                                                      // pass coordinates, size, and ID to superclass constructor
		lives = 1;                                                                               // initialize lives
		maxHealth = 100;                                                                         // initialize max health
		maxAmmo = 100;                                                                           // initialize max ammo
		health = maxHealth;                                                                      // initialize health
		ammo = maxAmmo;                                                                          // initialize ammo
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Update player
	 */
	public void tick() {                                                                         // 
		                                                                                         // 
		if(!(hit)) {                                                                             // check if hit flag is set
			                                                                                     // 
			if(counter >= 10) {                                                                  // for every 10 ticks
				                                                                                 // 
				color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));               // change color to random color
				counter = 0;                                                                     // reset counter
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		counter++;                                                                               // increment counter
                                                                                                 // 
		if(counter > 40) hit = false;                                                            // reset hit flag after 40 ticks
		                                                                                         // 
		if(health <= 0) {                                                                        // check if health is less than or equal to 0
			                                                                                     // 
			if(lives > 0) health = maxHealth;                                                    // reset health if lives is is greater than 1
			lives--;                                                                             // decrement lives
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		x += velX;                                                                               // update x
		y += velY;                                                                               // update y
		                                                                                         // 
		x = flip(x, 0, Game.WIDTH - s);                                                          // flip x at frame's vertical edges
		y = flip(y, 50, Game.HEIGHT - s);                                                        // flip y at frame's horizontal edges
		                                                                                         // 
		if(speedUp > 0) {                                                                        // check if speed up counter is greater than 0
			                                                                                     // 
			if(velX < 0) velX = -15 - speedBoost;                                                // increase player left speed by 7
			else if(velX > 0) velX = 15 + speedBoost;                                            // increase player right speed by 7
			if(velY < 0) velY = -15 - speedBoost;                                                // increase player up speed by 7
			else if(velY > 0) velY = 15 + speedBoost;                                            // increase player down speed by 7
			speedUp--;                                                                           // decrement speed up counter
			                                                                                     // 
		} else powerUp.remove(ID.SpeedUp);                                                       // remove speed up from power up list if speed up counter is less than 1
		                                                                                         // 
		if(slowMo > 0) {                                                                         // check if slow mo counter is greater than 0
			                                                                                     // 
			slowMo--;                                                                            // decrement slow mo counter
			                                                                                     // 
		} else powerUp.remove(ID.SlowMo);                                                        // remove slow mo from power up list if slow mo counter is less than 1
		                                                                                         // 
		if(freeze > 0) {                                                                         // check if freeze counter is greater than 0
			                                                                                     // 
			freeze--;                                                                            // decrement freeze counter
			                                                                                     // 
		} else powerUp.remove(ID.Freeze);                                                        // remove freeze from power up list if freeze counter is less than 1
		                                                                                         // 
		if(invincibility > 0) {                                                                  // check if invincibility counter is greater than 0
			                                                                                     // 
			health = maxHealth;                                                                  // set health to max health
			ammo = maxAmmo;                                                                      // set ammo to max ammo
			invincibility--;                                                                     // decrement invincibility counter
			                                                                                     // 
		} else powerUp.remove(ID.Invincibility);                                                 // remove invincibility from power up list if invincibility counter is less than 1
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw player
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		g.setColor(Color.WHITE);                                                                 // render player
		g.fillOval(x - 6, y - 6, s + 12, s + 12);                                                // 
		g.fillRect(x - 2, y - 2, s + 4, s + 4);                                                  // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.fillOval(x - 4, y - 4, s + 8, s + 8);                                                  // 
		g.setColor(Color.WHITE);                                                                 // 
		g.fillRect(x, y, s, s);                                                                  // 
		g.setColor(color);                                                                       // 
		g.fillRect(x + 2, y + 2, s - 4, s - 4);                                                  // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	                                                                                             // 
	                                                                                             // 
	public LinkedHashSet<GameObject> getCollected() {                                            // 
		                                                                                         // 
		return collected;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public LinkedHashSet<ID> getPowerUp() {                                                      // 
																								 // 
		return powerUp;                                                                          // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getLives() {                                                                      // 
		                                                                                         // 
		return lives;                                                                            // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setLives(int lives) {                                                            // 
		                                                                                         // 
		this.lives = lives;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getAmmo() {                                                                       // 
		                                                                                         // 
		return ammo;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setAmmo(int ammo) {                                                              // 
		                                                                                         // 
		this.ammo = ammo;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getMaxHealth() {                                                                  // 
		                                                                                         // 
		return maxHealth;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setMaxHealth(int maxHealth) {                                                    // 
		                                                                                         // 
		this.maxHealth = maxHealth;                                                              // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getMaxAmmo() {                                                                    // 
		                                                                                         // 
		return maxAmmo;                                                                          // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setMaxAmmo(int maxAmmo) {                                                        // 
		                                                                                         // 
		this.maxAmmo = maxAmmo;                                                                  // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getSpeedUp() {                                                                    // 
		                                                                                         // 
		return speedUp;                                                                          // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setSpeedUp(int boost) {                                                          // 
		                                                                                         // 
		this.speedUp = boost;                                                                    // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getSlowMo() {                                                                     // 
		                                                                                         // 
		return slowMo;                                                                           // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setSlowMo(int slowMo) {                                                          // 
		                                                                                         // 
		this.slowMo = slowMo;                                                                    // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getFreeze() {                                                                     // 
		                                                                                         // 
		return freeze;                                                                           // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setFreeze(int freeze) {                                                          // 
		                                                                                         // 
		this.freeze = freeze;                                                                    // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getInvincibility() {                                                              // 
		                                                                                         // 
		return invincibility;                                                                    // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setInvincibility(int invincibility) {                                            // 
		                                                                                         // 
		this.invincibility = invincibility;                                                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getHealthBoost() {                                                                // 
		                                                                                         // 
		return healthBoost;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setHealthBoost(int healthBoost) {                                                // 
		                                                                                         // 
		this.healthBoost = healthBoost;                                                          // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getSpeedBoost() {                                                                 // 
		                                                                                         // 
		return speedBoost;                                                                       // 
																								 // 
	}                                                                                            // 
                                                                                                 // 
	public void setSpeedBoost(int speedBoost) {                                                  // 
		                                                                                         // 
		this.speedBoost = speedBoost;                                                            // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public int getDamageBoost() {                                                                // 
		                                                                                         // 
		return damageBoost;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setDamageBoost(int damageBoost) {                                                // 
		                                                                                         // 
		this.damageBoost = damageBoost;                                                          // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public int getAmmoBoost() {                                                                  // 
		                                                                                         // 
		return ammoBoost;                                                                        // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setAmmoBoost(int ammoBoost) {                                                    // 
		                                                                                         // 
		this.ammoBoost = ammoBoost;                                                              // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	@Override																					 // 
	public boolean isDead() {                                                                    // 
		                                                                                         // 
		if(lives <= 1) {                                                                         // 
			                                                                                     // 
			return health > 0 ? false: true;                                                     // 
			                                                                                     // 
		} else return false;                                                                     // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	@Override                                                                                    // 
	public String[] toInfo() {                                                                   // 
                                                                                                 // 
		String[] info = {id.name()};                                                             // 
		return info;                                                                             // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
