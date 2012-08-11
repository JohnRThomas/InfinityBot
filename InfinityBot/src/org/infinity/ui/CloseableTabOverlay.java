package org.infinity.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.FlowLayout;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.BorderFactory;
import javax.swing.AbstractButton;

import javax.swing.plaf.basic.BasicButtonUI;

public class CloseableTabOverlay extends JPanel {

	private static final long serialVersionUID = 1L;
	private final JTabbedPane pane;

	public CloseableTabOverlay(final JTabbedPane pane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		if (pane == null) {
			throw new NullPointerException("TabbedPane is null");
		}
		this.pane = pane;
		setOpaque(false);
		final JLabel label = new JLabel() {
			private static final long serialVersionUID = 1L;
			public String getText() {
				int i = pane.indexOfTabComponent(CloseableTabOverlay.this);
				if (i != -1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
		};
		add(label);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		final JButton button = new TabButton();
		add(button);
		setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}

	private class TabButton extends JButton implements ActionListener {
		private static final long serialVersionUID = 1L;

		public TabButton() {
			final int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText("close this tab");
			setUI(new BasicButtonUI());
			setContentAreaFilled(false);
			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);
			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);
			addActionListener(this);
		}

		public void actionPerformed(final ActionEvent e) {
			final int i = pane.indexOfTabComponent(CloseableTabOverlay.this);
			if (i != -1) {
				InfinityGUI.removeClient(i);
				pane.remove(i);
				if (i != 0) {
					pane.setSelectedIndex(i-1);
				}
			}
		}

		public void updateUI() {
		}

		protected void paintComponent(final Graphics graphics) {
			super.paintComponent(graphics);
			final Graphics2D g = (Graphics2D) graphics.create();
			if (getModel().isPressed()) {
				g.translate(1, 1);
			}
			g.setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
			if (getModel().isRollover()) {
				g.setColor(Color.RED);
			}
			int delta = 6;
			g.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g.dispose();
		}
	}

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
		public void mouseEntered(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(true);
			}
		}

		public void mouseExited(final MouseEvent e) {
			final Component component = e.getComponent();
			if (component instanceof AbstractButton) {
				final AbstractButton button = (AbstractButton) component;
				button.setBorderPainted(false);
			}
		}
	};
}