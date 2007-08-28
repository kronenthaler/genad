package genad;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import genad.gui.Main;

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
		if(e==null) return new String[0];
		
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
			
			text=text.replaceAll("á","&amp;aacute;");
			text=text.replaceAll("é","&amp;eacute;");
			text=text.replaceAll("í","&amp;iacute;");
			text=text.replaceAll("ó","&amp;oacute;");
			text=text.replaceAll("ú","&amp;uacute;");
			
			text=text.replaceAll("Á","&amp;Aacute;");
			text=text.replaceAll("É","&amp;Eacute;");
			text=text.replaceAll("Í","&amp;Iacute;");
			text=text.replaceAll("Ó","&amp;Oacute;");
			text=text.replaceAll("Ú","&amp;Uacute;");
			
			text=text.replaceAll("ñ","&amp;ntilde;");
			text=text.replaceAll("Ñ","&amp;Ntilde;");
			
		}catch(Exception e){}
		return text;
	}
		
		
	public static String revertXMLSafe(String text){
		try{
			text=text.replaceAll("&aacute;","á");
			text=text.replaceAll("&eacute;","é");
			text=text.replaceAll("&iacute;","í");
			text=text.replaceAll("&oacute;","ó");
			text=text.replaceAll("&uacute;","ú");
			                                 
			text=text.replaceAll("&Aacute;","Á");
			text=text.replaceAll("&Eacute;","É");
			text=text.replaceAll("&Iacute;","Í");
			text=text.replaceAll("&Oacute;","Ó");
			text=text.replaceAll("&Uacute;","Ú");
			                                 
			text=text.replaceAll("&ntilde;","ñ");
			text=text.replaceAll("&Ntilde;","Ñ");


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
	
	public static boolean showError(String msg){
		JOptionPane.showMessageDialog(Main.getInstance(),msg,"Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}
}
