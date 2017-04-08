package loaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import colors.JMColor;

public class JMProgressBar extends JPanel {
	private static final long serialVersionUID = -2404702751477071566L;

	/** The number of tasks until finished **/
	public double numTasks;
	
	/** The number of tasks that have been completed **/
	private double completedTasks = 0;
	
	/** How far to draw the progress bar **/
	private int drawWidth = 0;
	
	/** The color of the progress bar **/
	public Color front = JMColor.DARK_GRAY;
	
	/** The color behind the progress bar **/
	public Color back = JMColor.LIGHT_GRAY;
	
	public JMProgressBar(int numTasks) {
		this.numTasks = numTasks;
		
		
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				 updateDrawWidth();     
		    }
		});
	}

	/**
	 * Call whenever a task has been completed.
	 * @return false if the task is not yet completed; otherwise, true.
	 */
	public Boolean taskCompleted() {
		if(completedTasks < numTasks) {
			completedTasks++;
			updateDrawWidth();
			return false;
		} else {
			return true;
		}
	}

	private void updateDrawWidth() {		
		drawWidth = (int) ((completedTasks/numTasks) * getWidth());
				
		revalidate();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(back);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(front);
		g.fillRect(0, 0, drawWidth, getHeight());
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();

		frame.add(new JMProgressBar(25));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}