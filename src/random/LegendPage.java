package random;

import java.awt.*;
import java.util.*;

public class LegendPage {
	
	protected LinkedList<GameObject> objectList = new LinkedList<GameObject>();														// object list
	protected EnumMap<ID, String[]> IdMap = new EnumMap<ID, String[]>(ID.class);													// ID map
	protected TreeMap<GameObject, EnumMap.Entry<ID, String[]>> objectMap = new TreeMap<GameObject, EnumMap.Entry<ID, String[]>>();	// object map
	protected String title;																		 									// title
	
	/**
	 * Constructs a blank legend page
	 * 
	 * @param title	page title
	 */
	public LegendPage(String title) {                                                            // 
		                                                                                         // 
		this.title = title;                                                                      // set page title
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Constructs a legend page populated with the objects provided in the list parameter
	 * 
	 * @param title	page title
	 * 
	 * @param objectList	list of objects to display on the page
	 */
	@SuppressWarnings("unchecked")                                                               // 
	public LegendPage(String title, LinkedList<GameObject> objectList) {                         // 
		                                                                                         // 
		this.title = title;                                                                      // set page title
		                                                                                         // 
		int col = 0;                                                                             // initialize column
		int row = 1;                                                                             // initialize row
		                                                                                         // 
		for(int i = 0; i < objectList.size(); i++) {                                             // for each object in object list
			                                                                                     // 
			if(i % 2 == 0) {                                                                     // for every other object
				                                                                                 // 
				col += 1;                                                                        // increment column
				row = 1;                                                                         // reset row
				                                                                                 // 
			} else row += 1;                                                                     // increment row
			                                                                                     // 
			objectList.get(i).setX(col * 260);                                                   // set object x value
			objectList.get(i).setY(row * 260);                                                   // set object y value
			                                                                                     // 
			if(objectList.get(i).getS() == 0) objectList.get(i).setS(40);                        // resize object to display size
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
		this.objectList = objectList;                                                            // copy object list reference
		                                                                                         // 
		for(int i = 0; i < objectList.size(); i++ ) {                                            // for each object in object list
			                                                                                     // 
			IdMap.put(objectList.get(i).getId(), objectList.get(i).toInfo());                    // add object ID and object info to ID map
			objectMap.put(objectList.get(i),                                                     // add object, object ID, and object info to object map
					(EnumMap.Entry<ID, String[]>) IdMap.entrySet().toArray()[i]);                // 
			                                                                                     // 
		}                                                                                        // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	/**
	 * Draw legend page
	 * 
	 * @param g graphics context
	 */
	public void render(Graphics g) {                                                             // 
		                                                                                         // 
		objectMap.forEach((k,v)->{                                                               // for each entry in object map
			                                                                                     // 
			if(k.getColor() == Color.BLACK) g.setColor(Color.WHITE);                             // set text color to display color
			else g.setColor(k.getColor());                                                       // 
																								 // 
			g.setFont(new Font("Impact", Font.BOLD, 20));                                        // render object ID
			g.drawString(v.getKey().name(),                                                      // 
					k.getX() + k.getS()/2                                                        // 
							- (g.getFontMetrics(g.getFont()).stringWidth(v.getKey().name()))/2,  // 
					k.getY() - 20);                                                              // 
			                                                                                     // 
			k.render(g);                                                                         // render object
			                                                                                     // 
			g.setColor(Color.WHITE);                                                             // 
			g.setFont(new Font("Consolas", Font.BOLD, 14));                                      // 
			                                                                                     // 
			int i = 0;                                                                           // 
			for(String s : v.getValue()) {                                                       // for each line in object info
				                                                                                 // 
				g.drawString(s,                                                                  // render line
						k.getX() + k.getS()/2                                                    // 
								- (g.getFontMetrics(g.getFont()).stringWidth(s))/2,              // 
						k.getY() + 72 + i);                                                      // 
				                                                                                 // 
				i += 16;                                                                         // increment line spacing
				                                                                                 // 
			}                                                                                    // 
			                                                                                     // 
		});																						 // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public LinkedList<GameObject> getObjectList() {                                              // 
		                                                                                         // 
		return objectList;                                                                       // 
		                                                                                         // 
	}                                                                                            // 
                                                                                                 // 
	public void setObjectList(LinkedList<GameObject> objectList) {                               // 
		                                                                                         // 
		this.objectList = objectList;                                                            // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public String getTitle() {                                                                   // 
		                                                                                         // 
		return title;                                                                            // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
	public void setTitle(String title) {                                                         // 
		                                                                                         // 
		this.title = title;                                                                      // 
		                                                                                         // 
	}                                                                                            // 
	                                                                                             // 
}                                                                                                // 
