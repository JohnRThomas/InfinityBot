package java.awt;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameCanvas extends Component {
	private static final long serialVersionUID = 1619048979922541067L;
	public BufferedImage image = null;
	public BufferedImage plainImage = null;
	public Graphics graphics = null;
	private static ArrayList<GameCanvas> canvases = new ArrayList<GameCanvas>();
	protected final int id;
	public static int count = 0;
	private MouseListener slaveMouseListener = null;
	private MouseMotionListener slaveMouseMotionListener = null;
	private KeyListener slaveKeyListener = null;


	public GameCanvas() {
		image = new BufferedImage(784, 562, 1);
		plainImage = new BufferedImage(784, 562, 1);
		graphics = image.getGraphics();
		int i = 0;
		for(; i < canvases.size(); i++){
			if(this.getParent() == canvases.get(i).getParent()){
				id = canvases.get(i).id;
				canvases.set(i, this);
				try{
					ClientList.get(id).updateCanvas(this);
					this.addMasterMouseListener(ClientList.get(id).getMouse());
					this.addMasterMouseMotionListener(ClientList.get(id).getMouseMotion());
					this.addMasterKeyListener(ClientList.get(id).getKeyboard());
				}catch(Exception e){}
				return;
			}
		}
		id = count++;
		canvases.add(this);
		try{
			this.addMasterMouseListener(ClientList.get(id).getMouse());
			this.addMasterMouseMotionListener(ClientList.get(id).getMouseMotion());
			this.addMasterKeyListener(ClientList.get(id).getKeyboard());
		}catch(Exception e){}
	}

	public GameCanvas(int i) {
		id = i;
	}
	@Override
	public Graphics getGraphics() {
	    try{
	      Thread.sleep(10);
	    }catch(InterruptedException e) {}
	    
		if (super.getGraphics() != null && graphics != null) {
			plainImage = new BufferedImage(784, 562, 1);
			plainImage.getGraphics().drawImage(image, 0, 0, null);
			try{
				ClientList.get(id).draw(image.getGraphics());
			}catch(Exception e) {
				ClientList.get(id).log("Painter", e.getMessage());
			}
			super.getGraphics().drawImage(image, 0, 0, null);
		}
		return graphics = image.createGraphics();
	}
	public BufferedImage getImage(){
		return plainImage;
	}
	
	public BufferedImage getPlainImage(){
		return plainImage;
	}
	public static ArrayList<GameCanvas> getList() {
		return canvases;
	}

	public int getId() {
		return id;
	}

	public void addMouseListener(MouseListener msl, boolean fromCanvas) {
		if(fromCanvas) slaveMouseListener = msl;
		else super.addMouseListener(msl);
	}
	
	public void addMasterMouseListener(MouseListener msl) {
		addMouseListener(msl, false);
	}

	public MouseListener getSlaveMouseListener() {
		return slaveMouseListener;
	}
	public void addMouseMotionListener(MouseMotionListener msl, boolean fromCanvas) {
		if(fromCanvas) slaveMouseMotionListener = msl;
		else super.addMouseMotionListener(msl);
	}
	
	public void addMasterMouseMotionListener(MouseMotionListener msl) {
		addMouseMotionListener(msl, false);
	}

	public MouseMotionListener getSlaveMouseMotionListener() {
		return slaveMouseMotionListener;
	}
	public void addKeyListener(KeyListener kl, boolean fromCanvas) {
		if(fromCanvas) slaveKeyListener = kl;
		else super.addKeyListener(kl);
	}
	
	public void addMasterKeyListener(KeyListener kl) {
		addKeyListener(kl, false);
	}
	public static GameCanvas get(int id) {
		for(int i = 0; i < canvases.size(); i++){
			if(canvases.get(i).id == id){
				return canvases.get(i);
			}
		}
		return null;
	}
	public KeyListener getSlaveKeyListener() {
		return slaveKeyListener;
	}
}