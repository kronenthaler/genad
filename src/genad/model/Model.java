package genad.model;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import genad.*;
import genad.gui.*;
import genad.model.*;
import genad.config.*;
import genad.engine.*;

/**
 *
 *	@author kronenthaler
 */
public class Model{
	public static final int PRODUCTION = 0;
	public static final int DEVELOPMENT = 1;
	
	private static Model me;
	private ConfigManager cfgMan;
	private Hashtable<String, Entity> entities;
	private Hashtable<String, Module> modules;
	private Vector<View> views;
	private boolean changed = false;
	private String destPath="", loadedPath;
	private String language="";
	private String pDBHost, pDBLogin, pDBPassword="", pDBSchema;
	private String dDBHost="", dDBLogin="", dDBPassword="", dDBSchema="";

	private Model(){
		views=new Vector<View>();
		entities=new Hashtable<String, Entity>();
		modules=new Hashtable<String, Module>();
		cfgMan=ConfigManager.getInstance();
	}
	
	public boolean load(File path){
		//load the project basic info
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();     	
			Document doc = db.parse(path);
			NodeList aux;

			Node fileServer=doc.getElementsByTagName("file-server-conf").item(0); //solo debe haber una configuracion por proyecto
			Node dbServer=doc.getElementsByTagName("db-server-conf").item(0);//solo debe haber una configuracion por proyecto
			Node exts=doc.getElementsByTagName("modules").item(0);
			Node ents=doc.getElementsByTagName("entities").item(0);
	
			language=doc.getElementsByTagName("project").item(0).getAttributes().getNamedItem("language").getTextContent();
			
			//file server info
			aux=fileServer.getChildNodes();
			for(int i=0,n=aux.getLength();i<n;i++){
				if(aux.item(i).getNodeName().equalsIgnoreCase("base-directory")){
					destPath=aux.item(i).getTextContent();
				}
			}

			//database servers info
			aux=dbServer.getChildNodes();
			for(int i=0,n=aux.getLength();i<n;i++){
				if(aux.item(i).getNodeName().equalsIgnoreCase("dev-host"))
					dDBHost=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("dev-user"))
					dDBLogin=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("dev-password"))
					dDBPassword=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("dev-schema"))
					dDBSchema=aux.item(i).getTextContent();
				
				if(aux.item(i).getNodeName().equalsIgnoreCase("prod-host"))
					pDBHost=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("prod-user"))
					pDBLogin=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("prod-password"))
					pDBPassword=aux.item(i).getTextContent();
				else if(aux.item(i).getNodeName().equalsIgnoreCase("prod-schema"))
					pDBSchema=aux.item(i).getTextContent();
			}
			
			//entities info
			aux=ents.getChildNodes();
			for(int i=0,n=aux.getLength();i<n;i++){
				if(aux.item(i).getNodeName().equalsIgnoreCase("entity")){
					Entity temp=new Entity();
					entities.put(Utils.capitalize(aux.item(i).getAttributes().getNamedItem("name").getTextContent().trim()), temp.load(aux.item(i),null));
				}		
			}
			
			//modules info
			aux=exts.getChildNodes();
			for(int i=0,n=aux.getLength();i<n;i++){
				if(aux.item(i).getNodeName().equalsIgnoreCase("module")){
					Module temp=new Module();
					String name=aux.item(i).getAttributes().getNamedItem("name").getTextContent().trim();
					modules.put(name, temp.load(aux.item(i),cfgMan.getPluginConfig(language).getModuleConfig(name)));
				}		
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		loadedPath=path.getAbsolutePath();
		return true;
	}
	
	public boolean save(File path){
		try{
			String content=toString();
			
			PrintStream out=new PrintStream(path);
			out.println(content);
			out.close();
			
			changed = false;
			notifyViews();
			
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	public boolean isChanged(){ return changed; }
	protected void setChanged(){ 
		changed = true;
		notifyViews();
	}
	
	public String getLanguage(){ return language; }
	public String getDestinationPath(){ return destPath; }
	public String getLoadedPath(){ return loadedPath; }
	public Enumeration<String> getEntities(){ return entities.keys(); }
	public Entity getEntity(String name){ return entities.get(name); }
	public Enumeration<String> getModules(){ return modules.keys(); }
	public Module getModule(String name){ return modules.get(name); }
	public String getDBHost(){ return dDBHost; }
	public String getDBLogin(){	return dDBLogin; }
	public String getDBPassword(){ return dDBPassword; }
	public String getDBSchema(){ return dDBSchema; }
	
	public void setLoadedPath(String path) { loadedPath=path; }
	public void setDestinationPath(String path){ destPath=path; setChanged(); }
	public void setLanguage(String lang){ language=lang; setChanged(); }
	public void setDBHost(String v){ dDBHost=v; setChanged(); }
	public void setDBLogin(String v){ dDBLogin=v; setChanged(); }
	public void setDBPassword(String v){ dDBPassword=v; setChanged(); }
	public void setDBSchema(String v){ dDBSchema=v; setChanged(); }
	
	public boolean renameEntity(String src, String dst){
		if(entities.get(dst)!=null) return false;
		entities.put(dst,entities.get(src));
		entities.remove(src);
		return true;
	}
	
	public boolean addEntity(String name){
		if(entities.get(name)!=null) return false;
		entities.put(name,new Entity(name,null));
		setChanged();
		return true;
	}
	public void removeEntity(String name){
		entities.remove(name);
		setChanged();
	}
	
	public void clearModules(){
		modules.clear();
	}
	public void addModule(String name){
		if(modules.get(name)==null){
			ModuleConfig cfg=cfgMan.getPluginConfig(language).getModuleConfig(name);
			modules.put(name,new Module(name,cfg));
			setChanged();
		}
	}
	public void removeModule(String name){
		if(modules.get(name)!=null){
			modules.remove(name);
			setChanged();
		}
	}
	
	// Model/View pattern
	public void attachView(View v){
		views.add(v);
		v.update(this);
	}
	
	public void dettachView(View v){
		views.remove(v);
		v.update(null);
	}
	
	public void notifyViews(){
		for(View v : views)
			v.update(this);
	}
	public static Model getInstance(){
		return getInstance(false);
	}
	//singleton pattern
	public static Model getInstance(boolean newInstance){
		if(me==null || newInstance) me=new Model();
		return me;
	}
	
	//misc
	public String toString(){
		String ret="";
		ret+="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		ret+="<project lastsaved=\""+System.currentTimeMillis()+"\" by=\""+System.getProperty("user.name")+"\" language=\""+language+"\">\n";
		ret+="	<file-server-conf>\n";
		ret+="		<base-directory><![CDATA["+destPath+"]]></base-directory>\n";
		ret+="	</file-server-conf>\n";
		ret+="	<db-server-conf>\n";
		ret+="		<dev-host><![CDATA["+dDBHost+"]]></dev-host>\n";
		ret+="		<dev-user><![CDATA["+dDBLogin+"]]></dev-user>\n";
		ret+="		<dev-password><![CDATA["+dDBPassword+"]]></dev-password>\n";
		ret+="		<dev-schema><![CDATA["+dDBSchema+"]]></dev-schema>\n";
		ret+="		<prod-host><![CDATA["+pDBHost+"]]></prod-host>\n";
		ret+="		<prod-user><![CDATA["+pDBLogin+"]]></prod-user>\n";
		ret+="		<prod-password><![CDATA["+pDBPassword+"]]></prod-password>\n";
		ret+="		<prod-schema><![CDATA["+pDBSchema+"]]></prod-schema>\n";
		ret+="	</db-server-conf>\n";
		ret+="	<modules>\n";
		for(Enumeration<String> e=modules.keys();e.hasMoreElements();)	
			ret+=modules.get(e.nextElement()).toString();
		ret+="	</modules>\n";
		ret+="	<entities>\n";
		for(Enumeration<String> e=entities.keys();e.hasMoreElements();)	
			ret+=entities.get(e.nextElement()).toString();
		ret+="	</entities>\n";
		ret+="</project>\n";
		return ret;
	}
}
