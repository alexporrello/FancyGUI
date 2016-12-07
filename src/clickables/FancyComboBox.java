package clickables;

import icons.FancyIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import text.FancyLabel;
import colors.FancyColor;
import colors.HoverColor;

/**
 * FancyCombo box adds some style to Java's built-in JComboBox 
 * by implementing a clickable interface with slick icons 
 * and custom colors.
 * 
 * @author Alexander Porrello
 *
 * @param <E> is the type of item that will be displayed in the ComboBox
 */
public class FancyComboBox<E> extends JPanel {
	private static final long serialVersionUID = 3408045971846568361L;

	/** The button by which the user will step to the next item **/
	private FancyButton next = new FancyButton(FancyIcon.NEXT_16x16);
	/** The button by which the user will step to the previous item **/
	private FancyButton last = new FancyButton(FancyIcon.LAST_16x16);
	/** The Currently selected index **/
	private int selectedIndex = 0;
	/** The label on which the name of the current selected item will be displayed **/
	private FancyLabel displayLabel;
	/** The array in which all of our items will be held **/
	private E[] array;
	/** The number of items to be held in the array **/
	private int numItems;
	/** The color of the items on the displaylabel **/
	private Color labelColor;
	
	/**
	 * To be used if you have an array of items for the user to select from.
	 * @param array is the array of items that will be displayed to the user.
	 */
	public FancyComboBox(E[] array) {
		this.array = array;
		numItems   = this.array.length;
		
		setLayout(new BorderLayout());

		makeButtons();

		displayLabel = new FancyLabel(this.array[selectedIndex] + "");
		displayLabel.setForeground(labelColor);
		add(displayLabel, BorderLayout.CENTER);
	}

	/**
	 * The method by which the {@link #next} and {@link #last} will be 
	 * created and added to the view.
	 */
	private void makeButtons() {
		setBackground(FancyColor.LIGHT_GRAY);
		
		next.setSize(new Dimension(30, 30));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndex < numItems-1) {
					selectedIndex++;
				} else if(selectedIndex == numItems-1) {
					selectedIndex = 0;
				}
				
				setSelectedIndex(selectedIndex);

				revalidate();
				repaint();
			}
		});
		
		last.setSize(new Dimension(30, 30));
		last.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedIndex > 0) {
					selectedIndex--;
				} else if(selectedIndex == 0) {
					selectedIndex = numItems-1;
				}
				
				setSelectedIndex(selectedIndex);

				revalidate();
				repaint();
			}
		});
		
		add(next, BorderLayout.EAST);
		add(last, BorderLayout.WEST);
	}

	/**
	 * Sets the currently displayed item to a given index.
	 * @param i is the index of the item that will be displayed.
	 */
	public void setSelectedIndex(int i) {
		if(i >= 0 && i < numItems) {
			remove(displayLabel);
			displayLabel = new FancyLabel(this.array[selectedIndex] + "");
			displayLabel.setForeground(labelColor);
			add(displayLabel, BorderLayout.CENTER);
		} else if(i > numItems){
			throw new IllegalArgumentException("i must not exceed " + numItems);
		} else if(i < 0){
			throw new IllegalArgumentException("i must not be less than 0");
		}
	}

	/**
	 * Sets the currently displayed item to a given item.
	 * @param item is the item that will be displayed.
	 */
	public void setSelectedItem(E item) {
		selectedIndex = Arrays.asList(array).indexOf(item);
		setSelectedIndex(selectedIndex);
	}

	/**
	 * Returns the currently selected item.
	 * @return the currently selected item.
	 */
	public E getSelectedItem() {
		return array[selectedIndex];
	}
	
	/**
	 * 
	 * @param icon is the icon that will be painted onto the left button.
	 */
	public void setLeftIcon(ImageIcon icon) {
		last.setIcon(icon);
	}
	
	/**
	 * 
	 * @param icon is the icon that will be painted onto the right button.
	 */
	public void setRightIcon(ImageIcon icon) {
		next.setIcon(icon);
	}
	
	/**
	 * 
	 * @param color is the color which the display will be set.
	 */
	public void setTextColor(Color color) {
		displayLabel.setForeground(color);
		labelColor = color;
	}
	
	
	public void setColor(HoverColor color) {
		next.setColor(color);
		last.setColor(color);

		setBackground(color.light);
	}
}