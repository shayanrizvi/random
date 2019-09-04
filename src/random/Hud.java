package random;

import java.awt.*;
import java.util.Random;

/**
 * 
 * This class defines the heads up display functionality.
 * 
 * @author Shayan
 *
 */
public class Hud {
	
	// Create variables
	private Game game;                                                                                                   // game
	private int healthGreenValue;																						 // health green value
	private int healthRedValue;                                                                                          // health red value
	private Color healthColor;                                                                                           // health color
	private int ammoBlueValue;                                                                                           // ammo blue value
	private int ammoRedValue;                                                                                            // ammo red value
	private Color ammoColor;                                                                                             // ammo color
	private Random r = new Random();                                                                                     // random 
	                                                                                                                     // 
	/**
	 * Constructs the heads up display
	 * 
	 * @param game reference to the game for object variable access
	 */
	public Hud(Game game) {                                                                                              // 
		                                                                                                                 // 
		this.game = game;                                                                                                // copy game reference
		                                                                                                                 // 
	}                                                                                                                    // 
	                                                                                                                     // 
	/**
	 * Update HUD
	 */
	public void tick() {                                                                                                 // 
		                                                                                                                 // 
		healthGreenValue = 255 * game.getPlayer().getHealth() / game.getPlayer().getMaxHealth();                         // calculate health green value
		healthRedValue = 255 - healthGreenValue;                                                                         // calculate health red value
		                                                                                                                 // 
		ammoBlueValue = 255 * game.getPlayer().getAmmo() / game.getPlayer().getMaxAmmo();                                // calculate ammo blue value
		ammoRedValue = 255 - ammoBlueValue;                                                                              // calculate ammo red value
		                                                                                                                 // 
		if(game.getPlayer().getInvincibility() > 0) {                                                                    // check if player has invincibility
                                                                                                                         // 
			healthColor = game.getPlayer().getColor();                                                                   // set health color to player color
			ammoColor = game.getPlayer().getColor();                                                                     // set ammo color to player color
			                                                                                                             // 
		} else {                                                                                                         // 
			                                                                                                             // 
			healthColor = new Color(healthRedValue, healthGreenValue, 0);                                                // set health color
			ammoColor = new Color(ammoRedValue, 0, ammoBlueValue);                                                       // set ammo color
			                                                                                                             // 
		}                                                                                                                // 
		                                                                                                                 // 
	}                                                                                                                    // 
	                                                                                                                     // 
	/**
	 * Draw HUD
	 * 
	 * @param g	graphics context
	 */
	public void render(Graphics g) {                                                                                     // 
		                                                                                                                 // 
		if(game.getGameOver()) {                                                                                         // check if game in over state
                                                                                                                         // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                                       // render game over screen
			g.setFont(new Font("Impact", Font.BOLD, 50));                                                                // 
			g.drawString("GAME OVER", (Game.WIDTH                                                                        // 
					- g.getFontMetrics(g.getFont()).stringWidth("GAME OVER"))/2, Game.HEIGHT/4);                         // 
			g.setColor(Color.WHITE);                                                                                     // 
			g.drawString("TIME: " + game.getTime(), 100 + Game.WIDTH/2                                                   // 
					- g.getFontMetrics(g.getFont()).stringWidth("TIME: "), Game.HEIGHT/4 + 100);                         // 
			g.drawString("PLAYER SCORE: " + game.getScore(), 100 + Game.WIDTH/2                                          // 
					- g.getFontMetrics(g.getFont()).stringWidth("PLAYER SCORE: "), Game.HEIGHT/4 + 150);                 // 
			g.drawString("HIGH SCORE: " + game.getHighScore(), 100 + Game.WIDTH/2                                        // 
					- g.getFontMetrics(g.getFont()).stringWidth("HIGH SCORE: "), Game.HEIGHT/4 + 200);                   // 
			g.setFont(new Font("Impact", Font.ITALIC, 25));                                                              // 
			g.drawString("L = LEGEND", (Game.WIDTH                                                                       // 
					- g.getFontMetrics(g.getFont()).stringWidth("L = LEGEND"))/2, Game.HEIGHT/4 + 250);                  // 
			g.drawString("SPACEBAR  =  PLAY AGAIN", (Game.WIDTH                                                          // 
					- g.getFontMetrics(g.getFont()).stringWidth("SPACEBAR  =  PLAY AGAIN"))/2, Game.HEIGHT/4 + 300);     // 
			                                                                                                             // 
		} else {                                                                                                         // 
			                                                                                                             // 
			if(game.getGameSpawn()) {                                                                                    // check if game in spawn state
                                                                                                                         // 
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                                   // render spawn screen
				g.setFont(new Font("Impact", Font.PLAIN, 80));                                                           // 
				g.drawString("LEVEL", -50 + (Game.WIDTH                                                                  // 
						- g.getFontMetrics(g.getFont()).stringWidth("LEVEL"))/2,                                         // 
						(Game.HEIGHT)/2 - 100);                                                                          // 
				g.drawString(Integer.toString(game.getLevel()), 100 + (Game.WIDTH                                        // 
						- g.getFontMetrics(g.getFont()).stringWidth(Integer.toString(game.getLevel())))/2,               // 
						(Game.HEIGHT)/2 - 100);                                                                          // 
				g.setColor(Color.BLACK);                                                                                 // 
				g.setFont(new Font("Impact", Font.BOLD, 100));                                                           // 
				g.drawString(Integer.toString(game.getCounter()), (Game.WIDTH                                            // 
						- g.getFontMetrics(g.getFont()).stringWidth(Integer.toString(game.getCounter())))/2,             // 
						(Game.HEIGHT)/2 + 45);                                                                           // 
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                                   // 
				g.setFont(new Font("Impact", Font.PLAIN, 90));                                                           // 
				g.drawString(Integer.toString(game.getCounter()), 2 + (Game.WIDTH                                        // 
						- g.getFontMetrics(g.getFont()).stringWidth(Integer.toString(game.getCounter())))/2,             // 
						(Game.HEIGHT)/2 + 40);                                                                           // 
				                                                                                                         // 
			} else if(game.getGamePaused()) {                                                                            // check if game in paused state
			                                                                                                             // 
				g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                                   // render paused screen
				g.setFont(new Font("Impact", Font.BOLD, 50));                                                            // 
				g.drawString("PAUSED", (Game.WIDTH                                                                       // 
						- g.getFontMetrics(g.getFont()).stringWidth("PAUSED"))/2, 200);                                  // 
				g.setColor(Color.WHITE);                                                                                 // 
				g.drawString("TIME: " + game.getTime(), 100 + Game.WIDTH/2                                               // 
						- g.getFontMetrics(g.getFont()).stringWidth("TIME: "), 300);                                     // 
				g.drawString("PLAYER SCORE: " + game.getScore(), 100 + Game.WIDTH/2                                      // 
						- g.getFontMetrics(g.getFont()).stringWidth("PLAYER SCORE: "), 350);                             // 
				g.drawString("HIGH SCORE: " + game.getHighScore(), 100 + Game.WIDTH/2                                    // 
						- g.getFontMetrics(g.getFont()).stringWidth("HIGH SCORE: "), 400);                               // 
				g.setFont(new Font("Impact", Font.ITALIC, 25));                                                          // 
				g.drawString("L = LEGEND", (Game.WIDTH                                                                   // 
						- g.getFontMetrics(g.getFont()).stringWidth("L = LEGEND"))/2, 500);                              // 
				g.drawString("P = UNPAUSE", (Game.WIDTH                                                                  // 
						- g.getFontMetrics(g.getFont()).stringWidth("P = UNPAUSE"))/2, 550);                             // 
				                                                                                                         // 
			}                                                                                                            // 
			                                                                                                             // 
			if(!game.getGameLegend()) {                                                                                  // check if game not in legend state
				                                                                                                         // 
				g.setColor(Color.WHITE);                                                                                 // render HUD
				g.fillRect(0, 0, Game.WIDTH, 50);                                                                        // 
																														 // 
				g.setColor(Color.BLACK);                                                                                 // 
				g.fillRect(4, 4, Game.WIDTH - 8, 42);                                                                    // 
				                                                                                                         // 
				g.setFont(new Font("Consolas", Font.BOLD,  20));                                                         // 
				g.setColor(healthColor);                                                                                 // 
				g.drawString("Health", 10, 22);                                                                          // 
				g.drawString(Integer.toString(game.getPlayer().getHealth()), 100, 22);                                   // 
				g.fillRect(165, 5, (Game.WIDTH - 330) *                                                                  // 
						game.getPlayer().getHealth()/game.getPlayer().getMaxHealth() , 19);                              // 
				g.setColor(game.getPlayer().getColor());                                                                 // 
				g.drawString("Lives", Game.WIDTH - 120, 22);                                                             // 
				g.drawString(Integer.toString(game.getPlayer().getLives()), Game.WIDTH - 40, 22);                        // 
				                                                                                                         // 
				g.setColor(ammoColor);                                                                                   // 
				g.drawString("Ammo", 32, 42);                                                                            // 
				g.drawString(Integer.toString(game.getPlayer().getAmmo()), 100, 42);                                     // 
				g.fillRect(165, 26, (Game.WIDTH - 330) *                                                                 // 
						game.getPlayer().getAmmo()/game.getPlayer().getMaxAmmo(), 19);                                   // 
				g.setColor(Color.YELLOW);                                                                                // 
				g.drawString("Coins", Game.WIDTH - 120, 42);                                                             // 
				g.drawString(Integer.toString(game.getCoins().size()), Game.WIDTH - 40, 42);                             // 
				                                                                                                         // 
				g.setColor(Color.WHITE);                                                                                 // 
				g.drawString("Score", 20, 67);                                                                           // 
				g.drawString(Integer.toString(game.getScore()), 100, 67);                                                // 
				g.drawString("Level", Game.WIDTH - 120, 67);                                                             // 
				g.drawString(Integer.toString(game.getLevel()), Game.WIDTH - 40, 67);                                    // 
				g.drawString("FPS = " + Game.frames, 20, Game.HEIGHT - 20);                                              // 
				g.drawString("P = PAUSE", Game.WIDTH - 120, Game.HEIGHT - 20);                                           // 
				                                                                                                         // 
				g.setFont(new Font("Impact", Font.BOLD, 30));                                                            // 
				g.drawString(Integer.toString(game.getTime()), (Game.WIDTH                                               // 
						- g.getFontMetrics(g.getFont()).stringWidth(Integer.toString(game.getTime())))/2,                // 
						80);                                                                                             // 
				                                                                                                         // 
				if(!(game.getPlayer().getPowerUp().isEmpty())) {                                                         // check if power up list is not empty
																														 // 
					g.setFont(new Font("Impact", Font.BOLD,  20));                                                       // 
					                                                                                                     // 
					for(ID i : game.getPlayer().getPowerUp()) {                                                          // for each power up in power up list
						                                                                                                 // 
						switch(i) {                                                                                      // switch power up ID
						                                                                                                 // 
						case SpeedUp:{                                                                                   // render power up message
							                                                                                             // 
							g.setColor(Color.ORANGE);                                                                    // 
							g.drawString(i + " " + Math.round(game.getPlayer().getSpeedUp() * 0.016),					 //
									Game.WIDTH * 2/5, Game.HEIGHT - 60);        										 // 
							break;                                                                                       // 
	                                                                                                                     // 
						} case SlowMo: {                                                                                 // 
	                                                                                                                     // 
							g.setColor(Color.MAGENTA);                                                                   // 
							g.drawString(i + " " + Math.round(game.getPlayer().getSlowMo() * 0.016),					 //
									Game.WIDTH * 3/5, Game.HEIGHT - 60);        										 // 
							break;                                                                                       // 
						                                                                                                 // 
						} case Freeze: {                                                                                 // 
							                                                                                             // 
							g.setColor(Color.CYAN);                                                                      // 
							g.drawString(i + " " + Math.round(game.getPlayer().getFreeze() * 0.016),					 //
									Game.WIDTH * 4/5, Game.HEIGHT - 60);       										     // 
							break;                                                                                       // 
	                                                                                                                     // 
						} case Invincibility: {                                                                          // 
	                                                                                                                     // 
							g.setColor(game.getPlayer().getColor());                                                     // 
							g.drawString(i + " " + Math.round(game.getPlayer().getInvincibility() * 0.016),				 //
									Game.WIDTH * 1/5, Game.HEIGHT - 60);   												 // 
							break;                                                                                       // 
	                                                                                                                     // 
						} case HealthBoost: {                                                                            // 
							                                                                                             // 
							g.setColor(Color.GREEN);                                                                     // 
							                                                                                             // 
							if(game.getPlayer().getHealthBoost() == 8) {                                                 // 
								                                                                                         // 
								g.drawString(i + " MAX", Game.WIDTH / 5, Game.HEIGHT - 40);                              // 
								                                                                                         // 
							} else {                                                                                     // 
								                                                                                         // 
								g.drawString(i + " " + game.getPlayer().getHealthBoost(),								 //
										Game.WIDTH / 5, Game.HEIGHT - 40);                                               // 
								                                                                                         // 
							}                                                                                            // 
							                                                                                             // 
							break;                                                                                       // 
	                                                                                                                     // 
						} case SpeedBoost: {                                                                             // 
							                                                                                             // 
							g.setColor(Color.ORANGE);                                                                    // 
	                                                                                                                     // 
							if(game.getPlayer().getSpeedBoost() == 8) {                                                  // 
								                                                                                         // 
								g.drawString(i + " MAX", Game.WIDTH / 5 * 2, Game.HEIGHT - 40);                          // 
								                                                                                         // 
							} else {                                                                                     // 
								                                                                                         // 
								g.drawString(i + " " + game.getPlayer().getSpeedBoost(),								 //
										Game.WIDTH / 5 * 2, Game.HEIGHT - 40);                     						 // 
							                                                                                             // 
							}                                                                                            // 
							                                                                                             // 
							break;                                                                                       // 
	                                                                                                                     // 
						} case DamageBoost: {                                                                            // 
							                                                                                             // 
							g.setColor(Color.RED);                                                                       // 
	                                                                                                                     // 
							if(game.getPlayer().getDamageBoost() == 8) {                                                 // 
								                                                                                         // 
								g.drawString(i + " MAX", Game.WIDTH / 5 * 3, Game.HEIGHT - 40);                          // 
																														 // 
							} else {                                                                                     // 
								                                                                                         // 
								g.drawString(i + " " + game.getPlayer().getDamageBoost(),								 //
										Game.WIDTH / 5 * 3, Game.HEIGHT - 40);                     						 // 
							                                                                                             // 
							}                                                                                            // 
							                                                                                             // 
							break;                                                                                       // 
						                                                                                                 // 
						} case AmmoBoost: {                                                                              // 
							                                                                                             // 
							g.setColor(Color.BLUE);                                                                      // 
							                                                                                             // 
							if(game.getPlayer().getAmmoBoost() == 8) {                                                   // 
								                                                                                         // 
								g.drawString(i + " MAX", Game.WIDTH / 5 * 4, Game.HEIGHT - 40);                          // 
								                                                                                         // 
							} else {                                                                                     // 
								                                                                                         // 
								g.drawString(i + " " + game.getPlayer().getAmmoBoost(),									 //
										Game.WIDTH / 5 * 4, Game.HEIGHT - 40);                       					 // 
								                                                                                         // 
							}                                                                                            // 
							                                                                                             // 
							break;                                                                                       // 
							                                                                                             // 
						} default: break;                                                                                // 
						                                                                                                 // 
						}                                                                                                // 
						                                                                                                 // 
					}                                                                                                    // 
				                                                                                                         // 
				}                                                                                                        // 
				                                                                                                         // 
			}                                                                                                            // 
			                                                                                                             // 
		}                                                                                                                // 
		                                                                                                                 // 
	}                                                                                                                    // 
	                                                                                                                     // 
}                                                                                                                        //