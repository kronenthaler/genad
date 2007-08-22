package genad;

import genad.gui.misc.Splash;

import java.io.*;

/**
 * 
 * @author kronenthaler
 */
public class Main {
	public static void main(String[] args) {
		//genad.engine.Engine engine=new genad.engine.Engine();
		//engine.transformXML("docs/tests/testing/mod.xml","docs/tests/testing/mod.xsl");
		genad.model.Model model=genad.model.Model.getInstance();
		model.load(new File("project.xml"));
		System.err.println(model);
		model.save(new File("project1.xml"));
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Splash.getInstance();
			}
		});
	}
}
