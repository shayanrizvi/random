package random;

import java.awt.*;
import java.util.*;

public class ControlPage extends LegendPage {
	
	private LinkedList<String> keys = new LinkedList<String>();									 // key list
	private LinkedList<String> functions = new LinkedList<String>();                             // function list
	private TreeMap<String, String> controlMap = new TreeMap<String, String>();                  // control map
	private Player player;                                                                       // player
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs the control page
	 */
	public ControlPage() {                                                                       // 
		                                                                                         // 
		super("Controls");                                                                       // set title
		                                                                                         // 
		keys.add("W");                                                                           // add keys to key list
		keys.add("S");                                                                           // 
		keys.add("A");                                                                           // 
		keys.add("D");                                                                           // 
		keys.add("SPACEBAR");                                                                    // 
		keys.add("LEFT CLICK");                                                                  // 
		keys.add("RIGHT CLICK");                                                                 // 
		keys.add("P");                                                                           // 
		keys.add("ESC");                                                                         // 
                                                                                                 // 
		functions.add("Move up");                                                                // add functions to function list
		functions.add("Move down");                                                              // 
		functions.add("Move left");                                                              // 
		functions.add("Move right");                                                             // 
		functions.add("Drop bomb");                                                              // 
		functions.add("Fire missle");                                                            // 
		functions.add("Fire seeking missle");                                                    // 
		functions.add("Pause");                                                                  // 
		functions.add("Exit Game");                                                              // 
		                                                                                         // 
		for(int i = 0; i < keys.size(); i++) {                                                   // for each key in key list
			                                                                                     // 
			controlMap.put(keys.get(i), functions.get(i));                                       // add key and corresponding function to control map
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		player = new Player(Game.WIDTH * 2/3 - 24, Game.HEIGHT/3 + 20, 48);                      // initialize player
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw control page
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		int i = 0;                                                                               // 
                                                                                                 // 
		for(String s : keys) {                                                                   // for each key in key list
			                                                                                     // 
			g.setColor(Color.WHITE);                                                             // render control map entry
			g.setFont(new Font("Impact", Font.BOLD, 20));                                        // 
			g.drawString(s, Game.WIDTH/4 - g.getFontMetrics(g.getFont()).stringWidth(s),		 //
					Game.HEIGHT/3 + i * 25);   													 // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));               // 
			g.drawString("=", Game.WIDTH/4 + 30, Game.HEIGHT/3 + i * 25);                        // 
			g.setColor(Color.WHITE);                                                             // 
			g.setFont(new Font("Consolas", Font.BOLD, 20));                                      // 
			g.drawString(controlMap.get(s), Game.WIDTH/4 + 70, Game.HEIGHT/3 + i * 25);          // 
			i++;                                                                                 // 
			                                                                                     // 
		}                                                                                        // 
                                                                                                 // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // render player
		g.setFont(new Font("Impact", Font.BOLD, 28));                                            // 
		g.drawString("Player", (Game.WIDTH * 2/3                                                 // 
				- g.getFontMetrics(g.getFont()).stringWidth("Player")/2), Game.HEIGHT/3 - 20);   // 
		player.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));              // 
		player.render(g);                                                                        // 
		                                                                                         // 
		g.setColor(Color.WHITE);                                                                 // render objective
		g.setFont(new Font("Impact", Font.BOLD, 30));                                            // 
		g.drawString("Objective", (Game.WIDTH * 2/3                                              // 
				- g.getFontMetrics(g.getFont()).stringWidth("Objective")/2),					 //
				Game.HEIGHT/3 + 140);              												 // 
		g.setColor(Color.LIGHT_GRAY);                                                            // 
		g.setFont(new Font("Consolas", Font.PLAIN, 20));                                         // 
		g.drawString("Collect all the coins", (Game.WIDTH * 2/3                                  // 
				- g.getFontMetrics(g.getFont()).stringWidth("Collect all the coins")/2),		 //
				Game.HEIGHT/3 + 180);  															 // 
		g.drawString("to advance to", (Game.WIDTH * 2/3                                          // 
				- g.getFontMetrics(g.getFont()).stringWidth("to advance to")/2),				 //
				Game.HEIGHT/3 + 204);         												     // 
		g.drawString("the next level", (Game.WIDTH * 2/3                                         // 
				- g.getFontMetrics(g.getFont()).stringWidth("the next level")/2),				 //
				Game.HEIGHT/3 + 228);          													 // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
