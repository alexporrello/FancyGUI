package tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Timer;

import displays.FancyFrame;
import displays.FancyPanel;

public class FancyTabPanelV2 extends FancyPanel {
	private static final long serialVersionUID = 3467583173655155282L;

	FancyTabOrganizer tabs = new FancyTabOrganizer();
	
	JComponent inView = new FancyPanel();
	
	public FancyTabPanelV2() {
		tabs.addTab("Testing", test(Color.RED));
		tabs.addTab("Testing 2", test(Color.BLUE));
		tabs.addTab("Testing 3", test(Color.ORANGE));
		
		setLayout(new BorderLayout());
		
		add(tabs, BorderLayout.NORTH);
		add(inView, BorderLayout.CENTER);
		
		addMouseListener();
		addMouseMotionListener();
	}
	
	public FancyPanel test(Color color) {
		FancyPanel fp = new FancyPanel();
		fp.setBackground(color);
		return fp;
	}
	
	public void switchJComponentInView(FancyTab2 newTab) {
		remove(inView);
		inView = newTab.content;
		add(inView, BorderLayout.CENTER);
		
		revalidate();
		repaint();
	}
	
	public void addMouseListener() {
		tabs.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				tabs.mouseClickPosn = e.getX();		

				if(tabs.swappedRight) {
					tabs.swapRightReleaseTimer.start();
				} else if(tabs.swappedLeft) {
					tabs.swapLeftReleaseTimer.start();
				} else {
					tabs.returnTimer.start();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				tabs.pressedTab     = tabs.tabs.get(e.getX(), e.getY());
				tabs.mouseClickPosn = e.getX() - tabs.pressedTab.x;

				tabs.tabs.setSelectedIndex(tabs.pressedTab);

				switchJComponentInView(tabs.pressedTab);

				repaint();
			}
		});
	}

	public void addMouseMotionListener() {
		tabs.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				tabs.pressedTab.x = arg0.getX() - tabs.mouseClickPosn;

				try {					
					FancyTab2 rightTab = tabs.tabs.getRightTab(tabs.pressedTab);

					if(tabs.pressedTab.x + (tabs.pressedTab.width/2) > rightTab.x) {
						tabs.swappedTab = rightTab;
						tabs.swapRightTimer.start();
					}
				} catch (NoSuchElementException e) {}

				try {
					FancyTab2 leftTab = tabs.tabs.getLeftTab(tabs.pressedTab);

					if((tabs.pressedTab.x < leftTab.x + (leftTab.width/2))
							&& (tabs.pressedTab.x > leftTab.x)) {
						tabs.swappedTab = leftTab;
						tabs.swapLeftTimer.start();
					}

				} catch (NoSuchElementException e) {}

				revalidate();
				repaint();
			}
		});
	}
	
	/**
	 * This is where all of the tab magic happens.
	 * @author Alexander Porrello
	 */
	public class FancyTabOrganizer extends FancyPanel {
		private static final long serialVersionUID = 1L;

		FancyTabContainer tabs = new FancyTabContainer();

		public int tabHeight = 25;
		public int tabWidth  = 150;

		int mouseClickPosn;

		Timer swapRightTimer;
		Timer swapLeftTimer;
		Timer swapRightReleaseTimer;
		Timer swapLeftReleaseTimer;
		Timer returnTimer;

		FancyTab2 pressedTab;
		FancyTab2 swappedTab;

		Boolean swappedRight = false;
		Boolean swappedLeft  = false;

		public FancyTabOrganizer() {
			setPreferredSize(new Dimension(500, tabHeight + 5));

			makeSwapReleaseTimers();
			makeSwapTimers();
			makeReturnTimer();
		}

		public void addTab(String title, JComponent component) {
			tabs.add(new FancyTab2(title, tabWidth, tabHeight, tabs.size(), component));
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
					swappedRight = true;

					if(swappedTab.x > pressedTab.determineXPosition()) {
						swappedTab.x = swappedTab.x - 1;
						repaint();
					} else {
						swapRightTimer.stop();
					}
				}
			});

			swapLeftTimer = new Timer(0, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					swappedLeft = true;

					if(swappedTab.x < pressedTab.determineXPosition()) {
						swappedTab.x = swappedTab.x + 1;
						repaint();
					} else {
						swapLeftTimer.stop();
					}
				}
			});
		}

		/**
		 * Initializes the timers that are responsible for setting a released
		 *  tab in its proper position.
		 */
		public void makeSwapReleaseTimers() {
			swapRightReleaseTimer = new Timer(1, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					swappedRight = false;

					if(pressedTab.x < swappedTab.determineXPosition()) {
						pressedTab.x = pressedTab.x + 1;

						repaint();
					} else {
						swapTabIndices(pressedTab, swappedTab);
						swapRightReleaseTimer.stop();
					}
				}
			});

			swapLeftReleaseTimer = new Timer(1, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					swappedLeft = false;

					if(pressedTab.x > swappedTab.determineXPosition()) {
						pressedTab.x = pressedTab.x - 1;

						repaint();
					} else {
						swapTabIndices(pressedTab, swappedTab);
						swapLeftReleaseTimer.stop();
					}
				}
			});
		}

		/**
		 * Swaps the indices of two given tabs.
		 * @param a the first tab whose index is to be swapped
		 * @param b the second tab whose index is to be swapped
		 */
		public void swapTabIndices(FancyTab2 a, FancyTab2 b) {
			int indexA = a.index;
			int indexB = b.index;

			a.index = indexB;
			b.index = indexA;
		}

		

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D gg = (Graphics2D) g;
			gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			gg.setColor(Color.LIGHT_GRAY);
			gg.fillRect(0, 0, getWidth(), getHeight());

			Font a = new JButton().getFont();
			gg.setFont(new Font(a.getName(), a.getStyle(), 13));

			for(FancyTab2 tab : tabs) {
				gg.setColor(Color.LIGHT_GRAY);
				gg.fillRect(tab.x, tab.y, tabWidth, tabHeight);
				gg.setColor(Color.BLACK);
				gg.drawString(tab.text, tab.x + 6, tab.y + tabHeight-8);
				
//				if(tabs.getRightTab(tab).index != tabs.selected.index
//						&& tab.index != tabs.selected.index) {
//					gg.fillRect(1, 5, 1, 5);
//				}
						
			}

			gg.setColor(Color.decode("#F0F0F0"));
			gg.fillRect(0, tabHeight, getWidth(), 5);

			if(tabs.selected != null) {
				gg.fillRect(tabs.selected.x, tabs.selected.y, tabWidth, tabHeight);
				gg.setColor(Color.BLACK);
				gg.drawString(tabs.selected.text, tabs.selected.x + 6, tabs.selected.y + tabHeight-8);
			}
		}
	}

	public static void main(String[] args) {
		FancyFrame frame = new FancyFrame();
		frame.setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(new Dimension(400, 50));
		frame.add(new FancyTabPanelV2());
		frame.pack();

		frame.setVisible(true);
	}
}
