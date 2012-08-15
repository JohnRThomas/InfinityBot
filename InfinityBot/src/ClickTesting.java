import java.awt.Color;
import java.awt.Graphics;

import org.infinity.bot.api.script.Script;
import org.infinity.bot.api.script.ScriptManifest;
import org.infinity.bot.api.utils.Time;

@ScriptManifest(Authors = { "TheBat" }, Category = ScriptManifest.Categories.OTHER, Description = "Tests clicking at point (100,100)", Name = "Click Tester", Version = 1.0)
public class ClickTesting extends Script{

	@Override
	public int loop() {
		mouse.click(100,100);
		Time.sleep(1000);
		return 10;
	}
	@Override
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.drawLine(mouse.getX(),0,mouse.getX(),2100);
		g.drawLine(0,mouse.getY(),2100,mouse.getY());
	}
}
