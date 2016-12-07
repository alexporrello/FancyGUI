package tabs;

import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;

import layout.GBC;
import displays.FancyComponentUtils;
import displays.FancyPanel;

public class FancyTabPanel extends FancyPanel {
	private static final long serialVersionUID = -4570311860949079409L;

	private ArrayList<FancyTab> tabs = new ArrayList<FancyTab>();

	public FancyPanel tabPanel = new FancyPanel();

	public JComponent current;

	public FancyTabPanel() {
		setUp();
	}

	public FancyTabPanel(ArrayList<FancyTab> allTabs) {
		for(FancyTab ft : allTabs) {
			addTab(ft);
		}

		setUp();
		addAllTabs();
	}

	private void setUp() {
		setLayout(new GridBagLayout());
		setBackground(FancyTab.mouseOff);
		setUpTabs();
	}

	private void setUpTabs() {
		tabPanel.setLayout(null);//new GridBagLayout());
		tabPanel.setBackground(FancyTab.mouseOff);
		FancyComponentUtils.setFixedSize(tabPanel, 120, 30);

		GBC.addWithGBC(this, tabPanel, 1.0, 0.0, 0, 0, GBC.HORIZ, GBC.NORTHWEST, 
				GBC.insets(0, 0, 0, 0), 1);

		revalidate();
		repaint();
	}

	private void addAllTabs() {
		tabPanel.removeAll();

		int x = 0;
		int y = 0;

		for(FancyTab ft : tabs) {

			tabPanel.add(ft);

			if(ft.drawingOffset != 0) {
				ft.setBounds(ft.drawingOffset, y, 120, 25);
				ft.drawingOffset = 0;
			} else {
				ft.setBounds(x, y, 120, 25);
			}

			x+= ft.getWidth();
		}

		FancyPanel underline = new FancyPanel();
		FancyComponentUtils.setFixedSize(underline, 300, 5);
		underline.setBackground(FancyTab.selected);

		tabPanel.add(underline);
		underline.setBounds(0, 25, getWidth(), 5);

		tabPanel.revalidate();
		tabPanel.repaint();
	}

	public void closeTab(FancyTab tab) {
		if(tab != null && tabPanel != null) {
			tabs.remove(tab);

			if(current != null) {
				remove(current);
			}

			current = new FancyPanel();
			GBC.addWithGBC(this, current, 1.0, 1.0, 0, 1, GBC.BOTH, GBC.CENT, GBC.insets(0, 0, 0, 0), 1);

			addAllTabs();

			revalidate();
			repaint();
		}
	}

	public void addTab(FancyTab tab) {
		setUpTab(tab);
		tabs.add(tab);
		setTabSelected(tab);

		addAllTabs();
	}

	private void setUpTab(FancyTab tab) {
		tab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!tab.isSelected) {
					for(FancyTab ft2 : tabs) {
						if(tabs.indexOf(ft2) == tabs.indexOf(tab)-1) {
							ft2.revalidate();
							ft2.repaint();
						}
					}

					tab.setMouseOverColor(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!tab.isSelected) {
					for(FancyTab ft2 : tabs) {
						if(tabs.indexOf(ft2) == tabs.indexOf(tab)-1) {
							if(!ft2.isSelected) {
								ft2.setSelected(false);
							}
						}
					}

					tab.setMouseOverColor(false);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				for(FancyTab ft2 : tabs) {
					ft2.setSelected(false);

					if(tabs.indexOf(ft2) == tabs.indexOf(tab)-1) {
						ft2.revalidate();
						ft2.repaint();
					}
				}

				tab.setSelected(true);
				addPanelToView(tab);
			}
		});

		tab.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				//tab.drawingOffset = e.getX();
				//	addAllTabs();

				for(FancyTab t : tabs) {
					if(t != tab) {
						if(e.getX() > t.getX() && t.getX() > tab.getX()) {
							swapTabPositions(t, tab);
							break;
						}

						if(e.getX() < t.getX() && t.getX() < tab.getX()) {
							swapTabPositions(tab, t);
							break;
						}
					}
				}
			}
		});
	}

	private void swapTabPositions(FancyTab t, FancyTab tab) {
		int toReplace = tabs.indexOf(t);

		FancyTab backup = tab.clone();

		tabs.set(tabs.indexOf(tab), t);
		tabs.set(toReplace, backup);

		if(tab.isSelected) {
			setTabSelected(backup);
		} else if(t.isSelected) {
			setTabSelected(t);
		}

		addAllTabs();
	}

	public void setTabSelected(FancyTab tab) {
		for(FancyTab ft2 : tabs) {
			ft2.setSelected(false);

			if(tabs.indexOf(ft2) == tabs.indexOf(tab)-1) {
				ft2.revalidate();
				ft2.repaint();
			}
		}

		tab.setSelected(true);
		addPanelToView(tab);
	}

	public void addPanelToView(FancyTab tab) {
		if(current != null) {
			remove(current);
		}

		current = tab.component;
		GBC.addWithGBC(this, current, 1.0, 1.0, 0, 1, GBC.BOTH, GBC.CENT, GBC.insets(0, 0, 0, 0), 1);

		current.revalidate();
		current.repaint();
	}
}
