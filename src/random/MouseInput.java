package random;

import java.awt.event.*;

/**
 * 
 * Mouse adapter class for receiving and implementing mouse events.
 * 
 * @author Shayan
 *
 */
public class MouseInput extends MouseAdapter {
	
	private Game game;																			 // game
																								 // 
	/**
	 * Constructs a mouse input handler
	 * 
	 * @param game reference to the game for object variable access
	 */
	MouseInput(Game game) {                                                                      // 
		                                                                                         // 
		this.game = game;                                                                        // copy game reference
                                                                                                 // 
//		try {Robot robot = new Robot();}                                                         // initialize robot
//		catch (AWTException e1) {e1.printStackTrace();}                                          // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
//	public void mouseMoved(MouseEvent e) {                                                       // 
//		                                                                                         // 
//		if(e.getXOnScreen() <= 0) robot.mouseMove(1, e.getY());                                  // contain mouse within JFrame
//		if(e.getXOnScreen() >= Game.WIDTH) robot.mouseMove(Game.WIDTH, e.getY());				 // contain mouse within JFrame
//		if(e.getYOnScreen() <= 0) robot.mouseMove(e.getX(), 1);                                  // contain mouse within JFrame
//		if(e.getYOnScreen() >= Game.HEIGHT) robot.mouseMove(e.getX(), Game.HEIGHT);              // contain mouse within JFrame
//		                                                                                         // 
//	}                                                                                            // 
//	                                                                                             // 
//	public void mouseExited(MouseEvent e) {                                                      // 
//		                                                                                         // 
//		if(e.getXOnScreen() <= 0) robot.mouseMove(1, e.getY());                                  // contain mouse within JFrame
//		if(e.getXOnScreen() >= Game.WIDTH) robot.mouseMove(Game.WIDTH, e.getY());                // contain mouse within JFrame
//		if(e.getYOnScreen() <= 0) robot.mouseMove(e.getX(), 1);                                  // contain mouse within JFrame
//		if(e.getYOnScreen() >= Game.HEIGHT) robot.mouseMove(e.getX(), Game.HEIGHT);              // contain mouse within JFrame
//		                                                                                         // 
//	}																							 // 
//																								 // 
	public void mousePressed(MouseEvent e) {													 // 
																								 // 
		if(!game.getGameStart() && !game.getGameSpawn()                                          // check if game not in start, spawn, paused, or over state
				&& !game.getGamePaused() && !game.getGameOver()) {                               // 
		                                                                                         // 
			if(game.getPlayer().getAmmo() > 0) {                                                 // check if player has ammo
				                                                                                 // 
				if(e.getButton() == 1) {                                                         // check if left button pressed
					                                                                             // 
					game.setScore(Math.max(0, game.getScore() - 10));                            // decrease score by 10
					game.getPlayer().setAmmo(Math.max(0, game.getPlayer().getAmmo() - 1));       // decrease player ammo by 1
					game.getWeapons().add(game.getPlayer().fireMissle(                           // fire missle
							10 + game.getPlayer().getDamageBoost() * 2,                          // 
							e.getX() - 10,                                                       // 
							e.getY() - 10,														 // 
							10 + game.getPlayer().getDamageBoost() * 3,                          // 
							7));                                                                 // 
					                                                                             // 
				} else if(e.getButton() == 3) {                                                  // check if right button pressed
					                                                                             // 
					if(game.getPlayer().getAmmo() >= 5) {                                        // check if player ammo is greater than 5
						                                                                         // 
						GameObject target = null;                                                // create target reference
						game.setScore(Math.max(0, game.getScore() - 50));                        // decrease score by 50
						game.getPlayer().setAmmo(Math.max(0, game.getPlayer().getAmmo() - 5));   // decrease ammo by 5
						                                                                         // 
						for(int i = 0; i < game.getObjects().size(); i++) {                      // for each object in object list
																								 // 
							switch(game.getObjects().get(i).getId()) {                           // switch object ID
							                                                                     // 
							case BounceEnemy: case FlipEnemy: case HorizontalEnemy:              // case: enemies and weapon boosts
							case VerticalEnemy: case SmallEnemy: case BigEnemy:                  // 
							case SeekEnemy: case MissleEnemy: case BombEnemy:                    // 
							case GuardEnemy: case DamageBoost: case AmmoBoost: {                 // 
								                                                                 // 
								if(game.getObjects().get(i).getBounds().contains(e.getPoint())) {// check if the cursor is on the object
									                                                             // 
									target = game.getObjects().get(i);                           // copy object reference to target
									                                                             // 
								}                                                                // 
																								 // 
							}                                                                    // 
							                                                                     // 
							break;                                                               // 
							                                                                     // 
							default: break;                                                      // 
							                                                                     // 
							}                                                                    // 
							                                                                     // 
						}                                                                        // 
						                                                                         // 
						if(target != null) {                                                     // check if target is not null
							                                                                     // 
							game.getWeapons().add(game.getPlayer().fireSeekMissle(				 // fire seeking missle at target
									16 + game.getPlayer().getDamageBoost(),                      // 
									target,                                                      // 
									24 + game.getPlayer().getDamageBoost() * 3));                // 
							                                                                     // 
						} else {                                                                 // 
							                                                                     // 
							game.getWeapons().add(game.getPlayer().fireSeekMissle(               // fire seeking missle
									16,                                                          // 
									e.getX(),                                                    // 
									e.getY(),                                                    // 
									24,                                                          // 
									5));														 // 
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
}                                                                                                // 
