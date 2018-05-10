package random;

import java.awt.event.*;

/**
 * 
 * Key adapter class for receiving and implementing key events.
 * 
 * @author Shayan
 *
 */
public class KeyInput extends KeyAdapter {
	
	private int key;																			 // key
	private Game game;																			 // game
	private Bomb bomb;																			 // bomb
	private boolean[] keyPressed = new boolean[4];												 // key pressed list (used to prevent sticky keys)
																								 // 
	/**
	 * Constructs a key input handler
	 * 
	 * @param game reference to the game for object variable access
	 */
	public KeyInput(Game game) {																 // 
																								 // 
		this.game = game;																		 // copy game reference
																								 // 
	}                                                                                            // 
	                                                                                             // 
	@Override
	public void keyPressed(KeyEvent e) {                                                         // 
		                                                                                         // 
		key = e.getKeyCode();                                                                    // determine which key is pressed
                                                                                                 // 
		if(key == KeyEvent.VK_ESCAPE) {															 // check if ESC pressed
			                                                                                     // 
			System.exit(0);                                                                      // terminate the program
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(game.getGameStart() || game.getGameOver() || game.getGamePaused()) {                  // check if game in start, over, or paused state
																								 // 
			if(!game.getGamePaused() && !game.getGameLegend()) {                                 // check if game not in paused or legend state
				                                                                                 // 
				if(key == KeyEvent.VK_SPACE) {                                                   // check if spacebar pressed
					                                                                             // 
					game.reStart();                                                              // start the game
					                                                                             // 
				} 																				 // 
				                                                                                 // 
			}                                                                                    // 
                                                                                                 // 
			if(game.getGamePaused()) {                                                           // check if game in paused state
				                                                                                 // 
				if(key == KeyEvent.VK_P) {                                                       // check if P pressed
																								 // 
					game.pause();                                                                // toggle game pause
					                                                                             // 
				}                                                                                // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_L) {                                                           // check if K pressed
				                                                                                 // 
				game.getLegend().setCurrentPage(game.getLegend().getPageList().getFirst());      // set current legend page to first page
				game.legend();                                                                   // toggle game legend state
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(game.getGameLegend()) {                                                           // check if game in legend state
				                                                                                 // 
				if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {                           // check if right arrow key or D pressed
					                                                                             // 
					game.getLegend().setCurrentPage(game.getLegend().getPageList().get(          // display next page
							Math.min(game.getLegend().getPageList().size() - 1,                  // 
									game.getLegend().getPageList().indexOf(                      // 
											game.getLegend().getCurrentPage()) + 1)));           // 
					                                                                             // 
				}                                                                                // 
                                                                                                 // 
				if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {                            // check if left arrow key or A pressed
                                                                                                 // 
					game.getLegend().setCurrentPage(game.getLegend().getPageList().get(          // display previous page
							Math.max(0, game.getLegend().getPageList().indexOf(                  // 
									game.getLegend().getCurrentPage()) - 1)));                   // 
					                                                                             // 
				}                                                                                // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		} else {																				 // 
			                                                                                     // 
			if(key == KeyEvent.VK_W) {                                                           // check if W pressed
				                                                                                 // 
				game.getPlayer().setVelY(-8 - game.getPlayer().getSpeedBoost());                 // move player up
				keyPressed[0] = true;                                                            // set W key pressed to true
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_S) {                                                           // check if S pressed
				                                                                                 // 
				game.getPlayer().setVelY(8 + game.getPlayer().getSpeedBoost());                  // move player down
				keyPressed[1] = true;                                                            // set S key pressed to true
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_A) {                                                           // check if A pressed
				                                                                                 // 
				game.getPlayer().setVelX(-8 - game.getPlayer().getSpeedBoost());                 // move player left
				keyPressed[2] = true;                                                            // set A key pressed to true
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_D) {                                                           // check if D pressed
				                                                                                 // 
				game.getPlayer().setVelX(8 + game.getPlayer().getSpeedBoost());                  // move player right
				keyPressed[3] = true;                                                            // set D key pressed to true
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_P) {                                                           // check if P pressed
				                                                                                 // 
				game.pause();                                                                    // toggle pause game
				                                                                                 // 
			}                                                                                    // 
																								 // 
			if(key == KeyEvent.VK_SPACE) {                                                       // check if spacebar pressed
				                                                                                 // 
				if(!game.getGamePaused() && !game.getGameSpawn()) {                              // check if game not in paused or spawn state
	                                                                                             // 
					if(game.getWeapons().contains(bomb)) {                                       // check if the weapon list contains a live bomb
						                                                                         // 
						bomb.detonate();                                                         // detonate bomb
						                                                                         // 
					} else {                                                                     // 
						                                                                         // 
						if(game.getPlayer().getAmmo() >= 20) {                                   // check if player ammo is greater than or equal to 20
							                                                                     // 
							game.setScore(Math.max(0, game.getScore() - 100));                   // decrease player score by 100
							game.getPlayer().setAmmo(Math.max(0, game.getPlayer().getAmmo()      // decrease player ammo by 20
									- 20));                                                      // 
							                                                                     // 
							bomb = game.getPlayer().dropBomb(                                    // drop bomb
									32 + 3 * game.getPlayer().getDamageBoost(),                  // 
									300,                                                         // 
									4 + game.getPlayer().getDamageBoost()/2);                    // 
							                                                                     // 
							game.getWeapons().add(bomb);                                         // add bomb to weapon list
							                                                                     // 
						}                                                                        // 
						                                                                         // 
					}                                                                            // 
					                                                                             // 
				}                                                                                // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		}                                                                                        // 
                                                                                                 // 
	}                                                                                            // 
																								 // 
	@Override
	public void keyReleased(KeyEvent e) {                                                        // 
		                                                                                         // 
		if(!game.getGameStart()) {                                  					         // check if game not in start state 
			                                                                                     // 
			key = e.getKeyCode();                                                                // determine which key is released
			                                                                                     // 
			if(key == KeyEvent.VK_W) {                                                           // check if W released
				                                                                                 // 
				keyPressed[0] = false;                                                           // set W key pressed to false
				                                                                                 // 
				if(keyPressed[1]) {                                                              // check if S is pressed
					                                                                             // 
					game.getPlayer().setVelY(8 + game.getPlayer().getSpeedBoost());              // move player down
					                                                                             // 
				} else {                                                                         // 
					                                                                             // 
					game.getPlayer().setVelY(0);                                                 // reset player vertical velocity
									                                                             // 
				}                                                                                // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_S) {                                                           // check if S released
				                                                                                 // 
				keyPressed[1] = false;                                                           // set S key pressed to false
				                                                                                 // 
				if(keyPressed[0]) {                                                              // check if W is pressed
					                                                                             // 
					game.getPlayer().setVelY(-8 - game.getPlayer().getSpeedBoost());             // move player up
					                                                                             // 
				} else {                                                                         // 
					                                                                             // 
					game.getPlayer().setVelY(0);                                                 // reset player vertical velocity
									                                                             // 
				}																				 // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			                                                                                     // 
			if(key == KeyEvent.VK_A) {                                                           // check if A released
				                                                                                 // 
				keyPressed[2] = false;                                                           // set A key pressed to false
				                                                                                 // 
				if(keyPressed[3]) {                                                              // check if D is pressed
					                                                                             // 
					game.getPlayer().setVelX(8 + game.getPlayer().getSpeedBoost());              // move player right
					                                                                             // 
				} else {                                                                         // 
					                                                                             // 
					game.getPlayer().setVelX(0);                                                 // reset player horizontal velocity
									                                                             // 
				}                                                                                // 
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
			if(key == KeyEvent.VK_D) {                                                           // check if D released
				                                                                                 // 
				keyPressed[3] = false;                                                           // set D key pressed to false
				                                                                                 // 
				if(keyPressed[2]) {                                                              // check if A is pressed
					                                                                             // 
					game.getPlayer().setVelX(-8 - game.getPlayer().getSpeedBoost());             // move player left
					                                                                             // 
				} else {                                                                         // 
					                                                                             // 
					game.getPlayer().setVelX(0);                                                 // reset player horizontal velocity
									                                                             // 
				}                                                                                // 
				                                                                                 // 
			}																					 // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
