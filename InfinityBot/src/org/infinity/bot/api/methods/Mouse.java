package org.infinity.bot.api.methods;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import org.infinity.bot.api.APIManager;
import org.infinity.bot.api.utils.Random;
import org.infinity.bot.internals.MouseManager;


public class Mouse{
	private MouseManager mouse;
	private int speed = 6;
	
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
	public void click(int x, int y) {
		click(x,y,true);
	}
	public void click(int x, Point pt){
		click(x,pt.y,true);
	}
	public void click(Point pt, int y){
		click(pt.x,y,true);
	}
	public void click(Point pt){
		click(pt.x,pt.y,true);
	}
	public void click(int x, Point pt, boolean button){
		click(x,pt.y,button);
	}
	public void click(Point pt, int y, boolean button){
		click(pt.x,y,button);
	}
	public void click(Point pt, boolean button){
		click(pt.x,pt.y,button);
	}
	public void click(int x, int y, boolean button){
		move(x,y);
		mouse.mousePressed(new MouseEvent(mouse,MouseEvent.MOUSE_PRESSED,System.currentTimeMillis(),0/*button ? MouseEvent.BUTTON1_MASK:MouseEvent.BUTTON3_MASK*/,x,y,1,false,button ? MouseEvent.BUTTON1:MouseEvent.BUTTON3));
		try {
			Thread.sleep(Random.nextInt(6, 12));
		} catch (InterruptedException e){}
		mouse.mouseReleased(new MouseEvent(mouse,MouseEvent.MOUSE_RELEASED,System.currentTimeMillis(),0/*button ? MouseEvent.BUTTON1_UP_MASK:MouseEvent.BUTTON3_MASK*/,x,y,1,false,button ? MouseEvent.BUTTON1:MouseEvent.BUTTON3));
		mouse.mouseClicked(new MouseEvent(mouse,MouseEvent.MOUSE_CLICKED,System.currentTimeMillis(),0/*button ? MouseEvent.BUTTON1_MASK:MouseEvent.BUTTON3_MASK*/,x,y,1,false,button ? MouseEvent.BUTTON1:MouseEvent.BUTTON3));
	}
	public void move(int x, int y){
		Point origin = new Point(getX(),getY());
		Point [] points = generatePath(origin, new Point(x,y));
		for(int i = 0; i < points.length; i++){
			mouse.mouseMoved(new MouseEvent(mouse,MouseEvent.MOUSE_MOVED,System.currentTimeMillis(),0,points[i].x,points[i].y,0,false,0));
		}
	}
    private static void adaptiveMidpoints(final Vector<Point> points) {
        int i = 0;
        while (i < points.size() - 1) {
            final Point a = points.get(i++);
            final Point b = points.get(i);
            if ((Math.abs(a.x - b.x) > 1) || (Math.abs(a.y - b.y) > 1)) {
                if (Math.abs(a.x - b.x) != 0) {
                    final double slope = (double) (a.y - b.y) / (double) (a.x - b.x);
                    final double incpt = a.y - slope * a.x;
                    for (int c = a.x < b.x ? a.x + 1 : b.x - 1; a.x < b.x ? c < b.x : c > a.x; c += a.x < b.x ? 1 : -1) {
                        points.add(i++, new Point(c, (int) Math.round(incpt + slope * c)));
                    }
                } else {
                    for (int c = a.y < b.y ? a.y + 1 : b.y - 1; a.y < b.y ? c < b.y : c > a.y; c += a.y < b.y ? 1 : -1) {
                        points.add(i++, new Point(a.x, c));
                    }
                }
            }
        }
    }
 
    private static double fact(final int n) {
        double result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
 
    private static double nCk(final int n, final int k) {
        return fact(n) / (fact(k) * fact(n - k));
    }
 
    private static Point[] generateControls(final int sx, final int sy, final int ex, final int ey, int ctrlSpacing) {
        final double dist = Math.sqrt((sx - ex) * (sx - ex) + (sy - ey) * (sy - ey));
        final double angle = Math.atan2(ey - sy, ex - sx);
        int ctrlPoints = (int) Math.floor(dist / ctrlSpacing);
        ctrlPoints = ctrlPoints * ctrlSpacing == dist ? ctrlPoints - 1 : ctrlPoints;
        if (ctrlPoints <= 1) {
            ctrlPoints = 2;
            ctrlSpacing = (int) dist / 3;
        }
        final Point[] result = new Point[ctrlPoints + 2];
        result[0] = new Point(sx, sy);
        for (int i = 1; i < ctrlPoints + 1; i++) {
            final double radius = ctrlSpacing * i;
            final Point cur = new Point((int) (sx + radius * Math.cos(angle)), (int) (sy + radius * Math.sin(angle)));
            double percent = 1D - (double) (i - 1) / (double) ctrlPoints;
            percent = percent > 0.5 ? percent - 0.5 : percent;
            percent += 0.25;
            final int variance = (int) ((dist / 2) * percent);
            cur.x = (int) (cur.x + variance * 2 * Math.random() - variance);
            cur.y = (int) (cur.y + variance * 2 * Math.random() - variance);
            result[i] = cur;
        }
        result[ctrlPoints + 1] = new Point(ex, ey);
        return result;
    }
 
    private static Point[] generateSpline(final Point[] controls) {
        final double degree = controls.length - 1;
        final java.util.Vector<Point> spline = new java.util.Vector<Point>();
        boolean lastFlag = false;
        for (double theta = 0; theta <= 1; theta += 0.01) {
            double x = 0;
            double y = 0;
            for (double index = 0; index <= degree; index++) {
                final double probPoly = nCk((int) degree, (int) index) * Math.pow(theta, index) * Math.pow(1D - theta, degree - index);
                x += probPoly * controls[(int) index].x;
                y += probPoly * controls[(int) index].y;
            }
            final Point temp = new Point((int) x, (int) y);
            try {
                if (!temp.equals(spline.lastElement())) {
                    spline.add(temp);
                }
            } catch (final Exception e) {
                spline.add(temp);
            }
            lastFlag = theta != 1.0;
        }
        if (lastFlag) {
            spline.add(new Point(controls[(int) degree].x, controls[(int) degree].y));
        }
        adaptiveMidpoints(spline);
        return spline.toArray(new Point[spline.size()]);
    }
 
    public static Point[] generatePath(final Point origin, final Point target) {
        return generateSpline(generateControls(origin.x, origin.y, target.x, target.y, 115));
    }
}