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
	
	public static String capitalize(String s){
		if(s==null || "".equals(s)) return "";
		if(s.length()==1) return s.toUpperCase();
		return Character.toUpperCase(s.charAt(0))+s.substring(1);
	}
	
	public static String xmlSafe(String text){
		try{
			text=text.replaceAll("&","&amp;amp;");
			
			text=text.replaceAll("�","&amp;aacute;");
			text=text.replaceAll("�","&amp;eacute;");
			text=text.replaceAll("�","&amp;iacute;");
			text=text.replaceAll("�","&amp;oacute;");
			text=text.replaceAll("�","&amp;uacute;");
			
			text=text.replaceAll("�","&amp;Aacute;");
			text=text.replaceAll("�","&amp;Eacute;");
			text=text.replaceAll("�","&amp;Iacute;");
			text=text.replaceAll("�","&amp;Oacute;");
			text=text.replaceAll("�","&amp;Uacute;");
			
			text=text.replaceAll("�","&amp;ntilde;");
			text=text.replaceAll("�","&amp;Ntilde;");
			
		}catch(Exception e){}	
		return text;
	}
		
		
	public static String revertXMLSafe(String text){
		try{
			text=text.replaceAll("&aacute;","�");
			text=text.replaceAll("&eacute;","�");
			text=text.replaceAll("&iacute;","�");
			text=text.replaceAll("&oacute;","�");
			text=text.replaceAll("&uacute;","�");
			                                 
			text=text.replaceAll("&Aacute;","�");
			text=text.replaceAll("&Eacute;","�");
			text=text.replaceAll("&Iacute;","�");
			text=text.replaceAll("&Oacute;","�");
			text=text.replaceAll("&Uacute;","�");
			                                 
			text=text.replaceAll("&ntilde;","�");
			text=text.replaceAll("&Ntilde;","�");


			text=text.replaceAll("&amp;","&");
		}catch(Exception e){}
		return text;	
	}
	
	public static String sanitize(String text){
		try{
			text=text.replaceAll("[^a-zA-Z0-9_]","_");
		}catch(Exception e){}
		return text;
	}
}
