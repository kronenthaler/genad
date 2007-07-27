package genad;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 *
 *	@author kronenthaler
 */
public class Utils{
	public static final String PANEL_BACKGROUND="Panel.background";
	public static final String TABBED_SELECTED="TabbedPane.selected";
	public static final String TABBED_BACKGROUND="TabbedPane.background";
	public static final String LIST_BACKGROUND="List.background";	
		
	public static Color getColor(String context){
		return javax.swing.UIManager.getDefaults().getColor(context);
	}
	
	public static void centerComponent(Component comp){
		Toolkit tk=comp.getToolkit();
		int width=comp.getWidth();
		int height=comp.getHeight();
		comp.setBounds(((int)tk.getScreenSize().getWidth()-width)>>1,
				  ((int)tk.getScreenSize().getHeight()-height)>>1,
				  width,
				  height);
	}
	
	public static String[] convert(Enumeration<String> e){
		Vector<String> aux=new Vector<String>();
		
		while(e.hasMoreElements()) aux.add(e.nextElement());
		String[] ret=aux.toArray(new String[0]);
		Arrays.sort(ret);
		return ret;
	}
	
	public static void sort(Vector<String> v){
		String[] aux=v.toArray(new String[0]);
		Arrays.sort(aux);
		v.clear();
		for(int i=0;i<aux.length;v.add(aux[i++]));
	}
}
