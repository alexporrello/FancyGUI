package text;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.sun.glass.events.KeyEvent;

import displays.FancyComponentUtils;
import displays.FancyPanel;

public class FormLabel extends FancyPanel {
	private static final long serialVersionUID = 1128543808968130465L;

	public FancyTextField field;

	public FancyLabel label;

	public FormLabel(String s, int width, int height) {
		setLayout(new BorderLayout());
		setOpaque(false);

		label = new FancyLabel(s);
		label.setOpaque(false);
		label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.addMouseClickedListener(e -> {
			if(e.getClickCount() == 2) {
				field.setText(label.getText());
				changeComponent(field);
			}
		});

		field = new FancyTextField(s);
		field.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		field.addFocusLostListener(e -> {
			label.setText(field.getText());
			changeComponent(label);
		});
		field.addKeyPressListener(e -> {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				label.setText(field.getText());
				changeComponent(label);
			}
		});

		setSize(width, height);
		add(label, BorderLayout.CENTER);
	}

	/**
	 * Changes the component currently in view.
	 * @param component is the component to change.
	 */
	private void changeComponent(JComponent component) {
		removeAll();
		this.add(component, BorderLayout.CENTER);

		if(component.equals(field)) {
			field.selectAll();
			component.requestFocus();
		} 

		revalidate();
		repaint();
	}

//	public void setForeground(Color foreground) {
//		label.setForeground(foreground);
//		field.setForeground(foreground);
//	}
//	
//	public void setOpaque(Boolean opaque) {
//		label.setOpaque(opaque);
//		field.setOpaque(opaque);
//	}
	
	public void setFontSize(int fontSize) {
		label.setFontSize(fontSize);
		field.setFontSize(fontSize);
	}

	public void setSize(int width, int height) {
		FancyComponentUtils.setFixedSize(this, width, height);
		FancyComponentUtils.setFixedSize(label, width, height);
		FancyTextUtils.setFixedSize(field, width, height);
		revalidate();
		repaint();
	}

	public String getText() {
		return field.getText();
	}
}