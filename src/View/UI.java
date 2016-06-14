package View;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JPanel {

	static boolean stopped=false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int frametime=50;
	public JFrame frame =new JFrame("Aniamtion");
	public Graphics2D g;
	private List <VBase> views = new ArrayList <VBase>(); 
	

  public UI() {
    new RectRunnable();
  }

  public void setWindow(int width, int height ) {
	    frame.setSize(width+25, height+50);
	    VBase.setWindow(width,height);
  }

  public void setLocation(int x, int y ) {
	    frame.setLocation(x, y);
}
  
  public void setup() {
	  
  }
  
  public void loop() {
	  stop();
  }
  
  public void addView(VBase view) {
	  views.add(view);
  }
  
  public void paint(Graphics _g) {
    super.paint(_g);
    g = (Graphics2D) _g;
    VBase.g=g;
    if(!stopped)
    	loop();
	for(VBase vb:views)
		vb.draw();
    
  }

  // start animation
  static public void start(UI obj) {
	    JFrame frame = obj.frame;
	    frame.add(obj);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.setSize(600, 400);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    obj.setWindow(600,300);
	    obj.setup();
  }
  
  // stop animation
  static public void stop() {
	  stopped=true;
  }
  
  public static void main_0(String[] args) {
	  start(new UI());
  }
  

  class RectRunnable implements Runnable {
    private Thread runner;

    public RectRunnable() {
      runner = new Thread(this);
      runner.start();
    }

    public void run() {
      while (!stopped) {
        repaint();
        try {
          Thread.sleep(frametime);
        } catch (Exception e) {
        }
      }
    }
  }
}
