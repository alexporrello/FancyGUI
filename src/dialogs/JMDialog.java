package dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import layout.GBC;
import text.JMLabel;
import text.Word;
import clickables.JMButton;
import colors.HoverColor;
import colors.JMColor;
import displays.JMPanel;

/**
 * Generic dialog.
 * @author Alexander Porrello
 */
public class JMDialog extends JDialog {
	private static final long serialVersionUID = 4117907837710043293L;

	/** The label that displays the message to the user **/
	JMLabel messageLabel = new JMLabel();

	/** The panel upon which the buttons and message are painted **/
	JMPanel mainPanel = new JMPanel();

	/** The panel that displays the confirm and cancel buttons **/
	JMPanel buttons = new JMPanel();

	JMButton confirmButton;

	JMButton denyButton;

	JMButton cancelButton;

	public DialogResult result = DialogResult.NONE_SELECTED;
	
	private Color background = JMColor.decode("#ffffff");
	private Color confirm_mouse_over = JMColor.decode("#0078d7");
	private Color deny_mouse_over = JMColor.DARK_RED;
	private Color mouse_off = JMColor.decode("#e1e1e1");
	private Color text = JMColor.decode("#000000");

	/**
	 * This is a two option dialogue, to be used when there is a "yes" option, 
	 * and a "cancel" option.
	 * 
	 * @param name is the name of the window
	 * @param message is the message for the user
	 * @param confirmText is the text of the "yes" option
	 * @param confirmListener is the "yes" action
	 */
	public JMDialog(String name, String message, String confirmText, 
			ActionListener confirmListener) {

		createDialog(name, message);

		confirmButton.setText(confirmText);
		confirmButton.addActionListener(e -> {
			confirmListener.actionPerformed(e);
			result = DialogResult.CONFIRM;
			dispose();
		});
		
		cancelButton.addActionListener(e -> {
			result = DialogResult.CANCEL;
			dispose();
		});

		GBC.addWithGBC(buttons, confirmButton, 0.0, 0, 0, 
				GBC.NONE, new Insets(0, 0, 0, 10), 1);
		GBC.addWithGBC(buttons, cancelButton,  0.0, 1, 0, 
				GBC.NONE, new Insets(0, 0, 0, 10), 1);

		GBC.addWithGBC(mainPanel, messageLabel, 1.0, 0, 0, 
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),  1);
		GBC.addWithGBC(mainPanel, buttons,      0.0, 0, 1, 
				GridBagConstraints.HORIZONTAL, new Insets(12, 3, 3, 0), 1);

		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * This is a three option dialogue, to be used when there is a "yes" option, 
	 * a "no" option, and a "cancel" option.
	 * 
	 * @param name is the name of the window
	 * @param message is the message for the user
	 * @param confirmText is the text of the "yes" option
	 * @param denyText is the text of the "no" option
	 * @param confirmListener is the "yes" action
	 * @param denyListener is the "no" action
	 */
	public JMDialog(String name, String message, String confirmText, 
			String denyText, ActionListener confirmListener,
			ActionListener denyListener) {

		createDialog(name, message);

		confirmButton.setText(confirmText);
		confirmButton.addActionListener(e -> {
			confirmListener.actionPerformed(e);
			result = DialogResult.CONFIRM;
			dispose();
		});

		denyButton.setText(denyText);
		denyButton.addActionListener(e -> {
			denyListener.actionPerformed(e);
			result = DialogResult.DENY;
			dispose();
		});

		cancelButton.addActionListener(e -> {
			result = DialogResult.CANCEL;
			dispose();
		});

		GBC.addWithGBC(buttons, confirmButton, 0.0, 0, 0, 
				GBC.NONE, new Insets(0, 0, 0, 10), 1);
		GBC.addWithGBC(buttons, denyButton,    0.0, 1, 0, 
				GBC.NONE, new Insets(0, 0, 0, 10), 1);
		GBC.addWithGBC(buttons, cancelButton,  0.0, 2, 0, 
				GBC.NONE, new Insets(0, 0, 0, 10), 1);

		GBC.addWithGBC(mainPanel, messageLabel, 1.0, 0, 0,
				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 6, 0),  1);
		GBC.addWithGBC(mainPanel, buttons,      0.0, 0, 1,
				GridBagConstraints.HORIZONTAL, new Insets(12, 3, 3, 0), 1);

		add(mainPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds all of the components to a dialog and sets it up.
	 * 
	 * @param name is the name to be displayed atop the dialog.
	 * @param message is the dialog's message.
	 * @param width is the width of the dialog window.
	 * @param height is the height of the dialog window.
	 */
	private void createDialog(String name, String message) {
		setTitle(name);
		setResizable(false);
		setModalExclusionType(JMDialog.ModalExclusionType.TOOLKIT_EXCLUDE);
		setModal(true);
		setLayout(new BorderLayout());
		Font font = getDefaultSystemFont();

		/** Determine window size **/

		Word      word       = new Word(message, font);
		Dimension windowSize = new Dimension(0, 0);

		windowSize = new Dimension(word.width + 40, 115);

		setSize(windowSize);

		/** Set up the main panel **/

		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(background);

		/** Set up the messages label **/

		messageLabel = new JMLabel(message);
		messageLabel.setFont(font);
		messageLabel.setForeground(text);
		messageLabel.setBorder(BorderFactory.createEmptyBorder());
		messageLabel.setOpaque(false);

		/** Set up the buttons **/

		Dimension buttonSize = new Dimension(80, 22);

		confirmButton = new JMButton("");
		confirmButton.setColor(new HoverColor(confirm_mouse_over, mouse_off));
		confirmButton.setPreferredSize(buttonSize);
		confirmButton.setFont(font);

		denyButton = new JMButton("");
		denyButton.setColor(new HoverColor(deny_mouse_over, mouse_off));
		denyButton.setPreferredSize(buttonSize);
		denyButton.setFont(font);

		cancelButton  = new JMButton("Cancel");
		cancelButton.addActionListener(e -> dispose()); 
		cancelButton.setColor(new HoverColor(confirm_mouse_over, mouse_off));
		cancelButton.setPreferredSize(buttonSize);
		cancelButton.setFont(font);

		confirmButton.font = JMColor.BLACK;
		denyButton.font    = JMColor.BLACK;
		cancelButton.font  = JMColor.BLACK;		

		buttons.setLayout(new GridBagLayout());
		buttons.setOpaque(false);
	}

	/**
	 * Discovers the default system font form a JLabel.
	 * @return the default system font.
	 */
	private Font getDefaultSystemFont() {
		return new Font(
				new JLabel().getFont().getName(), 
				new JLabel().getFont().getStyle(), 
				new JLabel().getFont().getSize());
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JMDialog text = new JMDialog("Adobe Premier Pro CC", 
				"This project already exists. Confirm overwrite? "
						+ "This action cannot be undone.", "Yes", "No", 
						e -> {}, e -> {});
		text.setVisible(true);
		text.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
