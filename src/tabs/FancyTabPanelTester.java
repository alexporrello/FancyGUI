package tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import displays.FancyFrame;
import displays.FancyPanel;

public class FancyTabPanelTester extends FancyFrame {
	private static final long serialVersionUID = -2169385802854160046L;

	public FancyTabPanelTester() {
		setLayout(new BorderLayout());
		
		ArrayList<FancyTab> tabs = new ArrayList<FancyTab>();
		tabs.add(new FancyTab("Hello!", new FancyPanel()));
		tabs.add(new FancyTab("Hello 2!", new FancyPanel()));
		tabs.add(new FancyTab("Hello 3!", new FancyPanel()));
		
		add(new FancyTabPanel(tabs), BorderLayout.NORTH);
		
		setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900, 150));
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new FancyTabPanelTester();
	}
}
