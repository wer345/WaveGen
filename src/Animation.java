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

import View.VBase;

public class Animation extends JPanel {
	  //interface void setup();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int frametime=50;
	public JFrame frame =new JFrame("Aniamtion");
	Graphics2D g;
	List <VBase> views = new ArrayList <VBase>(); 
	

  public Animation() {
    new RectRunnable();
  }


  void setup() {
	  
  }
  
  void loop() {
	  
  }
  public void paint(Graphics _g) {
    super.paint(_g);
    g = (Graphics2D) _g;
    VBase.g=g;
    loop();
	for(VBase vb:views)
		vb.draw();
    
  }

  static void start(Animation obj) {
	    JFrame frame = obj.frame;
	    frame.add(obj);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(600, 400);
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    obj.setup();
  }
  
  public static void main_0(String[] args) {
	  start(new Animation());
  }
  

  class RectRunnable implements Runnable {
    private Thread runner;

    public RectRunnable() {
      runner = new Thread(this);
      runner.start();
    }

    public void run() {
      while (true) {
        repaint();
        try {
          Thread.sleep(frametime);
        } catch (Exception e) {
        }
      }
    }
  }
}
