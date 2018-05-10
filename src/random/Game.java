package random;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;

/**
 * This class implements the game loop, initializes instances of other classes,
 * and handles their relative operation within the game.
 *
 * @author Shayan Rizvi
 */
public class Game extends Canvas implements Runnable {
	
	// Create variables
	private static final long serialVersionUID = -4584388369897487885L;							// class ID
	
	public static final int WIDTH =  (int)														// screen width
			Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int)														// screen height
			Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int maxScore = 2147483647;
	public static int frames;																	// frames per second
	
	private boolean running;																	// running
	private Thread thread;																		// game thread
	private Random r = new Random();															// random
	
	private Hud hud;																			// heads up display
	private Legend legend;																		// legend
	private LinkedList<GameObject> objects = new LinkedList<GameObject>();						// object list
	private LinkedList<Weapon> weapons = new LinkedList<Weapon>();								// player weapon list
	private LinkedList<Coin> coins = new LinkedList<Coin>();									// coin list
	private Player player;																		// player
	private int score;																			// score
	private int highScore;																		// highscore
	private int level;																			// level
	private int time;																			// time
	private int counter;																		// counter
	private double startTime;																	// start time
	private double pauseStartTime;																// pause start time
	private double pauseTime;																	// pause time
	private boolean gameStart = true;															// game start state
	private boolean gameSpawn = false;															// game spawn state
	private boolean gamePaused = false;															// game paused state
	private boolean gameLegend = false;															// game legend state
	private boolean gameOver = false;															// game over state
																								// 
	                                                                                            // 
																								// 
	/**
	 * Constructs the game
	 */
	public Game() {                                                                             // 
		                                                                                        // 
		File scoreFile = new File("score.txt");													// initialize score file
		                                                                                        // 
		try {                                                                                   // 
			                                                                                    // 
			Scanner in = new Scanner(scoreFile);                                                // read score file
			highScore = in.nextInt();                                                           // initialize highscore
			in.close();                                                                         // close score file
																								// 
		} catch (FileNotFoundException e) {highScore = 0;}                                      // if score file not found reset highscore
		                                                                                        // 
		new Window(WIDTH, HEIGHT, "Random", this);                                              // initialize window
		hud = new Hud(this);                                                                    // initialize hud
		legend = new Legend();                                                                  // initialize legend
		addKeyListener(new KeyInput(this));                                                     // add key listener
		addMouseListener(new MouseInput(this));		                                            // add mouse listener
//		addMouseMotionListener(new MouseInput(this));                                           // add mouse motion listener (for mouse lock within JFrame operation -- implementation pending)
		requestFocus();                                                                         // focus
		                                                                                        // 
	}                                                                                           // 
																								// 
	/**
	 * Start thread
	 */
	public synchronized void start() {                                                          // 
		                                                                                        // 
		thread = new Thread(this);                                                              // initialize thread
		thread.start();                                                                         // start thread / call run method
		running = true;                                                                         // initialize running
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Stop thread
	 */
	public synchronized void stop() {                                                           // 
		                                                                                        // 
		try {                                                                                   // 
																								// 
			thread.join();                                                                      // wait for thread to complete execution
			running = false;                                                                    // change running to false
			                                                                                    // 
		} catch(Exception e) {e.printStackTrace();}                                             // print exception
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Game loop (referenced)
	 */
	public void run() {																			// 
		                                                                                        // 
		long lastTime = System.nanoTime();                                                      // last time
		double amountOfTicks = 60.0;                                                            // amount of ticks
		double ns = 1000000000 / amountOfTicks;                                                 // nano second
		double delta = 0;                                                                       // delta
		long timer = System.currentTimeMillis();                                                // timer
		int frames = 0;                                                                         // frames
		                                                                                        // 
		while(running) {                                                                        // while the game is running
			                                                                                    // 
			long now = System.nanoTime();                                                       // get current time
			delta += (now - lastTime) / ns;                                                     // calculate time since last loop
			lastTime = now;                                                                     // set last time
			                                                                                    // 
			while(delta >= 1) {                                                                 // while delta is greater than 1
				                                                                                // 
				tick();                                                                         // game tick
				delta--;                                                                        // decrement delta
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
			if(running) {                                                                       // if the game is running
				                                                                                // 
				render();                                                                       // game render
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
			frames++;                                                                           // increment frames
			                                                                                    // 
			if(System.currentTimeMillis() - timer > 1000) {                                     // if one second has elapsed
				                                                                                // 
				Game.frames = frames;                                                           // record frames per second
				timer += 1000;                                                                  // increment timer by one second
				frames = 0;                                                                     // reset frames
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
		}                                                                                       // 
		                                                                                        // 
		stop();                                                                                 // call method to stop thread to ensure thread terminates smoothly
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Update frame
	 */
	private void tick() {                                                                       // 
			                                                                                    // 
		if(!gameStart) {                                                                        // check if game is not in start state
			                                                                                    // 
			if(player.isDead()) {                                                               // check if player is dead
				                                                                                // 
				objects.clear();                                                                // clear object list
				weapons.clear();                                                                // clear weapon list
				gameOver = true;                                                                // set game over flag to true
				                                                                                // 
				try {                                                                           // 
					                                                                            // 
					PrintWriter out = new PrintWriter("score.txt");               				// open score file
					out.print(Math.min(maxScore, highScore));                                   // write highscore to score file
					out.close();                                                                // close score file
					                                                                            // 
				} catch (FileNotFoundException e) {}											// do nothing if file not found (new file will be created)
				                                                                                // 
			} else if(coins.isEmpty()) {                                                        // check if coin list is empty, indicating level completion
				                                                                                // 
				objects.clear();                                                                // clear object list
				weapons.clear();                                                                // clear weapons list
				level++;                                                                        // increment level
				objects.add(player);                                                            // add player to object list
				player.setAmmo(Math.min(player.getMaxAmmo(),player.getAmmo() + 50));            // increase ammo by 50
				                                                                                // 
				spawn();                                                                        // spawn coins and enemies
				gameSpawn = true;                                                               // set game spawn flag to true
				counter = 3;                                                                    // set countdown counter to 3 seconds
		                                                                                        // 
				pauseStartTime = (-0.001 * (startTime                                           // record pause start time
						- System.currentTimeMillis()));                                         // 
				                                                                                // 
				                                                                                // 
			} else if(gameSpawn) {                                                              // check if game is in spawn state
				                                                                                // 
				if(time < -0.001 * (startTime                                                   // decrement countdown counter to 2 after 1 second
						- System.currentTimeMillis()) - pauseTime - 1) counter = 2;             // 
				                                                                                // 
				if(time < -0.001 * (startTime                                                   // decrement countdown counter to 1 after 2 seconds
						- System.currentTimeMillis()) - pauseTime - 2) counter = 1;             // 
				                                                                                // 
				if(time < -0.001 * (startTime                                                   // check if 3 seconds has elapsed since spawn start time
						- System.currentTimeMillis()) - pauseTime - 3) {                        // 
					                                                                            // 
					gameSpawn = false;                                                          // reset game spawn flag to false
		                                                                                        // 
					pauseTime = pauseTime + (-0.001 * (startTime                                // update total game pause time to include spawn time
							- System.currentTimeMillis())                                       // 
							- pauseStartTime);                                                  // 
					                                                                            // 
				}                                                                               // 
				                                                                                // 
			} else if(!(gamePaused)) {                                                          // check if game is not in paused state
				                                                                                // 
				time = (int) Math.round(-0.001 * (startTime                                     // update game time
						- System.currentTimeMillis()) - pauseTime);                             // 
				                                                                                // 
				hud.tick();                                                                     // update hud
				player.tick();                                                                  // update player
		                                                                                        // 
				for(int j = 0; j < weapons.size(); j++) {                                       // for each weapon in weapon list
					                                                                            // 
					weapons.get(j).tick();                                                      // update weapon
					                                                                            // 
					if(weapons.get(j).getId() != ID.Bomb) {										// check if weapon is not a bomb
																								// 
						if(weapons.get(j).isDead()) {                                 		    // check if missle is dead
							                                                                    // 
							weapons.remove(j);                                                  // remove missle from weapon list
							                                                                    // 
						}                                                                       // 
																								//
					} else {																	// if weapon is a bomb
																								// 
						if(weapons.get(j).getHealth() == 0) weapons.remove(j); 				    // remove bomb from weapon list if bomb health is 0 (calling the isDead method on a bomb detonates it)
																								// 
					}																			// 
																								//
				}                                                                               // 
				                                                                                // 
				for(int i = 0; i < objects.size(); i++) {                                       // for each object in object list
					                                                                            // 
					if(!(objects.get(i) == player)) {                                           // check if the object is not the player
																								// 
						if(!(player.getPowerUp().contains(ID.Freeze))                           // check if the player does not have the freeze or slow mo power ups
								&& !(player.getPowerUp().contains(ID.SlowMo))) {                // 
							                                                                    // 
							objects.get(i).tick();                                              // update object
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						if(!(player.getPowerUp().contains(ID.Freeze))                           // check if the player does not have the freeze power up but does have slowmo
								&& player.getPowerUp().contains(ID.SlowMo)) {                   // 
							                                                                    // 
							if(System.currentTimeMillis() % 2 == 0) {                           // for every other millisecond
								                                                                // 
								objects.get(i).tick();                                          // update object
								                                                                // 
							}                                                                   // 
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						if(player.collides(objects.get(i))) {                                   // check player collides with object
							                                                                    // 
							switch(objects.get(i).getId()) {                                    // switch object ID
							                                                                    // 
							case Coin: case LifeToken: case HealthToken: case AmmoToken:        // case: items
							case SpeedUp: case SlowMo: case Freeze: case Invincibility:         // 
							case HealthBoost: case SpeedBoost: {                                // 
								                                                                // 
								if(!(GuardEnemy.getGuardedList().contains(objects.get(i)))) {   // check if the item is not guarded
									                                                            // 
									player.getCollected().add(objects.get(i));                  // add item to collected list
									                                                            // 
								}                                                               // 
								                                                                // 
								break;                                                          // 
		                                                                                        // 
							} case BounceEnemy: case FlipEnemy: case HorizontalEnemy:           // case: enemies
							  case VerticalEnemy: case SmallEnemy: case BigEnemy:               // 
							  case SeekEnemy: case MissleEnemy: case BombEnemy:                 // 
							  case GuardEnemy: {                                                // 
								                                                                // 
								score = Math.max(0, score - 1);                                 // decrement score
								player.setHealth(Math.max(0, player.getHealth() - 1));          // decrement player health
								player.hit();                                                   // call hit method on player
								                                                                // 
								break;                                                          // 
								                                                                // 
							} default: break;                                                   // 
							                                                                    // 
							}                                                                   // 
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						for(int j = 0; j < weapons.size(); j++) {                               // for each weapon in weapon list
							                                                                    // 
							if(objects.get(i).collides(weapons.get(j))) {                       // if object collides with weapon
								                                                                // 
								switch(objects.get(i).getId()) {                                // switch object ID
								                                                                // 
								case Coin: case LifeToken: case HealthToken: case AmmoToken:    // case: items
								case SpeedUp: case SlowMo: case Freeze: case Invincibility:     // 
								case HealthBoost: case SpeedBoost: {							// 
		                                                                                        // 
									if(weapons.get(j).getId() != ID.Bomb) {                     // check if weapon is not a bomb

										if(!(GuardEnemy.getGuardedList().contains(objects.get(i)))) {				// check if item is not guarded
											                                                                        // 
											score += 20;                                                            // increase score by 20
											player.setAmmo(Math.min(player.getMaxAmmo(), player.getAmmo() + 1));    // increment player ammo
											player.getCollected().add(objects.get(i));								// add item to collected list
											                                                                        // 
										}                                                                           // 

									}															// 
									                                                            // 
									break;                                                      // 
								                                                                // 
								} case BounceEnemy: case FlipEnemy: case HorizontalEnemy: 		// case: enemies and weapon boosts
								  case VerticalEnemy: case SmallEnemy: case BigEnemy:           // 
								  case SeekEnemy: case MissleEnemy: case BombEnemy:             // 
								  case GuardEnemy: case DamageBoost: case AmmoBoost: {          // 
																								// 
									score += 10;												// increase score by 10
									objects.get(i).setHealth(Math.max(0,						// decrease object health by weapon damage
											objects.get(i).getHealth()             				// 
											- weapons.get(j).getDamage()));                     // 
									objects.get(i).hit();										// call hit method on object
									                                                            // 
									if(objects.get(i).isDead()) {                               // check if object is dead
										                                                        // 
										player.getCollected().add(objects.get(i));              // add object to collected list
										                                                        // 
									}                                                           // 
		                                                                                        // 
									if(weapons.get(j).getId() == ID.Bomb) {                     // check if weapon is a bomb
										                                                        // 
										weapons.get(j).isDead();                                // detonate bomb
										                                                        // 
									}                                                           // 
									                                                            // 
									break;                                                      // 
									                                                            // 
								} default: break;                                               // 
								                                                                // 
								}                                                               // 
								                                                                // 
								if(weapons.get(j).getId() != ID.Bomb) {                         // check if weapon is not a bomb
									                                                            // 
									weapons.remove(j);                                          // remove missle from weapon list
								                                                                // 
								}                                                               // 
								                                                                // 
							}                                                                   // 
							                                                                    // 
						}                                                                       // 
						                                                                        // 
					}                                                                           // 
					                                                                            // 
				}                                                                               // 
				                                                                                // 
				for(GameObject i : player.getCollected()) {                                     // for each object in collected list
					                                                                            // 
					switch(i.getId()) {                                                         // switch object ID
					                                                                            // 
					case Coin: {                                                                // case: coin
						                                                                        // 
						coins.remove(i);                                                        // remove coin from coin list
						score += i.getHealth() + 50;                                            // increase score by coin value + 50
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 2
								player.getHealth() + 2));                                       // 
						break;                                                                  // 
						                                                                        // 
					} case LifeToken: {                                                         // case: life token
						                                                                        // 
						score += 500;                                                           // increase score by 500
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 20
								player.getHealth() + 20));                                      // 
						player.setLives(player.getLives() + 1);                                 // increment player lives
						break;                                                                  // 
						                                                                        // 
					} case HealthToken: {                                                       // case: health token
						                                                                        // 
						score += 250;                                                           // increase score by 250
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by token value
								player.getHealth() + i.getHealth()));                           // 
						break;                                                                  // 
		                                                                                        // 
					} case AmmoToken: {                                                         // case: ammo token
						                                                                        // 
						score += 250;                                                           // increase score by 250
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 10
								player.getHealth() + 10));                                      // 
						player.setAmmo(Math.min(player.getMaxAmmo(),                            // increase player ammo by token value
								player.getAmmo() + i.getHealth()));                             // 
						break;                                                                  // 
						                                                                        // 
					} case SpeedUp: {                                                           // case: speed up
						                                                                        // 
						score += 250;                                                           // increase score by 250
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 10
								player.getHealth() + 10));                                      // 
						player.setSpeedUp(player.getSpeedUp()                                   // set player speed up to power up value
								+ i.getHealth() * 60);                                          // 
						player.getPowerUp().add(i.getId());                                     // add speed up to player power up list
						break;                                                                  // 
						                                                                        // 
					} case SlowMo: {                                                            // case: slow mo
						                                                                        // 
						score += 250;                                                           // increase score by 250
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 10
								player.getHealth() + 10));                                      // 
						player.setSlowMo(player.getSlowMo()                                     // set player slow mo to power up value
								+ i.getHealth() * 60);                                          // 
						player.getPowerUp().add(i.getId());                                     // add slow mo to player power up list
						break;                                                                  // 
						                                                                        // 
					} case Freeze: {                                                            // case: freeze
						                                                                        // 
						score += 250;                                                           // increase score by 250
						player.setHealth(Math.min(player.getMaxHealth(),                        // increase player health by 10
								player.getHealth() + 10));                                      // 
						player.setFreeze(player.getFreeze()                                     // set player freeze to power up value
								+ i.getHealth() * 60);                                          // 
						player.getPowerUp().add(i.getId());                                     // add freeze to player power up list
						break;                                                                  // 
						                                                                        // 
					} case Invincibility: {                                                     // case: invincibility
						                                                                        // 
						score += 1000;                                                          // increase score by 1000
						player.setInvincibility(player.getInvincibility()                       // set player invincibility to power up value
								+ i.getHealth() * 60);                                          // 
						player.getPowerUp().add(i.getId());                                     // add invincibility to player power up list
						break;                                                                  // 
		                                                                                        // 
					} case HealthBoost: {                                                       // case: health boost
						                                                                        // 
						if(player.getHealthBoost() < 8) {                                       // check if player health boost is less then 8 (max)
							                                                                    // 
							player.setHealthBoost(player.getHealthBoost() + 1);                 // increament player health boost
							player.setMaxHealth(player.getMaxHealth() + 50);                    // increase player max health by 50
							player.getPowerUp().add(i.getId());                                 // add health boost to player power up list
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						score += 2000;                                                          // increase score by 2000
						player.setHealth(player.getMaxHealth());                                // set player health to max health
						break;                                                                  // 
		                                                                                        // 
					} case SpeedBoost: {                                                        // case: speed boost
						                                                                        // 
						if(player.getSpeedBoost() < 8) {                                        // check if player speed boost is less then 8 (max)
							                                                                    // 
							player.setSpeedBoost(player.getSpeedBoost() + 1);                   // increament player speed boost
							player.getPowerUp().add(i.getId());                                 // add health boost to player power up list
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						score += 2000;                                                          // increase score by 2000
						player.setHealth(Math.min(player.getMaxHealth(),						// increase player health by 10
								player.getHealth() + 10));										// 
						break;                                                                  // 
						                                                                        // 
					} case DamageBoost: {                                                       // case: damage boost
						                                                                        // 
						if(player.getDamageBoost() < 8) {                                       // check if player damage boost is less then 8 (max)
							                                                                    // 
							player.setDamageBoost(player.getDamageBoost() + 1);                 // increament player health boost
							player.getPowerUp().add(i.getId());                                 // add health boost to player power up list
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						score += 2000;                                                          // increase score by 2000
						player.setHealth(Math.min(player.getMaxHealth(),						// increase player health by 10
								player.getHealth() + 10));										// 
						break;                                                                  // 
						                                                                        // 
					} case AmmoBoost: {                                                         // case: ammo boost
						                                                                        // 
						if(player.getAmmoBoost() < 8) {                                         // check if player ammo boost is less then 8 (max)
							                                                                    // 
							player.setAmmoBoost(player.getAmmoBoost() + 1);                     // increament player ammo boost
							player.setMaxAmmo(player.getMaxAmmo() + 50);                        // increase player max ammo by 50
							player.getPowerUp().add(i.getId());                                 // add ammo boost to player power up list
							                                                                    // 
						}                                                                       // 
						                                                                        // 
						score += 2000;                                                          // increase score by 2000
						player.setAmmo(player.getMaxAmmo());                                    // set player amoo to max ammo
						player.setHealth(Math.min(player.getMaxHealth(),						// increase player health by 10
								player.getHealth() + 10));										// 
						break;                                                                  // 
		                                                                                        // 
					} case BounceEnemy: case FlipEnemy: case HorizontalEnemy:                   // case: enemies
					  case VerticalEnemy: case SmallEnemy: case BigEnemy:                       // 
					  case SeekEnemy: case MissleEnemy: case BombEnemy:                         // 
					  case GuardEnemy: {                                                        // 
						                                                                        // 
						score += 100;                                                           // increase score by 100
						player.setHealth(Math.min(player.getMaxHealth(),						// increase player health by 5
								player.getHealth() + 5));										// 
						player.setAmmo(Math.min(player.getMaxAmmo(), player.getAmmo() + 10));   // increase player ammo by 10
						break;                                                                  // 
						                                                                        // 
					} default: break;                                                           // 
					                                                                            // 
					}                                                                           // 
					                                                                            // 
					player.hit(i.getColor());                                                   // call colored hit method on player
					objects.remove(i);                                                          // remove object from object list
					                                                                            // 
				}                                                                               // 
				                                                                                // 
				player.getCollected().clear();                                                  // clear collected list
				if(score >= highScore) highScore = score;                                       // update highscore
				                                                                                // 
				if(r.nextInt(1500) == 0) {                                                      // randomize spawn time for tokens and power ups
					                                                                            // 
					switch(r.nextInt(7)) {                                                      // randomize item spawn
					                                                                            // 
					case 0: {                                                                   // 
		                                                                                        // 
						LifeToken lifeToken = new LifeToken(                                    // spawn life token
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								6, player);                                                     // 
						                                                                        // 
						objects.add(lifeToken);                                                 // add life token to objects
						break;                                                                  // 
		                                                                                        // 
					} case 1: {                                                                 // 
		                                                                                        // 
						HealthToken healthToken = new HealthToken(                              // spawn health token
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								20);                                                            // 
						                                                                        // 
						objects.add(healthToken);                                               // add health token to objects
						break;                                                                  // 
		                                                                                        // 
					} case 2: {                                                                 // 
						                                                                        // 
						AmmoToken ammoToken = new AmmoToken(                                    // spawn ammo token
								100 + r.nextInt(Game.WIDTH - 200),                              // l
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								20, player);                                                    // 
						                                                                        // 
						objects.add(ammoToken);                                                 // add ammo token to objects
						break;                                                                  // 
		                                                                                        // 
					} case 3: {                                                                 // 
		                                                                                        // 
						SpeedUp speedUp = new SpeedUp(                                          // spawn speed up
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								20);                                                            // 
																								// 
						objects.add(speedUp);                                                   // add speed up to objects
						                                                                        // 
						break;                                                                  // 
						                                                                        // 
					} case 4: {                                                                 // 
		                                                                                        // 
						SlowMo slowMo = new SlowMo(                                             // spawn slow mo
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								20);                                                            // 
						                                                                        // 
						objects.add(slowMo);                                                    // add slow mo to objects
						break;                                                                  // 
						                                                                        // 
					} case 5: {                                                                 // 
						                                                                        // 
						Freeze freeze = new Freeze(                                             // spawn freeze
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								20);                                                            // 
						                                                                        // 
						objects.add(freeze);                                                    // add freeze to objects
						break;                                                                  // 
		                                                                                        // 
					} case 6: {                                                                 // 
						                                                                        // 
						Invincibility invincibility = new Invincibility(                        // spawn invincibility
								100 + r.nextInt(Game.WIDTH - 200),                              // 
								100 + r.nextInt(Game.HEIGHT - 200),                             // 
								6, player);                                                     // 
						                                                                        // 
						objects.add(invincibility);                                             // add invincibility to objects
						break;                                                                  // 
						                                                                        // 
					} default: break;                                                           // 
					                                                                            // 
					}                                                                           // 
					                                                                            // 
				}                                                                               // 
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
		}                                                                                       // 
	                                                                                            // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Draw game
	 */
	private void render() {                                                                     // 
		                                                                                        // 
		BufferStrategy bs = this.getBufferStrategy();                                           // get buffer strategy
		                                                                                        // 
		if(bs == null) {                                                                        // check for null buffer strategy
			                                                                                    // 
			this.createBufferStrategy(3);                                                       // create buffer strategy
			return;                                                                             // 
			                                                                                    // 
		}                                                                                       // 
		                                                                                        // 
		Graphics g = bs.getDrawGraphics();                                                      // create graphics context
		                                                                                        // 
		g.setColor(Color.BLACK);                                                                // 
		g.fillRect(0, 0, WIDTH, HEIGHT);                                                        // draw background
		                                                                                        // 
                                                                                                // 
		if(gameLegend) {                                                                        // check if game in legend state
			                                                                                    // 
			legend.render(g);                                                                   // renger legend
			                                                                                    // 
		} else if(gameStart) {                                                                  // check if game in start state
                                                                                                // 
			g.setColor(Color.WHITE);                                                            // render start screen
			g.setFont(new Font("Impact", Font.PLAIN, 24));                                      // 
			g.drawString("HIGHSCORE = " + Integer.toString(highScore),                          // 
					(Game.WIDTH - g.getFontMetrics(g.getFont()).stringWidth(                    // 
							"HIGHSCORE = " + Integer.toString(highScore)))/2, HEIGHT/5);        // 
			                                                                                    // 
			g.fillRect(0, HEIGHT/3, WIDTH, HEIGHT/3);                                           // 
			                                                                                    // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));              // 
			g.setFont(new Font("Impact", Font.BOLD, 120));                                      // 
			g.drawString("RANDOM", (Game.WIDTH - g.getFontMetrics(                              // 
					g.getFont()).stringWidth("RANDOM"))/2, HEIGHT/2 + 60);                      // 
			                                                                                    // 
			g.setColor(Color.WHITE);                                                            // 
			g.setFont(new Font("Impact", Font.PLAIN, 24));                                      // 
			g.drawString("L = LEGEND", (Game.WIDTH -                                            // 
					g.getFontMetrics(g.getFont()).stringWidth("L = LEGEND"))/2, 570);           // 
			g.drawString("SPACEBAR = START", (Game.WIDTH - g.getFontMetrics(                    // 
					g.getFont()).stringWidth("SPACEBAR = START"))/2, HEIGHT * 4/5);             // 
			                                                                                    // 
		} else {                                                                                // 
			                                                                                    // 
			if(!gameOver && !gameLegend) {                                                      // check if game not in over or legend state
	                                                                                            // 
				for(int i = 0; i < objects.size(); i++) {                                       // for each object in object list
					                                                                            // 
					objects.get(i).render(g);                                                   // render object
					                                                                            // 
				}                                                                               // 
				                                                                                // 
				for(int i = 0; i < weapons.size(); i++) {                                       // for each weapon in weapon list
					                                                                            // 
					weapons.get(i).render(g);                                                   // render weapon
					                                                                            // 
				}                                                                               // 
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
			if(!gameLegend) hud.render(g);														// render hud if game not in legend state
		}                                                                                       // 
		                                                                                        // 
		g.dispose();                                                                            // dispose of graphics context
		bs.show();                                                                              // show buffer
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	                                                                                            // 
	                                                                                            // 
	/**
	 * spawn coins, enemies, and upgrades
	 */
	public void spawn() {                                                                       // 
                                                                                                // 
		Coin defaultCoin = new Coin(                                                            // (default coin used to prevent level 1 from being skipped)
				100 + r.nextInt(Game.WIDTH - 200),                                              // 
				100 + r.nextInt(Game.HEIGHT - 200),                                             // 
				20);                                                                            // 
		                                                                                        // 
		objects.add(defaultCoin);                                                               // 
		coins.add(defaultCoin);                                                                 // 
		                                                                                        // 
		for(int i = 0; i < level/2; i++) {                                                      // for level number divided by 2
			                                                                                    // 
			objects.remove(defaultCoin);                                                        // remove default coin from object list
			coins.remove(defaultCoin);                                                          // remove default coin from coin list
			                                                                                    // 
			Coin coin = new Coin(                                                               // spawn coin
					100 + r.nextInt(Game.WIDTH - 200),                                          // 
					100 + r.nextInt(Game.HEIGHT - 200),                                         // 
					20);                                                                        // 
			                                                                                    // 
			objects.add(coin);                                                                  // add coin to object list
			coins.add(coin);                                                                    // add coin to coin list
			                                                                                    // 
			switch(r.nextInt(10)) {                                                             // randomize enemy spawn
			                                                                                    // 
			case 0: {                                                                           // 
				                                                                                // 
				BounceEnemy bounceEnemy = new BounceEnemy(                                      // spawn bounce enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24);                                                                    // 
				                                                                                // 
				objects.add(bounceEnemy);                                                       // add bounce enemy to object list
				break;                                                                          // 
			                                                                                    // 
			} case 1: {                                                                         // 
				                                                                                // 
				FlipEnemy flipEnemy = new FlipEnemy(                                            // spawn flip enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24);                                                                    // 
				                                                                                // 
				objects.add(flipEnemy);                                                         // add flip enemy to object list
				break;                                                                          // 
                                                                                                // 
			} case 2: {                                                                         // 
				                                                                                // 
				HorizontalEnemy horizontalEnemy = new HorizontalEnemy(                          // spawn horizontal enemy
						50 + r.nextInt(Game.WIDTH - 200),                                       // 
						100 + r.nextInt(Game.HEIGHT/2 - 100),                                   // 
						24);                                                                    // 
				                                                                                // 
				objects.add(horizontalEnemy);                                                   // add horizontal enemy to object list
				break;                                                                          // 
			                                                                                    // 
			} case 3: {                                                                         // 
				                                                                                // 
				VerticalEnemy verticalEnemy = new VerticalEnemy(                                // spawn vertical enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24);                                                                    // 
				                                                                                // 
				objects.add(verticalEnemy);                                                     // add vertical enemy to object list
				break;                                                                          // 
				                                                                                // 
			} case 4: {                                                                         // 
				                                                                                // 
				SmallEnemy smallEnemy = new SmallEnemy(                                         // spawn small enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24);                                                                    // 
				                                                                                // 
				objects.add(smallEnemy);                                                        // add small enemy to object list
				break;                                                                          // 
                                                                                                // 
			} case 5: {                                                                         // 
				                                                                                // 
				BigEnemy bigEnemy = new BigEnemy(                                               // spawn big enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24);                                                                    // 
				                                                                                // 
				objects.add(bigEnemy);                                                          // add big enemy to object list
				break;                                                                          // 
                                                                                                // 
			} case 6: {                                                                         // 
				                                                                                // 
				SeekEnemy seekEnemy = new SeekEnemy(                                            // spawn seek enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24,																		//
						player);                                                       	        // 
				                                                                                // 
				objects.add(seekEnemy);                                                         // add seek enemy to object list
				break;                                                                          // 
                                                                                                // 
			} case 7: {                                                                         // 
				                                                                                // 
				MissleEnemy missleEnemy = new MissleEnemy(                                      // spawn missle enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24,                                                                     // 
						player);                                                                // 
				                                                                                // 
				objects.add(missleEnemy);                                                       // add missle enemy to object list
				break;                                                                          // 
																								// 
			} case 8: {                                                                         // 
				                                                                                // 
				BombEnemy bombEnemy = new BombEnemy(                                            // spawn bomb enemy
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						24,                                                                     // 
						player);                                                                // 
				                                                                                // 
				objects.add(bombEnemy);                                                         // add bomb enemy to object list
				break;                                                                          // 
				                                                                                // 
			} case 9: {                                                                         // 
				                                                                                // 
				GuardEnemy guardEnemy = new GuardEnemy(                                         // spawn guard enemy
						coin.getX() - 12,                                                       // 
						coin.getY(),                                                            // 
						48,                                                                     // 
						coin);                                                                  // 
				                                                                                // 
				objects.add(guardEnemy);                                                        // add guard enemy to object list
				break;                                                                          // 
                                                                                                // 
			} default: break;                                                                   // 
			                                                                                    // 
			}                                                                                   // 
			                                                                                    // 
		}                                                                                       // 
		                                                                                        // 
		if(level >= 10 && r.nextInt(3) == 0) {                                                  // check if level is greater than 10 and randomize upgrade spawn chance
			                                                                                    // 
			switch(r.nextInt(4)) {                                                              // randomize upgrade spawn
                                                                                                // 
			case 0: {                                                                           // 
				                                                                                // 
				HealthBoost healthBoost = new HealthBoost(                                      // spawn health boost
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						20,																		//
						player);                                                           	    // 
				                                                                                // 
				objects.add(healthBoost);                                                       // add health boost to object list
				break;                                                                          // 
				                                                                                // 
			} case 1: {                                                                         // 
				                                                                                // 
				SpeedBoost speedBoost = new SpeedBoost(                                         // spawn speed boost
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						20);                                                                    // 
				                                                                                // 
				objects.add(speedBoost);                                                        // add speed boost to object list
				break;                                                                          // 
                                                                                                // 
			} case 2: {                                                                         // 
				                                                                                // 
				DamageBoost damageBoost = new DamageBoost(                                      // spawn damage boost
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						20, player);                                                            // 
				                                                                                // 
				objects.add(damageBoost);                                                       // add damage boost to object list
				break;                                                                          // 
                                                                                                // 
			} case 3: {                                                                         // 
				                                                                                // 
				AmmoBoost ammoBoost = new AmmoBoost(                                            // spawn ammo boost
						100 + r.nextInt(Game.WIDTH - 200),                                      // 
						100 + r.nextInt(Game.HEIGHT - 200),                                     // 
						20, player);                                                            // 
				                                                                                // 
				objects.add(ammoBoost);                                                         // add ammo boost to object list
				break;                                                                          // 
                                                                                                // 
			} default: break;                                                                   // 
			                                                                                    // 
			}                                                                                   // 
			                                                                                    // 
		}                                                                                       // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
                                                                                                // 
	                                                                                            // 
	/**
	 * Toggle game pause state
	 */
	public void pause() {                                                                       // 
		                                                                                        // 
		if(!(gameSpawn)) {                                                                      // check if game not in spawn state
			                                                                                    // 
			if(!gamePaused) {                                                                   // check if game not in paused state
				                                                                                // 
				pauseStartTime = (-0.001                                                        // set pause start time to current time
						* (startTime - System.currentTimeMillis()));                            // 
				                                                                                // 
			} else {                                                                            // 
				                                                                                // 
				gameLegend = false;                                         			        // set legend state to false
				pauseTime = pauseTime + (-0.001 * (startTime                                    // update total game pause time
						- System.currentTimeMillis())                                           // 
						- pauseStartTime);                                                      // 
				                                                                                // 
			}                                                                                   // 
			                                                                                    // 
			gamePaused = !gamePaused;                                                           // toggle game pause state
			                                                                                    // 
		}                                                                                       // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Toggle game legend state
	 */
	public void legend() {                                                                      // 
		                                                                                        // 
		gameLegend = !gameLegend;                                                               // toggle game legend state
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	/**
	 * Initialize game
	 */
	public void reStart() {                                                                     // 
                                                                                                // 
		objects.clear();                                                                        // clear object list
		weapons.clear();                                                                        // clear weapon list
		coins.clear();                                                                          // clear coin list
		level = 1;                                                                              // initialize level
		score = 0;                                                                              // initialize score
		startTime = System.currentTimeMillis();                                                 // initialize start time
		time = 0;                                                                               // initialize game time
		pauseTime = 0;                                                                          // initialize pause time
		pauseStartTime = 0;                                                                     // initialize pause start time
		gameStart = false;                                                                      // set game start state to false
		gameOver = false;                                                                       // set game over state to false
		                                                                                        // 
		player = new Player(Game.WIDTH/2 - 24,                                                  // initialize player
				Game.HEIGHT/2 - 24,                                                             // 
				48);                                                                            // 
		                                                                                        // 
		objects.add(player);                                                                    // add player to object list
		spawn();                                                                                // spawn default coin
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	                                                                                            // 
	                                                                                            // 
	public Hud getHud() {                                                                       // 
		                                                                                        // 
		return hud;                                                                             // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public Legend getLegend() {                                                                 // 
		                                                                                        // 
		return legend;                                                                          // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public Player getPlayer() {                                                                 // 
		                                                                                        // 
		return player;                                                                          // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public LinkedList<GameObject> getObjects() {                                                // 
		                                                                                        // 
		return objects;                                                                         // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public LinkedList<Weapon> getWeapons() {                                                    // 
		                                                                                        // 
		return weapons;                                                                         // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public LinkedList<Coin> getCoins() {                                                        // 
		                                                                                        // 
		return coins;                                                                           // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public int getScore() {                                                                     // 
		                                                                                        // 
		return score;                                                                           // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public void setScore(int score) {                                                           // 
		                                                                                        // 
		this.score = score;                                                                     // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public int getHighScore() {                                                                 // 
		                                                                                        // 
		return highScore;                                                                       // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public int getLevel() {                                                                     // 
		                                                                                        // 
		return level;                                                                           // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public int getTime() {                                                                      // 
		                                                                                        // 
		return time;                                                                            // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public int getCounter() {                                                                   // 
		                                                                                        // 
		return counter;                                                                         // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public boolean getGameStart() {                                                             // 
		                                                                                        // 
		return gameStart;                                                                       // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public boolean getGameSpawn() {                                                             // 
		                                                                                        // 
		return gameSpawn;                                                                       // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public boolean getGamePaused() {                                                            // 
		                                                                                        // 
		return gamePaused;																	    // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
	public boolean getGameLegend() {                                                            // 
		                                                                                        // 
		return gameLegend;                                                                      // 
		                                                                                        // 
	}                                                                                           // 
	                                                                                            // 
	public boolean getGameOver() {                                                              // 
		                                                                                        // 
		return gameOver;                                                                        // 
		                                                                                        // 
	}                                                                                           // 
                                                                                                // 
}                                                                                               // 
