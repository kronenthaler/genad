package genad;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.nio.channels.*;

import genad.model.*;
import genad.config.*;
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
	
	public static String[] invert(Stack<String> s){
		String[] ret=new String[s.size()];
		s.copyInto(ret);
		for(int i=0,n=ret.length;i<n>>1; i++){
			String aux=ret[i];
			ret[i] = ret[n-i-1];
			ret[n-i-1]=aux;
		}
		return ret;
	}
	
	public static void sort(Vector<String> v){
		/*String[] aux=v.toArray(new String[0]);
		Arrays.sort(aux);
		v.clear();
		for(int i=0;i<aux.length;v.add(aux[i++]));*/
		Collections.sort(v);
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
	
	public static boolean showConfirm(String msg){
		return JOptionPane.showConfirmDialog(Main.getInstance(),msg, "Confirmation", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;
	}
	
	public static boolean showInformation(String msg){
		JOptionPane.showMessageDialog(genad.gui.Main.getInstance(), msg,"Information", JOptionPane.INFORMATION_MESSAGE);
		return true;
	}
	
	public static boolean showWarning(String msg){
		JOptionPane.showMessageDialog(genad.gui.Main.getInstance(), msg,"Warning", JOptionPane.WARNING_MESSAGE);
		return true;
	}
	
	//generation related
	
	public static long countFiles(File root){
		long ret=0;
		if(!root.exists()) return 0;
		for(File current : root.listFiles()){
			if(current.isFile()){ 
				if(!current.isHidden()) 
					ret++;
			}else{
				if(!current.isHidden())
					ret+=countFiles(current);
			}
		}
		
		return ret;
	}
	
	public static int copyDirectory(File srcDir, File dstDir) throws IOException {
        if(srcDir.isHidden() || !srcDir.exists()) return 0;
		if(srcDir.isDirectory()){
            if(!dstDir.exists())
                dstDir.mkdirs();
            int ret=0;
            for(String children :srcDir.list())
				ret+=copyDirectory(new File(srcDir, children),new File(dstDir, children));
			return ret;
        }else
            return copyFile(srcDir, dstDir);
    }
    
    public static int copyFile(File src,File dest) throws IOException{
        //System.out.println("Copiando archivo: "+src.toString()+"...");
        FileChannel in = null, out = null;
		try{          
			in = new FileInputStream(src).getChannel();
			out = new FileOutputStream(dest).getChannel();
			
			in.transferTo(0,in.size(),out);
		}finally{
			if(in != null) in.close();
			if(out != null) out.close();
		}
		return 1;
    }
	
	//return the topological sort of a graph of modules linked by their dependencies
	public static String[] topologicalSort(String[] modNames, LangConfig pCfg){
		Hashtable<String, Vector<String>> M=new Hashtable<String, Vector<String>>();
		Hashtable<String, Boolean> visited=new Hashtable<String, Boolean>();
		Stack<String> result=new Stack<String>();
		
		for(String current:modNames){
			if(M.get(current)==null) M.put(current,new Vector<String>());
			
			ModuleConfig mCfg=pCfg.getModuleConfig(current);
			Vector<String> links=mCfg.getDependencies();
			for(String src:links){
				if(M.get(src)==null) M.put(src,new Vector<String>());
				M.get(src).add(current);
			}
		}
				
		for(String current:modNames)
			if(visited.get(current)==null || !visited.get(current))
				tsort(current, M, result, visited);
		
		return invert(result);
	}
	
	private static void tsort(String v, Hashtable<String, Vector<String>> M, Stack<String> result, Hashtable<String, Boolean> visited){
		visited.put(v,true);
		for(int i=0;i<M.get(v).size();i++){
			String w=M.get(v).get(i);
			if(visited.get(w)==null || !visited.get(w))
				tsort(w,M,result, visited);
		}
		
		result.push(v);//stack it
	}
}
