package displays;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JMFrame extends JFrame {
	private static final long serialVersionUID = -1028890050573574197L;

	public JMFrame() {
		setLookAndFeel();
	}
	
	/**
	 * Sets the FancyFrame's look and feel to the current OS's look and feel.
	 */
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
