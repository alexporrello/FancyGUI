package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JDialog;

import layout.GBC;
import loaders.JMSpinner;
import loaders.JMSpinner.SpinnerSize;
import text.JMLabel;
import clickables.JMButton;
import colors.HoverColor;
import displays.JMPanel;

public class SpinnerDialog extends JDialog {
	private static final long serialVersionUID = 6304551518964199589L;

	public JMButton cancel = new JMButton("Cancel");

	private boolean showCancel = true;

	public SpinnerDialog(ArrayList<Image> imageIcon, String processTitle) {
		initialConfig(imageIcon);		
		makeGUI(processTitle);
	}
	
	public SpinnerDialog(ArrayList<Image> imageIcon, String processTitle, Boolean showCancel) {
		this.showCancel = showCancel;
		
		initialConfig(imageIcon);		
		makeGUI(processTitle);
	}

	public void initialConfig(ArrayList<Image> imageIcon) {
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setIconImages(imageIcon);
		this.setLayout(new BorderLayout());
		this.setModal(true);
		this.setResizable(false);
	}

	public void makeGUI(String processTitle) {
		cancel.setFocusable(false);

		JMPanel loading = new JMPanel();
		loading.setLayout(new GridBagLayout());
		GBC.addWithGBC(loading, new JMLabel(processTitle), 1.0, 1.0, 0, 0,
				GBC.CENTER, GBC.NORTH, GBC.insets(15, 0, 5, 0), 1);
		GBC.addWithGBC(loading, new JMSpinner(SpinnerSize.SIZE_48x48), 1.0, 1.0, 0, 1,
				GBC.CENTER, GBC.NORTH, GBC.insets(0, 115, 25, 115), 1);			

		add(loading, BorderLayout.CENTER);

		if(showCancel) {
			add(cancel, BorderLayout.SOUTH);
		}

		this.pack();
	}

	public void setCancelButtonColors(HoverColor color, Color font) {
		cancel.setColor(color);
		cancel.font = font;
	}
}
