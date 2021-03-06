package java.awt;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public interface ClientActions {
	public void updateCanvas(GameCanvas gameCanvas);
	public void draw(Graphics g);
	public void log(final String source, final Object ob);
	public MouseListener getMouse();
	public MouseMotionListener getMouseMotion();
	public KeyListener getKeyboard();
	public MouseWheelListener getMouseWheel();
	public void remove(int i);
	public void destroy();
}
