package Learning;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import view.UI;


public class LineAnim extends UI {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	public void setup() {
		frametime=20;
	    frame.setSize(800, 600);
	}

	public void loop()
	{
	    g.setColor(new Color(50, 50, 50));
	    g.drawLine(0, 0, 100, y++);
	}
	
	public static void main(String[] args) {
		start(new LineAnim());
	}
}
