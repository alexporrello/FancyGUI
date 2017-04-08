package displays;

import icons.FancyIcon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import clickables.JMButton;
import colors.JMColor;
import colors.HoverColor;

public class JMScrollPane extends JScrollPane {
	private static final long serialVersionUID = -5458523733486110315L;

	public static final int HORIZ_SCROLL_NEVER = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
	public static final int VERT_SCROLL_NEVER  = JScrollPane.VERTICAL_SCROLLBAR_NEVER;

	private Boolean showButtons = true;

	private Color mouse_pressed = JMColor.decode("#606060");
	private Color mouse_over    = JMColor.decode("#a6a6a6");
	private Color mouse_off     = JMColor.decode("#cdcdcd");

	private Color scrollThumbColor = JMColor.decode("#e2e2e2");
	private Color scrollBackground = JMColor.decode("#f0f0f0");

	private Boolean mousePressed = false;

	private int drawWidth = 3;

	public JMScrollPane(JComponent c) {
		super(c);

		setUpPanel();
	}

	public JMScrollPane(JComponent c, Boolean showButtons) {
		super(c);

		this.showButtons = showButtons;

		setUpPanel();
	}

	public JMScrollPane(JComponent c, Boolean showButtons, 
			Color scrollDisplayColor, Color scrollBackground) {
		super(c);

		this.showButtons = showButtons;
		this.scrollThumbColor = scrollDisplayColor;
		this.scrollBackground = scrollBackground;

		setUpPanel();
	}

	private void setUpPanel() {
		setBorder(BorderFactory.createEmptyBorder());
		setScrollBarWidth(drawWidth);

		setScrollBarUI();
	}

	public void setButtonsVisible(Boolean visible) {
		this.showButtons = visible;
		setScrollBarUI();
	}

	private void setScrollBarUI() {
		super.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createDecreaseButton(int orientation) {
				if(showButtons) {
					return makeScrollButton(FancyIcon.LEFT_BLACK_16x16);
				} else {
					return createZeroButton();
				}
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				if(showButtons) {
					return makeScrollButton(FancyIcon.RIGHT_BLACK_16x16);
				} else {
					return createZeroButton();
				}
			}

			@Override 
			protected void paintTrack(Graphics g, JComponent c, 
					Rectangle trackBounds) {

				g.setColor(scrollBackground); // Fill the background
				g.fillRect(0, 0, getWidth(), getHeight());
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, 
					Rectangle thumbBounds) {

				int leftBounds  = thumbBounds.x;
				int rightBounds = thumbBounds.x + thumbBounds.width;				
				
				c.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						if(mousePressed) {
							scrollThumbColor = mouse_pressed;
						} else {
							scrollThumbColor = mouse_off;
						}

						repaint();
					}

					@Override
					public void mousePressed(MouseEvent e) {
						mousePressed = true;
						
						if(e.getX() > leftBounds && e.getX() < rightBounds) {
							scrollThumbColor = mouse_pressed;
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						mousePressed = false;
						
						if(inBounds(e, thumbBounds)) {
							scrollThumbColor = mouse_over;
						} else {
							scrollThumbColor = mouse_off;
						}

						repaint();
					}

				});
				
				c.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						if(e.getY() > leftBounds && e.getY() < rightBounds) {
							if(!mousePressed) {
								scrollThumbColor = mouse_over;
							} 

							repaint();
						}else {
							if(!mousePressed) {
								scrollThumbColor = mouse_off;
							} 
						}
					}
				});

				g.setColor(scrollThumbColor);
				g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, drawWidth);
			}

			@Override
			protected void configureScrollBarColors() {
				super.configureScrollBarColors();
			}
		});
		
		super.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected JButton createDecreaseButton(int orientation) {
				if(showButtons) {
					return makeScrollButton(FancyIcon.UP_BLACK_16x16);
				} else {
					return createZeroButton();
				}
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				if(showButtons) {
					return makeScrollButton(FancyIcon.DOWN_BLACK_16x16);
				} else {
					return createZeroButton();
				}
			}

			@Override 
			protected void paintTrack(Graphics g, JComponent c, 
					Rectangle trackBounds) {

				g.setColor(scrollBackground); // Fill the background
				g.fillRect(0, 0, getWidth(), getHeight());
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, 
					Rectangle thumbBounds) {

				g.setColor(scrollThumbColor);

				int upperBounds = thumbBounds.y;
				int lowerBounds = thumbBounds.y + thumbBounds.height;				
				
				c.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent e) {
						if(mousePressed) {
							scrollThumbColor = mouse_pressed;
						} else {
							scrollThumbColor = mouse_off;
						}

						repaint();
					}

					@Override
					public void mousePressed(MouseEvent e) {
						mousePressed = true;
						
						if(e.getY() > upperBounds && e.getY() < lowerBounds) {
							scrollThumbColor = mouse_pressed;
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						mousePressed = false;
						
						if(inBounds(e, thumbBounds)) {
							scrollThumbColor = mouse_over;
						} else {
							scrollThumbColor = mouse_off;
						}

						repaint();
					}

				});
				
				c.addMouseMotionListener(new MouseMotionAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						if(e.getY() > upperBounds && e.getY() < lowerBounds) {
							if(!mousePressed) {
								scrollThumbColor = mouse_over;
							} 

							repaint();
						}else {
							if(!mousePressed) {
								scrollThumbColor = mouse_off;
							} 
						}
					}
				});

				g.fillRect(thumbBounds.x, thumbBounds.y, drawWidth-1, thumbBounds.height);
			}

			@Override
			protected void configureScrollBarColors() {
				super.configureScrollBarColors();
			}
		});
	}
	
	private boolean inBounds(MouseEvent e, Rectangle thumbBounds) {
		int upperBounds = thumbBounds.y;
		int lowerBounds = thumbBounds.y + thumbBounds.height;
		int leftBounds  = thumbBounds.x;
		int rightBounds = thumbBounds.x + thumbBounds.width;
		
		Boolean cond1 = e.getY() > upperBounds;
		Boolean cond2 = e.getY() < lowerBounds;
		Boolean cond3 = e.getX() > leftBounds;
		Boolean cond4 = e.getX() < rightBounds;
		
		return cond1 && cond2 && cond3 && cond4;
	}

	private JMButton makeScrollButton(ImageIcon icon) {
		JMButton button = new JMButton(icon);
		button.setColor(new HoverColor(scrollThumbColor, scrollBackground));
		button.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		button.setPreferredSize(new Dimension(17, 17));
		button.setFocusable(false);
		return button;
	}

	private JButton createZeroButton() {
		JButton jbutton = new JButton();

		jbutton.setPreferredSize(new Dimension(0, 0));
		jbutton.setMinimumSize(new Dimension(0, 0));
		jbutton.setMaximumSize(new Dimension(0, 0));

		return jbutton;
	}

	public void setScrollBarWidth(int drawWidth) {
		this.drawWidth = drawWidth;
		this.getVerticalScrollBar().setPreferredSize(
				new Dimension(this.drawWidth, 0));
		this.getHorizontalScrollBar().setPreferredSize(
				new Dimension(0, this.drawWidth));

		this.revalidate();
		this.repaint();
	}
}
