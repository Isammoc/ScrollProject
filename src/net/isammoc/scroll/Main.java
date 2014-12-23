package net.isammoc.scroll;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		JFrame jf = new JFrame("Hello World");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		


		JComponent top = new JLabel(new ImageIcon(
				Main.class.getResource("images/portal.jpg")), 10);
		JComponent bottom = new JLabel(new ImageIcon(
				Main.class.getResource("images/portal.jpg")), 10);

		final JScrollPane spTop = new JScrollPane(top);
		final JScrollPane spBottom = new JScrollPane(bottom);

		// Jamais de ScrollBar verticales
		spTop.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		spBottom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Même modèle pour les deux scrolls horizontaux (ça bouge en même
		// temps)
		JScrollBar bottomScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		bottomScrollBar.setModel(spTop.getHorizontalScrollBar().getModel());
		spBottom.setHorizontalScrollBar(bottomScrollBar);

		final JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, spTop,
				spBottom);

		// Affichage que d'un scroll en même temps
		split.addPropertyChangeListener("dividerLocation",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getSource() instanceof JSplitPane) {
							JSplitPane split = (JSplitPane) evt.getSource();
							if ((float) (int) evt.getNewValue()
									/ split.getHeight() < .5) {
								spTop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
								spBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
							} else {
								spTop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
								spBottom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
							}
						}
					}
				});

		jf.getContentPane().add(split);
		jf.setVisible(true);
		jf.setSize(300, 500);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				split.setDividerLocation(.52);
			}
		});
	}
}
