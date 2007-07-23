package genad;

import genad.gui.misc.Splash;

/**
 * 
 * @author kronenthaler
 */
public class Main {
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Splash.getInstance();
			}
		});
	}
}
