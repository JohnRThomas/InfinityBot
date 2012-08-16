package org.infinity.bot.internals;

import java.awt.Component;
import java.awt.GameCanvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class MouseManager  extends Component implements MouseListener, MouseMotionListener, MouseWheelListener{
	private static final long serialVersionUID = -6083468090663688500L;

	protected GameCanvas cancan;
	private final Client myClient;
	int x = 0, y = 0;
	public MouseManager(Client cli) {
		myClient = cli;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				System.out.println(e.toString());
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();cancan.getSlaveMouseListener().mouseClicked(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();cancan.getSlaveMouseListener().mouseEntered(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();cancan.getSlaveMouseListener().mouseExited(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				System.out.println(e.toString());
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();cancan.getSlaveMouseListener().mousePressed(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				System.out.println(e.toString());
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();cancan.getSlaveMouseListener().mouseReleased(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();
				cancan.getSlaveMouseMotionListener().mouseDragged(e);
			}
		}catch(Exception ex){}
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				e.setSource(cancan);
				x = e.getX();
				y = e.getY();
				cancan.getSlaveMouseMotionListener().mouseMoved(e);
			}
		}catch(Exception ex){}
	}
	public Point getPoint() {
		return new Point(x,y);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		try{
			if(myClient.isInputEnabled() || e.getSource() == this){
				e.setSource(cancan);
				cancan.getSlaveMouseWheelListener();
			}
		}catch(Exception ex){}
	}
}
