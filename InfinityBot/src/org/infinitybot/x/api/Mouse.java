package org.infinitybot.x.api;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.infinitybot.x.MouseManager;

public class Mouse{
	private MouseManager mouse;
	private int speed = 2;
	public Mouse(APIManager manager){
		mouse = manager.getMouse();
	}
	public int getX(){
		return mouse.getPoint().x;
	}
	public int getY(){
		return mouse.getPoint().y;
	}
	public Point getPoint() {
		return mouse.getPoint();
	}
	public void setSpeed(int speed) {
		if(speed < 0)throw new IllegalArgumentException("Speed must be a positive number!");
		this.speed = speed;
	}
	public int getSpeed() {
		return speed;
	}
	public void move(int x, Point pt){
		move(x,pt.y);
	}
	public void move(Point pt, int y){
		move(pt.x,y);
	}
	public void move(Point pt){
		move(pt.x,pt.y);
	}
	public void move(int x, int y){
		int curX = getX();
		int curY = getY();
		int slopeX = x - curX;
		int slopeY = y - curY;

		Point [] coordlist = new Point[4];
		coordlist[0] = new Point(curX,curY);
		coordlist[1] = new Point(curX,curY);
		coordlist[2] = new Point(x,y);
		coordlist[3] = new Point(x,y);

		double k = .025;
		double t;
		double x1,x2,y1,y2;
		x1 = coordlist[0].x;
		y1 = coordlist[0].y;
		for(t=k; t<=1+k; t+=k){
			//use Berstein polynomials
			x2=(coordlist[0].x+t*(-coordlist[0].x*3+t*(3*coordlist[0].x-
					coordlist[0].x*t)))+t*(3*coordlist[1].x+t*(-6*coordlist[1].x+
							coordlist[1].x*3*t))+t*t*(coordlist[2].x*3-coordlist[2].x*3*t)+
							coordlist[3].x*t*t*t;
			y2=(coordlist[0].y+t*(-coordlist[0].y*3+t*(3*coordlist[0].y-
					coordlist[0].y*t)))+t*(3*coordlist[1].y+t*(-6*coordlist[1].y+
							coordlist[1].y*3*t))+t*t*(coordlist[2].y*3-coordlist[2].y*3*t)+
							coordlist[3].y*t*t*t;				

			Point start = new Point((int)x1,(int)y1);
			Point end = new Point((int)x2,(int)y2);
			if(start.x-end.x != 0){
				int b = start.y - ((start.y-end.y)/(start.x-end.x)) * start.x;
				for(int i = start.x; i < end.x; i++){
					mouse.mouseMoved(new MouseEvent(mouse,MouseEvent.MOUSE_MOVED,System.currentTimeMillis(),0,i,(((start.y-end.y)/(start.x-end.x)) * i) + b,0,false,0));
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e){}                                                                                            
				}
			}else{
				
			}
			x1 = x2;
			y1 = y2;
		}
	}
}