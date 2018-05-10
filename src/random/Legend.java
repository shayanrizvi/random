package random;

import java.awt.*;
import java.util.*;

public class Legend {
	
	// Create variables
	private LinkedList<LegendPage> pageList = new LinkedList<LegendPage>();						 // page list
	private LegendPage currentPage;                                                              // current page
	private int[] leftX = new int[3];                                                            // left arrow x values
	private int[] leftY = new int[3];                                                            // left arrow y values
	private int[] rightX = new int[3];                                                           // right arrow x values
	private int[] rightY = new int[3];                                                           // right arrow y values
	private Polygon leftArrow;                                                                   // left arrow
	private Polygon rightArrow;                                                                  // right arrow
	private Random r = new Random();                                                             // random
	                                                                                             // 
	/**
	 * Constructs the legend screen
	 */
	public Legend() {                                                                            // 
                                                                                                 // 
		leftX[0] = 20;                                                                           // define left arrow x values
		leftX[1] = 40;                                                                           // 
		leftX[2] = 40;                                                                           // 
                                                                                                 // 
		leftY[0] = 100;                                                                          // define left arrow y values
		leftY[1] = 80;                                                                           // 
		leftY[2] = 120;                                                                          // 
                                                                                                 // 
		rightX[0] = Game.WIDTH - 20;                                                             // define right arrow x values
		rightX[1] = Game.WIDTH - 40;                                                             // 
		rightX[2] = Game.WIDTH - 40;                                                             // 
		                                                                                         // 
		rightY[0] = 100;                                                                         // define right arrow y values
		rightY[1] = 80;                                                                          // 
		rightY[2] = 120;                                                                         // 
                                                                                                 // 
		leftArrow = new Polygon(leftX, leftY, 3);                                                // initialize left arrow
		rightArrow = new Polygon(rightX, rightY, 3);                                             // initialize right arrow
		                                                                                         // 
		ControlPage controls = new ControlPage();                                                // initialize control page
		pageList.add(controls);                                                                  // add control page to page list
		                                                                                         // 
		LinkedList<GameObject> tokens = new LinkedList<GameObject>();                            // initialize token list
		tokens.add(new Coin(0, 0, 20));                                                          // add tokens to token list
		tokens.add(new LifeToken(0, 0, 6, null));                                                // 
		tokens.add(new HealthToken(0, 0, 20));                                                   // 
		tokens.add(new AmmoToken(0, 0, 20, null));                                               // 
		LegendPage tokensPage = new LegendPage("Tokens", tokens);                                // create token page
		pageList.add(tokensPage);                                                                // add token page to page list
                                                                                                 // 
		LinkedList<GameObject> powerUps = new LinkedList<GameObject>();                          // initialize power up list
		powerUps.add(new SpeedUp(0, 0, 20));                                                     // add power ups to power up list
		powerUps.add(new SlowMo(0, 0, 20));                                                      // 
		powerUps.add(new Freeze(0, 0, 20));                                                      // 
		powerUps.add(new Invincibility(0, 0, 6, null));                                          // 
		LegendPage powerUpsPage = new LegendPage("Power Ups", powerUps);                         // create power up page
		pageList.add(powerUpsPage);                                                              // add power up page to page list
                                                                                                 // 
		LinkedList<GameObject> upgrades = new LinkedList<GameObject>();                          // initialize upgrade list
		upgrades.add(new HealthBoost(0, 0, 20, null));                                           // add upgrades to upgrade list
		upgrades.add(new SpeedBoost(0, 0, 20));                                                  // 
		upgrades.add(new DamageBoost(0, 0, 20, null));                                           // 
		upgrades.add(new AmmoBoost(0, 0, 20, null));                                             // 
		LegendPage upgradesPage = new LegendPage("Upgrades", upgrades);                          // create upgrade page
		pageList.add(upgradesPage);                                                              // add upgrade page to page list
		                                                                                         // 
		LinkedList<GameObject> basicEnemies = new LinkedList<GameObject>();                      // initialize basic enemy list
		basicEnemies.add(new BounceEnemy(0, 0, 24));                                             // add basic enemies to basic enemy list
		basicEnemies.add(new FlipEnemy(0, 0, 24));                                               // 
		basicEnemies.add(new HorizontalEnemy(0, 0, 24));                                         // 
		basicEnemies.add(new VerticalEnemy(0, 0, 24));                                           // 
		basicEnemies.add(new SmallEnemy(0, 0, 24));                                              // 
		basicEnemies.add(new BigEnemy(0, 0, 10));                                                // 
		LegendPage basicEnemiesPage = new LegendPage("Basic Enemies", basicEnemies);             // create basic enemy page
		pageList.add(basicEnemiesPage);                                                          // add basic enemy page to page list
                                                                                                 // 
		LinkedList<GameObject> advancedEnemies = new LinkedList<GameObject>();                   // initialize advanced enemy list
		advancedEnemies.add(new SeekEnemy(0, 0, 24, null));                                      // add advanced enemies to advanced enemy list
		advancedEnemies.add(new MissleEnemy(0, 0, 24, null));                                    // 
		advancedEnemies.add(new BombEnemy(0, 0, 24, null));                                      // 
		advancedEnemies.add(new GuardEnemy(0, 0, 40, null));                                     // 
		LegendPage advancedEnemiesPage = new LegendPage("Advanced Enemies", advancedEnemies);    // create advanced enemy page
		pageList.add(advancedEnemiesPage);                                                       // add advanced enemy page to page list
                                                                                                 // 
		LinkedList<GameObject> weapons = new LinkedList<GameObject>();                           // initialize weapon list
		weapons.add(new Missle());                                                               // add weapons to weapon list
		weapons.add(new SeekMissle());                                                           // 
		weapons.add(new Bomb());                                                                 // 
		LegendPage weaponsPage = new LegendPage("Weapons", weapons);                             // create weapon page
		pageList.add(weaponsPage);                                                               // add weapon page to page list
                                                                                                 // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw legend
	 * 
	 * @param g	graphics context
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		g.setFont(new Font("Impact", Font.BOLD, 50));                                            // render legend
		g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));                   // 
		g.drawString("Legend", (Game.WIDTH - g.getFontMetrics(                                   // 
				new Font("Impact", Font.BOLD, 50)).stringWidth("Legend"))/2, 60);                // 
                                                                                                 // 
		g.setColor(Color.WHITE);                                                                 // 
		g.setFont(new Font("Impact", Font.BOLD, 24));                                            // 
		g.drawString(currentPage.getTitle(), (Game.WIDTH                                         // 
				- g.getFontMetrics(g.getFont()).stringWidth(currentPage.getTitle()))/2,          // 
				110);                                                                            // 
		                                                                                         // 
		g.setFont(new Font("Impact", Font.PLAIN, 24));                                           // 
		                                                                                         // 
		if(pageList.peekFirst() != currentPage) {                                                // check if current page is not first page
			                                                                                     // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));               // render left arrow
			g.fillPolygon(leftArrow);                                                            // 
			g.setColor(Color.WHITE);                                                             // 
			g.drawString(pageList.get(Math.max(0,                                                // 
					pageList.indexOf(currentPage) - 1)).getTitle(), 50, 110);                    // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		if(pageList.peekLast() != currentPage) {                       					         // check if current page is not last page
                                                                                                 // 
			g.setColor(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));               // render right arrow
			g.fillPolygon(rightArrow);                                                           // 
			g.setColor(Color.WHITE);                                                             // 
			g.drawString(pageList.get(Math.min(pageList.size() - 1,                              // 
					pageList.indexOf(currentPage) + 1)).getTitle(),                              // 
					Game.WIDTH - 50 - g.getFontMetrics(g.getFont()).stringWidth(                 // 
							pageList.get(pageList.indexOf(currentPage) + 1).getTitle()), 110);   // 
			                                                                                     // 
		}                                                                                        // 
                                                                                                 // 
		g.setColor(Color.WHITE);                                                                 // 
		g.fillRect(0, 130, Game.WIDTH, 10);                                                      // 
		g.fillRect(0, 704, Game.WIDTH, 10);                                                      // 
		                                                                                         // 
		g.setFont(new Font("Consolas", Font.BOLD, 20));                                          // 
		g.drawString("L = EXIT", 1260, 760);                                                     // 
		                                                                                         // 
		currentPage.render(g);                                                                   // render current legend page
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public LinkedList<LegendPage> getPageList() {                                                // 
		                                                                                         // 
		return pageList;                                                                         // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public LegendPage getCurrentPage() {                                                         // 
		                                                                                         // 
		return currentPage;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setCurrentPage(LegendPage page) {                                                // 
		                                                                                         //
		this.currentPage = page;																 // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             //
}																								 // 
