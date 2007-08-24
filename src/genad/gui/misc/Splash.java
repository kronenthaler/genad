package genad.gui.misc;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.table.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;

/**
 *
 *	@author kronenthaler
 */
public class Splash extends JDialog{
	private int componentsLoaded=0;
	private final int totalComponents=10;
	private static Splash me=null;
	private SplashCanvas canvas;
	private Toolkit tk=getToolkit();
	
	private Splash(){
		super(new Frame("GenAd"),true);
		
		ImageIcon background=new ImageIcon(getClass().getResource("/images/splash.png"));
		final int width=background.getIconWidth();
		final int height=background.getIconHeight();
				
		//fire the thread initializing the environment
		new Thread(new Runnable(){
			public void run(){
				setText("Loading Configuration");
				ConfigManager cf=ConfigManager.getInstance();
				
				for(componentsLoaded=0;componentsLoaded<totalComponents;){
					try{Thread.sleep(100);}catch(Exception e){}
					nextStep();
				}
				
				JFrame frame=genad.gui.Main.getInstance();
				frame.setSize(tk.getScreenSize());
				frame.setVisible(true);
				
				setVisible(false);
				dispose();
				//System.exit(0);
			}
		}).start();
		
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true); //remove frame decoration
		setTitle("GenAd loading...");
		
		//the next instructions can be nasty, but provides a double bufered canvas without
		//flickering painting.
		canvas=new SplashCanvas(background);
		canvas.setSize(width,height);
		canvas.setBackground(Color.white);
		getContentPane().add(canvas);
			
		setBounds(((int)tk.getScreenSize().getWidth()-width)>>1,
				  ((int)tk.getScreenSize().getHeight()-height)>>1,
				  width,
				  height);//center the splash
		pack();
		canvas.makeStrategy();
		setVisible(true);
	}
	
	//singleton pattern
	public static Splash getInstance(){
		if(me==null) me=new Splash();
		return me;
	}
	
	public void setText(String msg){
		canvas.msg=msg;
		canvas.repaint();
	}
	
	public void nextStep(){
		componentsLoaded++;
		canvas.repaint();
	}
	
	private class SplashCanvas extends Canvas{
		private BufferStrategy strat;
		private Image background;
		protected String msg="";
		private Font font=new Font("Courier New", Font.PLAIN,12);
		private Color fontColor=new Color(153,153,153);
		private Color progressColor=new Color(155,155,155,178);
		private RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		SplashCanvas(ImageIcon imgBackground){
			super();
			background=imgBackground.getImage();
		}
		
		//double buffered canvas
		public void makeStrategy(){
			createBufferStrategy(2);
			strat=getBufferStrategy();
			repaint();
		}
		
		public void update(Graphics g){
			paint(g);
		}

		public void paint(Graphics g2){
			if(strat!=null){
				Graphics2D g=(Graphics2D)strat.getDrawGraphics();
				g.setRenderingHints(rh);
			
				g.drawImage(background,0,0,this);
				g.setColor(fontColor);
				g.setFont(font);
				g.drawString(msg+"...", 20,115);

				g.setColor(progressColor);
				g.fillRoundRect(17,120,(int)((componentsLoaded/((double)totalComponents))*464),16,2,19);
				
				strat.show();
			}else
				repaint(100);
		}
	}
}


