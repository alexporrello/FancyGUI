package displays;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import text.FancyTextArea;
import colors.FancyColor;

public class ExpandablePanel extends FancyPanel {
	private static final long serialVersionUID = 149674684797815832L;

	int x = 0;

	boolean move = false;

	private JComponent content = new FancyPanel();
	private FancyPanel moveClick = new FancyPanel();

	private Anchor anchor;
	
	public ExpandablePanel(JComponent c, Anchor anchor) {
		this.anchor = anchor;
		this.content = c;
		
		setUpPanel();
	}

	public ExpandablePanel(Anchor anchor) {
		this.anchor = anchor;
		
		setUpPanel();
	}

	public void setUpPanel() {
		
		
//		moveClick = new FancyPanel() {
//			private static final long serialVersionUID = 2272922984775118888L;
//
//			@Override
//			public void paintComponent(Graphics g) {
//				Graphics2D gg = (Graphics2D) g;
//				
//				gg.setColor(FancyColor.BLACK);
//				
//			}
//		};		
		
		super.setLayout(new BorderLayout());
		super.add(moveClick, anchor.anchor);
		super.add(content, BorderLayout.CENTER);

		
		if(anchor == Anchor.NORTH || anchor == Anchor.SOUTH) {
			moveClick.setPreferredSize(new Dimension(getWidth(), 3));
		} else {
			moveClick.setPreferredSize(new Dimension(3, getHeight()));
		}

		moveClick.setBackground(FancyColor.DARK_BLUE);
		content.setBackground(FancyColor.LIGHT_BLUE);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				move = false;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(conditions(e.getPoint())) {
					setCursor();
					move = true;
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(move && minWidth(e.getPoint())) {
					reSize(e.getPoint());

					revalidate();
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(conditions(e.getPoint())) {
					setCursor();
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}

	private boolean minWidth(Point p) {
		if(anchor == Anchor.NORTH) {
			return p.y > 3;
		} else if(anchor == Anchor.EAST) {
			return p.x < getWidth()-3;
		} else if(anchor == Anchor.SOUTH) {
			return p.y < getHeight()-3;
		} else if(anchor == Anchor.WEST) {
			return p.x > 3;
		} else {
			return false;
		}
	}
	
	private void reSize(Point p) {
		if(anchor == Anchor.NORTH) {
			setPreferredSize(new Dimension(getWidth(), p.y));
		} else if(anchor == Anchor.EAST) {
			setPreferredSize(new Dimension(getWidth()-p.x, getHeight()));
		} else if(anchor == Anchor.SOUTH) {
			setPreferredSize(new Dimension(getWidth(), getHeight()-p.y));
		} else if(anchor == Anchor.WEST) {
			setPreferredSize(new Dimension(p.x, getHeight()));
		}
	}
	
	private Boolean conditions(Point p) {
		if(anchor == Anchor.NORTH) {
			return p.y > getHeight()-6 && p.y < getHeight()+3;
		} else if(anchor == Anchor.EAST) {
			return p.x > -3 && p.x < 6;
		} else if(anchor == Anchor.SOUTH) {
			return p.y > -3 && p.y < 6;
		} else if(anchor == Anchor.WEST) {
			return p.x < getWidth()+3 && p.x > getWidth()-6;
		} else {
			return false;
		}
	}
	
	private void setCursor() {
		if(anchor == Anchor.NORTH) {
			setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
		} else if(anchor == Anchor.EAST) {
			setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		} else if(anchor == Anchor.SOUTH) {
			setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		} else if(anchor == Anchor.WEST) {
			setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
		} 
	}

	@Override
	public Component add(Component comp) {
		return content.add(comp);
	}

	@Override
	public void setLayout(LayoutManager mgr) {
		if(content != null) {
			content.setLayout(mgr);
		}
	}

	@Override
	public void removeAll() {
		content.removeAll();
	}


	public enum Anchor {
		NORTH(BorderLayout.SOUTH), SOUTH(BorderLayout.NORTH), 
		EAST(BorderLayout.WEST), WEST(BorderLayout.EAST);

		String anchor;

		Anchor(String anchor) {
			this.anchor = anchor;
		}

	}


	public static void main(String[] args) {
		FancyFrame frame = new FancyFrame();
		frame.setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		ExpandablePanel ep = new ExpandablePanel(Anchor.NORTH);
		ep.setPreferredSize(new Dimension(200, 300));
		frame.add(ep, BorderLayout.NORTH);
		
		ExpandablePanel ep2 = new ExpandablePanel(Anchor.EAST);
		ep2.setLayout(new BorderLayout());
		ep2.add(new FancyScrollPane(
				new FancyTextArea("This is a test of the expandability of this thing")), 
				BorderLayout.CENTER);
		ep2.setPreferredSize(new Dimension(200, 300));
		frame.add(ep2, BorderLayout.EAST);
		
		ExpandablePanel ep3 = new ExpandablePanel(Anchor.SOUTH);
		ep3.setLayout(new BorderLayout());
		ep3.add(new FancyScrollPane(
				new FancyTextArea("This is a test of the expandability of this thing")), 
				BorderLayout.CENTER);
		ep3.setPreferredSize(new Dimension(200, 300));
		frame.add(ep3, BorderLayout.SOUTH);

		ExpandablePanel ep4 = new ExpandablePanel(Anchor.WEST);
		ep4.setPreferredSize(new Dimension(200, 300));
		frame.add(ep4, BorderLayout.WEST);
		
		FancyPanel fp = new FancyPanel();
		fp.setPreferredSize(new Dimension(300, 300));
		frame.add(fp, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);
	}
}
