package java.awt;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

/**
 * @author Timer
 */
public class Canvas extends GameCanvas implements Accessible {

	private static final String base = "canvas";
	private static int nameCounter = 0;
	private static final long serialVersionUID = -2284879212465893870L;

	public Canvas() {
	}

	public Canvas(final GraphicsConfiguration config) {
		this();
		this.graphicsConfig = config;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public Graphics getGraphics() {
		return super.getGraphics();
	}

	@Override
	public String constructComponentName() {
		synchronized (Canvas.class) {
			return base + nameCounter++;
		}
	}

	@Override
	public void addNotify() {
		synchronized (getTreeLock()) {
			if (this.peer == null) {
				this.peer = getToolkit().createCanvas(this);
			}
			super.addNotify();
		}
	}

	@Override
	public void paint(final Graphics g) {
		g.clearRect(0, 0, this.width, this.height);
	}

	@Override
	public void update(final Graphics g) {
		g.clearRect(0, 0, this.width, this.height);
		paint(g);
	}

	@Override
	public boolean postsOldMouseEvents() {
		return true;
	}

	@Override
	public void createBufferStrategy(final int numBuffers) {
		super.createBufferStrategy(numBuffers);
	}

	@Override
	public void createBufferStrategy(final int numBuffers, final BufferCapabilities caps) throws AWTException {
		super.createBufferStrategy(numBuffers, caps);
	}

	@Override
	public BufferStrategy getBufferStrategy() {
		return super.getBufferStrategy();
	}
	@Override
	public void addMouseListener(MouseListener msl) {
		super.addMouseListener(msl,true);
	}
	@Override
	public void addMouseMotionListener(MouseMotionListener msl) {
		super.addMouseMotionListener(msl,true);
	}
	@Override
	public void addKeyListener(KeyListener kl) {
		super.addKeyListener(kl,true);
	}
	@Override
	public AccessibleContext getAccessibleContext() {
		if (this.accessibleContext == null) {
			this.accessibleContext = new AccessibleAWTCanvas();
		}
		return this.accessibleContext;
	}

	protected class AccessibleAWTCanvas extends Component.AccessibleAWTComponent {

		private static final long serialVersionUID = -6325592262103146699L;

		protected AccessibleAWTCanvas() {
			super();
		}

		@Override
		public AccessibleRole getAccessibleRole() {
			return AccessibleRole.CANVAS;
		}
	}

}