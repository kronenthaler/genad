package genad;

import genad.gui.misc.Splash;

import java.io.*;

/**
 * 
 * @author kronenthaler
 */
public class Main {
	public static void main(String[] args) {
		/*try{
			genad.engine.Engine engine=new genad.engine.Engine();
			engine.transformXML("project1.xml","js.xsl",new PrintStream(new File("db.js")));
		}catch(Exception e){}//*/
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Splash.getInstance();
			}
		});
	}
}
