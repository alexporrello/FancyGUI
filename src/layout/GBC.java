package layout;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GBC extends GridBagConstraints {
	private static final long serialVersionUID = 3962801422733178745L;
	
	public static final int HORIZ = GridBagConstraints.HORIZONTAL;
	public static final int VERT  = GridBagConstraints.VERTICAL;
	public static final int CENT  = GridBagConstraints.CENTER;
	
	/**
	 * Adds a component to its parent with GridBagConstraints.
	 * 
	 * @param parent is the parents of the JComponent to be added.
	 * @param child is the JComponent to be added.
	 * @param weightx is the horizontal weight.
	 * @param gridx is the x posn.
	 * @param gridy is the y posn.
	 * @param fill is the fill position, from GridBagConstraint.
	 * @param insets is the space around the component.
	 * @param gridWidth is the grid width of the component.
	 */
	public static void addWithGBC(JComponent parent, JComponent child, Double weightx,
			int gridx, int gridy, int fill, Insets insets, int gridWidth) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx   = weightx;
		gbc.gridx     = gridx;
		gbc.gridy     = gridy;
		gbc.fill      = fill;
		gbc.insets    = insets;
		gbc.gridwidth = gridWidth;

		parent.add(child, gbc);
	}

	/**
	 * Adds a component to its parent with GridBagConstraints.
	 * 
	 * @param parent is the parents of the JComponent to be added.
	 * @param child is the JComponent to be added.
	 * @param weightx is the horizontal weight.
	 * @param gridx is the x posn.
	 * @param gridy is the y posn.
	 * @param fill is the fill position, from GridBagConstraint.
	 * @param insets is the space around the component.
	 * @param gridWidth is the grid width of the component.
	 */
	public static void addWithGBC(JComponent parent, JComponent child, Double weightx,
			int gridx, int gridy, int fill, int anchor, Insets insets, int gridWidth) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx   = weightx;
		gbc.gridx     = gridx;
		gbc.gridy     = gridy;
		gbc.fill      = fill;
		gbc.anchor    = anchor;
		gbc.insets    = insets;
		gbc.gridwidth = gridWidth;

		parent.add(child, gbc);
	}
	
	/**
	 * Adds a component to its parent with GridBagConstraints.
	 * 
	 * @param parent is the parents of the JComponent to be added.
	 * @param child is the JComponent to be added.
	 * @param weightx is the horizontal weight.
	 * @param gridx is the x posn.
	 * @param gridy is the y posn.
	 * @param fill is the fill position, from GridBagConstraint.
	 * @param insets is the space around the component.
	 * @param gridWidth is the grid width of the component.
	 */
	public static void addWithGBC(JComponent parent, JComponent child, Double weightx, Double weighty,
			int gridx, int gridy, int fill, int anchor, Insets insets, int gridWidth) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx   = weightx;
		gbc.weighty   = weighty;
		gbc.gridx     = gridx;
		gbc.gridy     = gridy;
		gbc.fill      = fill;
		gbc.anchor    = anchor;
		gbc.insets    = insets;
		gbc.gridwidth = gridWidth;

		parent.add(child, gbc);
	}
	
	/**
	 * Adds a component to its parent with GridBagConstraints.
	 * 
	 * @param parent is the parents of the JComponent to be added.
	 * @param child is the JComponent to be added.
	 * @param weightx is the horizontal weight.
	 * @param weighty is the vertical weight.
	 * @param gridx is the x posn.
	 * @param gridy is the y posn.
	 * @param fill is the fill position, from GridBagConstraint.
	 * @param anchor is the anchor position of the component.
	 * @param insets is the space around the component.
	 * @param gridWidth is the grid width of the component.
	 * @param gridHeight is the grid height of the component.
	 */
	public static void addWithGBC(JComponent parent, JComponent child, Double weightx, Double weighty,
			int gridx, int gridy, int fill, int anchor, Insets insets, int gridWidth, int gridHeight) {

		GBC gbc        = new GBC();
		gbc.weightx    = weightx;
		gbc.weighty    = weighty;
		gbc.gridx      = gridx;
		gbc.gridy      = gridy;
		gbc.fill       = fill;
		gbc.anchor     = anchor;
		gbc.insets     = insets;
		gbc.gridwidth  = gridWidth;
		gbc.gridheight = gridHeight;

		parent.add(child, gbc);
	}
	
	/**
	 * Adds a component to its parent with GridBagConstraints.
	 * 
	 * @param parent is the parents of the JComponent to be added.
	 * @param child is the JComponent to be added.
	 * @param weightx is the horizontal weight.
	 * @param gridx is the x posn.
	 * @param gridy is the y posn.
	 * @param fill is the fill position, from GridBagConstraint.
	 * @param insets is the space around the component.
	 * @param gridWidth is the grid width of the component.
	 */
	public static void addWithGBC(JFrame parent, JComponent child, Double weightx, Double weighty,
			int gridx, int gridy, int fill, int anchor, Insets insets, int gridWidth) {

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx   = weightx;
		gbc.weighty   = weighty;
		gbc.gridx     = gridx;
		gbc.gridy     = gridy;
		gbc.fill      = fill;
		gbc.anchor    = anchor;
		gbc.insets    = insets;
		gbc.gridwidth = gridWidth;

		parent.add(child, gbc);
	}
	
	/**
	 * Returns new insets with given parameters.
	 * @param top is the top space. 
	 * @param left is the left space.
	 * @param bottom is the bottom space.
	 * @param right is the right space.
	 * @return new insets with given parameters.
	 */
	public static Insets insets(int top, int left, int bottom, int right) {
		return new Insets(top, left, bottom, right);
	}
}
