package genad.config;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import genad.*;

/**
 *	DTA to manipulate and consult the configuration of the plugins and genad itself
 *	@author kronenthaler
 */
public class Config implements Serializable{
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
				}else if(root.item(i).getNodeName().equals("langs")){
					NodeList langs=root.item(i).getChildNodes();
					for(int j=0,m=langs.getLength();j<m;j++){
						if(langs.item(j).getNodeName().equals("lang")){
							installed.add(langs.item(j).getAttributes().getNamedItem("name").getTextContent());
							actives.put(langs.item(j).getAttributes().getNamedItem("name").getTextContent(),
									    langs.item(j).getAttributes().getNamedItem("active").getTextContent().equals("yes"));
						}
					}
				}
			}
		}catch(RuntimeException e){
			Utils.showError("Fatal Error: "+e.getMessage()+"\n"+e.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Utils.sort(installed);
	}
	
	void save() throws IOException{
		PrintStream out=new PrintStream(new FileOutputStream(path));
		out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.println(this);
		out.flush();
		out.close();
	}
	
	Hashtable<String, Boolean> getLangsActive(){ return actives;}
	Vector<String> getLangsInstalled(){ return installed;	}
	String getDefaultValue(String option){ return defaults.get(option);	}
	
	public String toString(){
		StringBuffer ret=new StringBuffer();
		ret.append("<configuration>\n");
		for(Enumeration<String> e=defaults.keys();e.hasMoreElements();){
			String s=e.nextElement();
			ret.append("\t<default option=\""+s+"\" value=\""+defaults.get(s)+"\"/>\n");
		}
		ret.append("\t<langs>\n");
		for(int i=0;i<installed.size();i++){
			ret.append("\t\t<lang name=\""+installed.get(i)+"\" active=\""+(actives.get(installed.get(i))?"yes":"no")+"\"/>\n");
		}
		ret.append("\t</langs>\n")
			.append("</configuration>");
		return ret.toString();
	}
}
