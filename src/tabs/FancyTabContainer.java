package tabs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Contains several useful methods to quickly access tabs.
 * @author Alexander Porrello
 */
public class FancyTabContainer extends ArrayList<FancyTab> {
	private static final long serialVersionUID = -1272719320778131995L;

	public FancyTab selected = null;
	
	public FancyTabContainer() {

	}
	
	public ArrayList<FancyTab> getAllTabs() {
		return this;
	}
	
	public void setSelectedIndex(FancyTab toSet) {
		selected = toSet;
	}
	
	/**
	 * Removes a FancyTab and sets all the indecees correct.
	 * @param tab
	 */
	public void remove(FancyTab tab) {
		
		for(FancyTab t : this) {
			if(t.index > tab.index) {
				t.index = t.index-1;
			}
		}
		
		super.remove(tab);
	}
	
	/**
	 * Returns a tab given its name.
	 * @param name is the tab's name
	 * @return the tab belonging to the name
	 */
	public FancyTab get(String name) {
		for(FancyTab ft : this) {
			if(ft.name.equals(name)) {
				return ft;
			}
		}

		throw new NoSuchElementException();
	}
	
	/**
	 * Returns a tab if it has been clicked on.
	 * @param x the x posn of the cursor
	 * @param y the y posn of the cursor
	 * @return a tab if it has been clicked on.
	 */
	public FancyTab get(int x, int y) {
		for(FancyTab ft : this) {
			if(ft.contains(new Point(x, y))) {
				return ft;
			}
		}

		throw new NoSuchElementException();
	}

	/**
	 * Returns a tab given its position.
	 * @param  posn the posn of the tab to be returned.
	 * @return the proper posn.
	 */
	public FancyTab get(int posn) {
		for(FancyTab ft : this) {
			if(ft.index == posn) {
				return ft;
			}
		}

		throw new NoSuchElementException();
	}

	/**
	 * Returns the tile to the right of a given tile.
	 * @param ft the given tile.
	 * @return the tile to the right of the given tile.
	 */
	public FancyTab getRightTab(FancyTab ft) {
		if(ft.index + 1 <= this.size()) {
			return get(ft.index + 1);
		} else {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns the tile to the left of a given tile.
	 * @param ft the given tile.
	 * @return the tile to the right of the given tile.
	 */
	public FancyTab getLeftTab(FancyTab ft) {
		if(ft.index - 1 >= 0) {
			return get(ft.index - 1);
		} else {
			throw new NoSuchElementException();
		}
	}
}
