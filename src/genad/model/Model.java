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
	private String destPath, loadedPath;
	private String language;
	private String pDBHost, pDBLogin, pDBPassword, pDBSchema;
	private String dDBHost, dDBLogin, dDBPassword, dDBSchema;

	private Model(){
		views=new Vector<View>();
		entities=new Hashtable<String, Entity>();
		modules=new Hashtable<String, Module>();
		me=this;
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
					modules.put(Utils.capitalize(aux.item(i).getAttributes().getNamedItem("name").getTextContent().trim()), temp.load(aux.item(i)));
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
			PrintStream out=new PrintStream(path);
			out.println(toString());
			out.close();
			return true;
		}catch(Exception e){
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
	public String getDBHost(int mode){ return mode==PRODUCTION?pDBHost:dDBHost; }
	public String getDBLogin(int mode){	return mode==PRODUCTION?pDBLogin:dDBLogin; }
	public String getDBPassword(int mode){ return mode==PRODUCTION?pDBPassword:dDBPassword; }
	public String getDBSchema(int mode){ return mode==PRODUCTION?pDBSchema:dDBSchema; }
		
	// Model/View pattern
	public void attachView(View v){
		views.add(v);
		v.update(this);
	}
	
	public void notifyViews(){
		for(View v : views)
			v.update(this);
	}
	
	//singleton pattern
	public static Model getInstance(){
		if(me==null) new Model();
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
