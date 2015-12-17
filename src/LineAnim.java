import java.awt.Color;
import java.awt.geom.Rectangle2D;


public class LineAnim extends Animation {
	private static final long serialVersionUID = 1L;

	private int y = 0;

	void setup() {
		frametime=20;
	    frame.setSize(800, 600);
	}

	void loop()
	{
	    g.setColor(new Color(50, 50, 50));
	    g.drawLine(0, 0, 100, y++);
	}
	
	public static void main(String[] args) {
		start(new LineAnim());
	}
}
