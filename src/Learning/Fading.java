package Learning;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import view.UI;


public class Fading extends UI {
	private static final long serialVersionUID = 1L;
	private Rectangle2D rect = new Rectangle2D.Float(20f, 20f, 80f, 50f);

	private float alpha_rectangle = 1f;

	public void setup() {
		frametime=20;
	    frame.setSize(650, 400);
	}

	public void loop()
	{
	    g.setColor(new Color(50, 50, 50));
	    RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    alpha_rectangle += -0.01f;
	    if(alpha_rectangle<0)
	    	alpha_rectangle=1f;
	    g.setRenderingHints(rh);
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha_rectangle));
	    g.fill(rect);
	}
	
	public static void main(String[] args) {
		start(new Fading());
	}
}
