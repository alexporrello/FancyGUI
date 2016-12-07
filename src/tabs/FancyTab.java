package tabs;

import icons.FancyIcon;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import layout.GBC;
import text.FancyLabel;
import clickables.FancyButton;
import colors.FancyColor;
import colors.HoverColor;
import displays.FancyComponentUtils;
import displays.FancyPanel;

public class FancyTab extends FancyPanel implements Cloneable {
	private static final long serialVersionUID = -7726386930856586263L;

	public static final Color mouseOff  = FancyColor.decode("#CCCCCC");
	public static final Color mouseOver = FancyColor.decode("#E6E6E6");
	public static final Color selected  = FancyColor.decode("#F2F2F2");

	public FancyLabel title;

	private FancyButton close = new FancyButton(FancyIcon.EMPTY);

	boolean isSelected = false;

	public JComponent component;

	public int drawingOffset = 0;
	
	public FancyTab(String name, JComponent component) {
		this.component = component;

		setLayout(new GridBagLayout());
		setBackground(mouseOff);

		setupTitleLabel(name);
		setupCloseButton();

		makeTab();		
	}

	/**
	 * Sets up the label that displays the tab's name.
	 * @param name the tab's given name.
	 */
	private void setupTitleLabel(String name) {
		title = new FancyLabel(name);
		title.setOpaque(false);
		title.setHorizontalAlignment(SwingConstants.LEFT);
		FancyComponentUtils.setFixedSize(title, 120, 25);
	}

	/**
	 * Sets up the close button. <br>
	 * <b>NOTE:</b> This method will not close out the tab. The close action
	 * must be defined in the program.
	 */
	private void setupCloseButton() {
		FancyComponentUtils.setFixedSize(close, 25, 15);
		close.setColor(new HoverColor(mouseOff, mouseOff));
		close.setOpaque(false);
		close.setFocusable(true);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!isSelected) {
					setBackground(mouseOver);
					close.setIcon(FancyIcon.DELETE_BLACK_16x16);
					close.setColor(new HoverColor(mouseOver, mouseOver));
				} else {
					close.setIcon(FancyIcon.DELETE_RED);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!isSelected) {
					setBackground(mouseOff);
					close.setColor(new HoverColor(mouseOff, mouseOff));
					close.setIcon(FancyIcon.EMPTY);
				} else {
					close.setIcon(FancyIcon.DELETE_BLACK_16x16);
				}
			}
		});
	}

	/**
	 * Sets up the tab.
	 */
	private void makeTab() {
		int x = 0;

		GBC.addWithGBC(this, title, 1.0, 0.0, x++, 0, 
				GBC.VERT, GBC.FIRST_LINE_START, GBC.insets(2, 0, 2, 0), 1);	
		GBC.addWithGBC(this, close, 0.0, 0.0, x++, 0, 
				GBC.VERT, GBC.EAST, GBC.insets(2, 0, 2, 0), 1);	
	}

	public void setMouseOverColor(Boolean hovered) {
		if(hovered) {
			setBackground(FancyTab.mouseOver);
			close.setColor(new HoverColor(FancyTab.mouseOver, FancyTab.mouseOver));
			close.setIcon(FancyIcon.DELETE_BLACK_16x16);
		} else {
			setBackground(FancyTab.mouseOff);
			close.setColor(new HoverColor(FancyTab.mouseOff, FancyTab.mouseOff));
			close.setIcon(FancyIcon.EMPTY);
		}
	}

	/**
	 * Used to add an ActionEvent to the close button.
	 * @param listener is the ActionEvent to be added.
	 */
	public void addActionToCloseButton(Consumer<ActionEvent> listener) {
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.accept(e);
			}
		});
	}

	public void setSelected(Boolean selected) {
		this.isSelected = selected;

		if(selected) {
			setBackground(FancyTab.selected);
			close.setColor(new HoverColor(FancyTab.selected, FancyTab.selected));
			close.setIcon(FancyIcon.DELETE_BLACK_16x16);
		} else {
			setBackground(mouseOff);
			close.setColor(new HoverColor(mouseOff, mouseOff));
			close.setIcon(FancyIcon.EMPTY);
		}

		revalidate();
		repaint();
	}
	
	@Override
	public FancyTab clone() {
		return this;
	}
}