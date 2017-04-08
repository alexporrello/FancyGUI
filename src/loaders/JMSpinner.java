package loaders;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import displays.JMPanel;

public class JMSpinner extends JMPanel {
	private static final long serialVersionUID = 1763641511940382618L;

	private int frame = 0;

	private String size;

	private Image[] images = new Image[8];

	public JMSpinner(SpinnerSize spinnerSize) {
		this.setPreferredSize(new Dimension(spinnerSize.width, spinnerSize.height));
		this.setSize(new Dimension(spinnerSize.width, spinnerSize.height));

		size = spinnerSize.width + "x" + spinnerSize.height + ".png";

		images[0] = new ImageIcon(JMSpinner.class.getClass().getResource("/01_" + size)).getImage();
		images[1] = new ImageIcon(JMSpinner.class.getClass().getResource("/02_" + size)).getImage();
		images[2] = new ImageIcon(JMSpinner.class.getClass().getResource("/03_" + size)).getImage();
		images[3] = new ImageIcon(JMSpinner.class.getClass().getResource("/04_" + size)).getImage();
		images[4] = new ImageIcon(JMSpinner.class.getClass().getResource("/05_" + size)).getImage();
		images[5] = new ImageIcon(JMSpinner.class.getClass().getResource("/06_" + size)).getImage();
		images[6] = new ImageIcon(JMSpinner.class.getClass().getResource("/07_" + size)).getImage();
		images[7] = new ImageIcon(JMSpinner.class.getClass().getResource("/08_" + size)).getImage();

		Timer timer = new Timer(120, this::spin);
		timer.setInitialDelay(0);
		timer.start();
	}

	private void spin(ActionEvent e) {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D gg = (Graphics2D) g;

		if (frame == images.length) {
			frame = 0;
		}

		gg.setColor(this.getBackground());
		gg.fillRect(0, 0, getWidth(), getHeight());
		gg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gg.drawImage(images[frame], 0, 0, null);

		frame++;
	}

	public enum SpinnerSize {
		SIZE_16x16(16, 16), SIZE_32x32(32, 32), SIZE_48x48(48, 48);

		int width;
		int height;

		SpinnerSize(int width, int height) {
			this.width = width;
			this.height = height;
		}
	}
}
