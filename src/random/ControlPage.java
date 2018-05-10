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
		player = new Player(916, 285, 48);                                                       // initialize player
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw control page
	 */
	public void render(Graphics g) {                                                             // 
                                                                                                 // 
		int i = 0;                                                                               // 
		g.setFont(new Font("Consolas", Font.BOLD, 25));                                          // 
                                                                                                 // 
		for(String s : keys) {                                                                   // for each key in key list
			                                                                                     // 
			g.setColor(Color.WHITE);                                                             // render control map entry
			g.setFont(new Font("Impact", Font.BOLD, 20));                                        // 
			g.drawString(s, 250 - g.getFontMetrics(g.getFont()).stringWidth(s), 300 + i * 25);   // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));               // 
			g.drawString("=", 280, 300 + i * 25);                                                // 
			g.setColor(Color.WHITE);                                                             // 
			g.setFont(new Font("Consolas", Font.BOLD, 20));                                      // 
			g.drawString(controlMap.get(s), 330, 300 + i * 25);                                  // 
			i++;                                                                                 // 
			                                                                                     // 
		}                                                                                        // 
                                                                                                 // 
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // render player
		g.setFont(new Font("Impact", Font.BOLD, 20));                                            // 
		g.drawString("Player", (1200 + Game.WIDTH/2                                              // 
				- g.getFontMetrics(g.getFont()).stringWidth("Player"))/2, 235);                  // 
		player.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));              // 
		player.render(g);                                                                        // 
		                                                                                         // 
		g.setColor(Color.WHITE);                                                                 // render objective
		g.setFont(new Font("Impact", Font.BOLD, 30));                                            // 
		g.drawString("Objective", (1200 + Game.WIDTH/2                                           // 
				- g.getFontMetrics(g.getFont()).stringWidth("Objective"))/2, 430);               // 
		g.setColor(Color.LIGHT_GRAY);                                                            // 
		g.setFont(new Font("Consolas", Font.PLAIN, 20));                                         // 
		g.drawString("Collect all the coins", (1200 + Game.WIDTH/2                               // 
				- g.getFontMetrics(g.getFont()).stringWidth("Collect all the coins"))/2, 475);   // 
		g.drawString("to advance to", (1200 + Game.WIDTH/2                                       // 
				- g.getFontMetrics(g.getFont()).stringWidth("to advance to"))/2, 500);           // 
		g.drawString("the next level", (1200 + Game.WIDTH/2                                      // 
				- g.getFontMetrics(g.getFont()).stringWidth("the next level"))/2, 525);          // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
