package tabs;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Timer;

import colors.FancyColor;
import displays.FancyFrame;
import displays.FancyPanel;

public class FancyTabPanel extends FancyPanel {
	private static final long serialVersionUID = 3467583173655155282L;

	private FancyTabOrganizer tabOrganizer = new FancyTabOrganizer();

	private JComponent inView = new FancyPanel();

	private Color xColor = FancyColor.DARK_GRAY;

	public FancyTabPanel() {	
		setLayout(new BorderLayout());

		add(tabOrganizer, BorderLayout.NORTH);
		add(inView, BorderLayout.CENTER);
	}

	/**
	 * Returns selected component in {@link #tabOrganizer}.
	 * @return
	 */
	public JComponent selectedComponent() {
		return getSelectedTab().content;
	}

	public FancyTab getSelectedTab() {
		return tabOrganizer.tabs.selected;
	}

	/**
	 * Method used to add a tab.
	 * @param title the tab's title
	 * @param component the component to be displayed when the tab's clicked
	 * @param closeListener the action taken when the close is pressed
	 */
	public void addTab(String title, JComponent component, 
			Consumer<MouseEvent> closeListener, Consumer<MouseEvent> clickListener) {
		tabOrganizer.addTab(title, component, closeListener, clickListener);
	}

	/**
	 * Method used to remove a tab.
	 * @param tab the tab to be removed
	 */
	public void removeTab(String tabTitle) {
		tabOrganizer.removeTab(tabTitle);
	}

	/**
	 * Changes between the JComponent that is displayed.
	 * @param newTab the tab whose JComponent is to be displayed
	 */
	private void switchJComponentInView(FancyTab newTab) {
		remove(inView);
		inView = newTab.content;
		add(inView, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

	public ArrayList<FancyTab> getTabArray() {
		return tabOrganizer.tabs.getAllTabs();
	}
	
	/**
	 * This is where all of the tab magic happens.
	 * @author Alexander Porrello
	 */
	private class FancyTabOrganizer extends FancyPanel {
		private static final long serialVersionUID = 1L;

		FancyTabContainer tabs = new FancyTabContainer();

		public int tabHeight = 25;
		public int tabWidth  = 150;

		int mouseClickPosn;

		Timer swapRightTimer;
		Timer swapLeftTimer;
		Timer returnTimer;

		FancyTab pressedTab;
		FancyTab swappedTab;

		Font tabFont;

		public FancyTabOrganizer() {
			setPreferredSize(new Dimension(500, tabHeight + 5));

			Font a = new JButton().getFont();
			tabFont = new Font(a.getName(), a.getStyle(), 12);

			makeSwapTimers();
			makeReturnTimer();
			addMouseListener();
			addMouseMotionListener();
		}

		/**
		 * Initializes the timer responsible for returning Tabs back to their 
		 * proper position.
		 */
		public void makeReturnTimer() {
			returnTimer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(pressedTab.x > pressedTab.determineXPosition()) {
						pressedTab.x = pressedTab.x - 1;
						repaint();
					} else if(pressedTab.x < pressedTab.determineXPosition()) {
						pressedTab.x = pressedTab.x + 1;
						repaint();
					} else {
						returnTimer.stop();
					}
				}
			});
		}

		/**
		 * Initializes the timers responsible for swapping tabs.
		 */
		public void makeSwapTimers() {
			swapRightTimer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {					
					if(swappedTab.x > pressedTab.determineXPosition()) {
						swappedTab.x = swappedTab.x - 1;
						repaint();
					} else {
						swapTabIndices(pressedTab, swappedTab);
						swapRightTimer.stop();
					}
				}
			});

			swapLeftTimer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {										
					if(swappedTab.x < pressedTab.determineXPosition()) {
						swappedTab.x = swappedTab.x + 1;
						repaint();
					} else {
						swapTabIndices(pressedTab, swappedTab);
						swapLeftTimer.stop();
					}
				}
			});
		}

		public void addMouseListener() {
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					mouseClickPosn = e.getX();
					returnTimer.start();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					try {
						pressedTab     = tabs.get(e.getX(), e.getY());
						mouseClickPosn = e.getX() - pressedTab.x;

						tabs.setSelectedIndex(pressedTab);
						switchJComponentInView(pressedTab);

						repaint();
					} catch (NoSuchElementException f) {}
				}
			});
		}

		public void addMouseMotionListener() {
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent arg0) {
					if(tabs.selected != null) {
						if(tabs.selected.determineXClickPosn().contains(arg0.getPoint())) {
							xColor = FancyColor.DARK_RED;
						} else {
							xColor = FancyColor.DARK_GRAY;
						}

						repaint();
					}
				}

				@Override
				public void mouseDragged(MouseEvent arg0) {
					pressedTab.x = arg0.getX() - mouseClickPosn;

					try {					
						FancyTab rightTab = tabs.getRightTab(pressedTab);

						if(pressedTab.x + (pressedTab.width/2) > rightTab.x) {
							swappedTab = rightTab;
							swapRightTimer.start();
						}
					} catch (NoSuchElementException e) {}

					try {
						FancyTab leftTab = tabs.getLeftTab(pressedTab);

						if((pressedTab.x < leftTab.x + (leftTab.width/2))
								&& (pressedTab.x > leftTab.x)) {
							swappedTab = leftTab;
							swapLeftTimer.start();
						}

					} catch (NoSuchElementException e) {}

					revalidate();
					repaint();
				}
			});
		}

		/**
		 * Swaps the indices of two given tabs.
		 * @param a the first tab whose index is to be swapped
		 * @param b the second tab whose index is to be swapped
		 */
		public void swapTabIndices(FancyTab a, FancyTab b) {
			int indexA = a.index;
			int indexB = b.index;

			a.index = indexB;
			b.index = indexA;
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D gg = (Graphics2D) g;
			gg.setFont(tabFont);
			gg.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);

			gg.setColor(Color.LIGHT_GRAY);
			gg.fillRect(0, 0, getWidth(), getHeight());

			for(FancyTab tab : tabs) {
				gg.setColor(Color.LIGHT_GRAY);
				gg.fillRect(tab.x, tab.y, tabWidth, tabHeight);
				gg.setColor(Color.BLACK);
				gg.drawString(tab.drawText, tab.x + 6, tab.y + tabHeight-8);
			}

			gg.setColor(Color.decode("#F0F0F0"));
			gg.fillRect(0, tabHeight, getWidth(), 5);

			if(tabs.selected != null) {
				//Draw the selected tab in the front.
				gg.fillRect(tabs.selected.x, tabs.selected.y, tabWidth,
						tabHeight);
				gg.setColor(Color.BLACK);
				gg.drawString(tabs.selected.drawText, tabs.selected.x + 6, 
						tabs.selected.y + tabHeight-8);

				//Draw out the "X"
				//				gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				//						RenderingHints.VALUE_ANTIALIAS_ON);
				Rectangle r = tabs.selected.determineXClickPosn();
				gg.setColor(xColor);
				gg.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, 
						BasicStroke.JOIN_ROUND));
				gg.drawLine(r.x, r.y, r.x+r.width, r.height);
				gg.drawLine(r.x, r.height, r.x+r.width, r.y);
			}
		}

		public void addTab(String title, JComponent component,
				Consumer<MouseEvent> listener, Consumer<MouseEvent> clickListener) {
			FancyTab toAdd = new FancyTab(title, tabWidth, tabHeight, tabs.size(), tabFont, component);
			tabs.add(toAdd);
			tabs.setSelectedIndex(toAdd); 

			switchJComponentInView(toAdd);

			addMouseListener(new MouseAdapter() {								
				@Override
				public void mouseClicked(MouseEvent e) {
					if(toAdd.determineXClickPosn().contains(e.getPoint())) {
						listener.accept(e);
					} else {
						clickListener.accept(e);
					}
				}
			});

			repaint();
		}

		public void removeTab(String tabTitle) {
			try {
				FancyTab ft = tabs.get(tabTitle);

				if(tabs.size() > 1) {
					if(ft.index == 0) {
						try {
							tabs.setSelectedIndex(tabs.getRightTab(ft));
							switchJComponentInView(tabs.getRightTab(ft));
						} catch (NoSuchElementException e) {}
					} else {
						try {
							tabs.setSelectedIndex(tabs.getLeftTab(ft));
							switchJComponentInView(tabs.getLeftTab(ft));
						} catch (NoSuchElementException e) {}
					}
				}

				tabs.remove(ft);

				for(FancyTab tab : tabs) {
					new RemoveTimer(tab);
				}

				repaint();
			} catch (NoSuchElementException e) {}
		}

		public class RemoveTimer {

			Timer removeTimer;

			public RemoveTimer(FancyTab tab) {
				removeTimer = new Timer(0, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						if(tab.x > tab.determineXPosition()) {
							tab.x = tab.x - 1;
							repaint();
						} else if(tab.x < tab.determineXPosition()) {
							tab.x = tab.x + 1;
							repaint();
						} else {
							removeTimer.stop();
						}
					}
				});

				removeTimer.start();
			}
		}
	}

	public static FancyPanel test(Color color) {
		FancyPanel fp = new FancyPanel();
		fp.setBackground(color);
		return fp;
	}

	public static void main(String[] args) {
		FancyFrame frame = new FancyFrame();
		frame.setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(new Dimension(400, 50));

		FancyTabPanel ftpv2 = new FancyTabPanel();

		ftpv2.addTab("Testing", test(Color.RED), e -> {
			ftpv2.removeTab("Testing");
		}, f -> {});
		ftpv2.addTab("Testing 2", test(Color.BLUE), e -> {
			ftpv2.removeTab("Testing 2");
		}, f -> {});
		ftpv2.addTab("Testing 3", test(Color.ORANGE), e -> {
			ftpv2.removeTab("Testing 3");
		}, f -> {});

		frame.add(ftpv2);
		frame.pack();

		frame.setVisible(true);
	}
}
