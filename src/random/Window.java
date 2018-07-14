package random;

import java.awt.*;
import javax.swing.*;

/**
 * This class defines a JFrame to house the Game canvas.
 * 
 * @author Shayan
 */
public class Window extends JFrame {
	
	private static final long serialVersionUID = 6596273530124848110L;		// class ID
	
	/**
	 * Constructs a Jframe to house the game
	 * 
	 * @param width	game width
	 * @param height game height
	 * @param title	game title
	 * @param game reference to the game for object variable access
	 */
	public Window(int width, int height, String title, Game game) {
		
		setTitle(title);													// title
		setPreferredSize(new Dimension(width, height));						// preferred size
		setMaximumSize(new Dimension(width, height));						// maximum size
		setMinimumSize(new Dimension(width, height));						// minimum size
//	    setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);	// maximized
		setResizable(false);												// resizability
		setAlwaysOnTop(true);												// always on top
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);						// exit on close
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));						// crosshair cursor
//		setUndecorated(true);												// undecorated
		setVisible(true);													// visibility
		add(game);															// add game
		
	}
	
}
