package genad.config;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 *	DTA to manipulate and consult the configuration of the plugins and genad itself
 *	@author kronenthaler
 */
public class Config{
	private String path;
	private Hashtable<String, String> defaults;
	private Hashtable<String, Boolean> actives;
	private Vector<String> installed;
		
	public Config(){
		defaults=new Hashtable<String, String>();
		installed=new Vector<String>();
		actives=new Hashtable<String,Boolean>();
	}
	
	public void load(String _path){
		try{
			path=_path;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//dbf.setValidating(true); //si se define un dtd para este archivo
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
			
			NodeList root=doc.getElementsByTagName("configuration").item(0).getChildNodes();
			for(int i=0,n=root.getLength();i<n;i++){
				if(root.item(i).getNodeName().equals("default")){
					defaults.put(root.item(i).getAttributes().getNamedItem("option").getTextContent(),
								 root.item(i).getAttributes().getNamedItem("value").getTextContent());
				}else if(root.item(i).getNodeName().equals("plugins")){
					NodeList plugins=root.item(i).getChildNodes();
					for(int j=0,m=plugins.getLength();j<m;j++){
						if(plugins.item(j).getNodeName().equals("plugin")){
							installed.add(plugins.item(j).getAttributes().getNamedItem("name").getTextContent());
							actives.put(plugins.item(j).getAttributes().getNamedItem("name").getTextContent(),
									    plugins.item(j).getAttributes().getNamedItem("active").getTextContent().equals("yes"));
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void save()throws IOException{
		
	}
	
	public Hashtable<String, Boolean> getPluginsActive(){ return actives;}
	public Vector getPluginsInstalled(){ return installed;	}
	public String getDefaultValue(String option){ return defaults.get(option);	}
	
	public String toString(){
		String ret="<configuration>\n";
		for(Enumeration<String> e=defaults.keys();e.hasMoreElements();){
			String s=e.nextElement();
			ret+="\t<default option=\""+s+"\" value=\""+defaults.get(s)+"\"/>\n";
		}
		ret+="\t<plugins>\n";
		for(int i=0;i<installed.size();i++){
			ret+="\t\t<plugin name=\""+installed.get(i)+"\" active=\""+(actives.get(installed.get(i))?"yes":"no")+"\"/>\n";
		}
		ret+="\t</plugins>\n";
		
		ret+="</configuration>";
		return ret;
	}
}
