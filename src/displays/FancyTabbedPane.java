package displays;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JPanel;

import clickables.FancyButton;
import colors.FancyColor;

public class FancyTabbedPane extends JPanel {
	private static final long serialVersionUID = -6118817207468017455L;

	public static Color[]   GREEN_TAB  = {FancyColor.DARK_GREEN, FancyColor.LIGHT_GREEN};
	public static Color[]   BLUE_TAB   = {FancyColor.DARK_BLUE, FancyColor.LIGHT_BLUE};
	public static Color[]   RED_TAB    = {FancyColor.DARK_RED, FancyColor.LIGHT_RED};
	public static Color[]   ORANGE_TAB = {FancyColor.DARK_ORANGE, FancyColor.LIGHT_ORANGE};
	public static Color[]   GREY_TAB   = {FancyColor.DARK_GRAY, FancyColor.LIGHT_GRAY};
	
	/** The panel upon which the tabs are displayed **/
	private JPanel tabPanel = new JPanel();
	/** The lower border of {@link #tabPanel} **/
	private JPanel border   = new JPanel();
	/** The panel upon which the tab GUI is displayed **/
	private JPanel tabGUI   = new JPanel();
	/** The content of the selected tab to be displayed **/
	private JPanel content  = new JPanel();

	/** All of our tabs **/
	public TabPanel[] tabs          = {};
	public int        selectedIndex = 0;

	public FancyTabbedPane(Color[] colors) {
		setLayout(new BorderLayout());

		tabPanel.setLayout(new GridBagLayout());
		tabPanel.setBackground(colors[0]);
		
		border.setBackground(colors[1]);
		border.setPreferredSize(new Dimension(50, 5));
		
		tabGUI.setLayout(new BorderLayout());
		tabGUI.add(border, BorderLayout.SOUTH);
		tabGUI.add(tabPanel, BorderLayout.CENTER);

		add(tabGUI, BorderLayout.NORTH);
		add(content, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		
		for(TabPanel p : tabs) {
			if(e.getSource() == p.tab) {
				tabs[selectedIndex].tab.setSelected(false);
				selectedIndex = Arrays.binarySearch(tabs, p);
				
				tabs[selectedIndex].tab.setSelected(true);
				content = tabs[selectedIndex].content;
				
				break;
			}
		}
		
		revalidate();
		repaint();
	}
	
	/** Sets the selected tab **/
	public void setSelected() {
		content = tabs[selectedIndex].content;
		tabs[selectedIndex].tab.setSelected(true);
		
		revalidate();
		repaint();
	}

	/**
	 * Adds all the tabs to their locations.
	 * 
	 * @param tabs are the tabs to be added.
	 */
	public void addTabs(TabPanel[] tabs) {
		this.tabs = tabs;

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill  = GridBagConstraints.HORIZONTAL;

		int x = 0;

		for(TabPanel t : this.tabs) {
			gbc.gridx = x;
			gbc.gridy = 0;
			
			tabPanel.add(t.tab, gbc);
			
			x++;
		}
		
		tabs[selectedIndex].tab.setSelected(true);
		
		revalidate();
		repaint();
	}
	
	/**
	 * A tab and a panel. To be used in {@link FancyTabbedPane}.
	 * @author Alexander Porrello
	 */
	public static class TabPanel implements Comparable<TabPanel> {
		FancyButton tab;
		FancyPanel  content = new FancyPanel();
		
		public TabPanel(FancyButton tab) {
			this.tab = tab;
		}

		@Override
		public int compareTo(TabPanel e) {
			return e.tab.getName().compareTo(tab.getName());
		}
	}
}