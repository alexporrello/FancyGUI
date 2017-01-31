package tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import displays.FancyFrame;
import displays.FancyPanel;

public class FancyTabPanelTester extends FancyFrame {
	private static final long serialVersionUID = -2169385802854160046L;

	public FancyTabPanelTester() {
		setLayout(new BorderLayout());
		
		FancyPanel a = new FancyPanel();
		FancyPanel b = new FancyPanel();
		FancyPanel c = new FancyPanel();
		
		a.setBackground(Color.BLACK);
		b.setBackground(Color.BLACK);
		c.setBackground(Color.BLACK);
		
		ArrayList<FancyTab> tabs = new ArrayList<FancyTab>();
		tabs.add(new FancyTab("Hello 1!", a));
		tabs.add(new FancyTab("Hello 2!", b));
		tabs.add(new FancyTab("Hello 3!", c));
		
		add(new FancyTabPanel(tabs), BorderLayout.NORTH);
		
		setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900, 150));
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new FancyTabPanelTester();
	}
}
