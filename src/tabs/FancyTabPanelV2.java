package tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.Timer;

import displays.FancyFrame;
import displays.FancyPanel;

public class FancyTabPanelV2 extends FancyPanel {
	private static final long serialVersionUID = 3467583173655155282L;

	FancyTabContainer tabs = new FancyTabContainer();

	public int tabHeight = 25;
	public int tabWidth  = 150;

	int mouseClickPosn;
	//int movingTabPosn;

	Timer swapRightTimer;
	Timer swapLeftTimer;
	Timer swapRightReleaseTimer;
	Timer swapLeftReleaseTimer;
	Timer returnTimer;

	FancyTab2 pressedTab;
	FancyTab2 swappedTab;

	Boolean swappedRight = false;
	Boolean swappedLeft  = false;
	
	public FancyTabPanelV2() {
		setPreferredSize(new Dimension(500, tabHeight));
		tabs.add(new FancyTab2("Testing", tabWidth, tabHeight, 0, new FancyPanel()));
		tabs.add(new FancyTab2("Testing2", tabWidth, tabHeight, 1, new FancyPanel()));
		tabs.add(new FancyTab2("Testing3", tabWidth, tabHeight, 2, new FancyPanel()));

		makeSwapReleaseTimers();
		makeSwapTimers();
		//TODO makeReturnTimer();
		addMouseListener();
		addMouseMotionListener();
	}
	
	public void makeSwapTimers() {
		swapRightTimer = new Timer(1, new ActionListener() {
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
		
		swapLeftTimer = new Timer(1, new ActionListener() {
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
	
	public void makeSwapReleaseTimers() {
		swapRightReleaseTimer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				swappedRight = false;
				
				if(pressedTab.x < swappedTab.determineXPosition()) {
					pressedTab.x = pressedTab.x + 1;

					repaint();
				} else {
					int indexA = pressedTab.posn;
					int indexB = swappedTab.posn;

					pressedTab.posn = indexB;
					swappedTab.posn = indexA;

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
					int indexA = pressedTab.posn;
					int indexB = swappedTab.posn;

					pressedTab.posn = indexB;
					swappedTab.posn = indexA;

					swapLeftReleaseTimer.stop();
				}
			}
		});
	}

	public void addMouseListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseClickPosn = e.getX();		

				if(swappedRight) {
					swapRightReleaseTimer.start();
				} else if(swappedLeft) {
					swapLeftReleaseTimer.start();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				pressedTab     = tabs.get(e.getX(), e.getY());
				mouseClickPosn = e.getX() - pressedTab.x;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO show window contents
			}
		});
	}

	public void addMouseMotionListener() {
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				pressedTab.x = arg0.getX() - mouseClickPosn;

				try {					
					FancyTab2 rightTab = tabs.getRightTab(pressedTab);

					if(pressedTab.x + (pressedTab.width/2) > rightTab.x) {
						swappedTab = rightTab;
						swapRightTimer.start();
					}
				} catch (NoSuchElementException e) {}

				try {
					FancyTab2 leftTab = tabs.getLeftTab(pressedTab);

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

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		gg.setColor(Color.LIGHT_GRAY);
		gg.fillRect(0, 0, getWidth(), getHeight());

		Font a = new JButton().getFont();
		gg.setFont(new Font(a.getName(), a.getStyle(), 13));

		for(FancyTab2 tab : tabs) {
			gg.setColor(Color.decode("#F0F0F0"));
			gg.fillRect(tab.x, tab.y, tabWidth, tabHeight);
			gg.setColor(Color.BLACK);
			gg.drawString(tab.text, tab.x + 6, tab.y + tabHeight-8);
		}
	}


	/**
	 * Contains several useful methods to quickly access tabs.
	 * @author Alexander Porrello
	 */
	public class FancyTabContainer extends ArrayList<FancyTab2> {
		private static final long serialVersionUID = -1272719320778131995L;

		public FancyTabContainer() {

		}

		/**
		 * Returns a tab if it has been clicked on.
		 * @param x the x posn of the cursor
		 * @param y the y posn of the cursor
		 * @return a tab if it has been clicked on.
		 */
		public FancyTab2 get(int x, int y) {
			for(FancyTab2 ft : this) {
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
		public FancyTab2 get(int posn) {
			for(FancyTab2 ft : this) {
				if(ft.posn == posn) {
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
		public FancyTab2 getRightTab(FancyTab2 ft) {
			if(ft.posn + 1 <= this.size()) {
				return get(ft.posn + 1);
			} else {
				throw new NoSuchElementException();
			}
		}

		/**
		 * Returns the tile to the left of a given tile.
		 * @param ft the given tile.
		 * @return the tile to the right of the given tile.
		 */
		public FancyTab2 getLeftTab(FancyTab2 ft) {
			if(ft.posn - 1 >= 0) {
				return get(ft.posn - 1);
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	public class FancyTab2 {

		public String text;

		public int x;
		public int y;
		public int width;
		public int height;

		public JComponent content;

		public int posn;

		public FancyTab2(String title, int width, int height, 
				int posn, JComponent content) {
			this.text     = title;
			this.width    = width;
			this.height   = height;
			this.content  = content;
			this.posn     = posn;
			this.y        = 0;
			this.x        = determineXPosition();
		}

		public int determineXPosition() {
			return (posn * width) + posn;
		}

		/**
		 * Checks if a given point is contained within this tab.
		 * @param p the point to check.
		 * @return true if the tab contains the point; else, false
		 */
		public boolean contains(Point p) {
			return new Rectangle(x, y, width, height).contains(
					new Point(p.x, p.y));
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
