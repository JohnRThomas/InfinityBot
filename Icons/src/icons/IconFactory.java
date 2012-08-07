package icons;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconFactory {
	public Icon getStart(){
		return createImageIcon("start.png");
	}
	public Icon getPause(){
		return createImageIcon("pause.png");
	}
	public Icon getStop(){
		return createImageIcon("stop.png");
	}
	public Icon getRerun(){
		return createImageIcon("rerun.png");
	}
	public Icon getClose(){
		return createImageIcon("close.png");
	}
	public Icon getMini(){
		return createImageIcon("mini.png");
	}
	public Icon getFavi(){
		return createImageIcon("favi.png");
	}
	public Icon getKey(){
		return createImageIcon("keyboard.png");
	}
	public Icon getDisKey(){
		return createImageIcon("keyboardDis.png");
	}
	public Image getLoading(){
		return createImageIcon("loading.png").getImage();
	}

	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
