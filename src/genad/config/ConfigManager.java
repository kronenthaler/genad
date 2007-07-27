package genad.config;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

//@TODO: encapsular los metodos de la clase mainConfig para que sean accedidos solamente desde esta clase.
/**
 *	Loads the configuration files for GenAd itself and the modules installed, leaving the 
 *	information in the class Config
 *	@author kronenthaler
 */
public class ConfigManager{
	public static final String PATH="conf/";
	public static final String MAIN_CONF="/conf.xml";
	private static ConfigManager me=null;
	
	private Hashtable<String, PluginConfig> pluginsConfig;
	private Config mainConfig;
		
	private ConfigManager(){
		me=this;
		refreshConfiguration();
	}
	
	private void loadConfiguration(){
		mainConfig.load(PATH+MAIN_CONF);
		//foreach module installed in the mainConfig load its configuration.
		Vector<String> installed=mainConfig.getPluginsInstalled();
		for(String id : installed)
			pluginsConfig.put(id,new PluginConfig(PATH + id + MAIN_CONF));
	}
	
	public void saveConfiguration() throws IOException {
		mainConfig.save();
		
		Vector<String> installed=mainConfig.getPluginsInstalled();
		for(Enumeration<String> e=pluginsConfig.keys();e.hasMoreElements(); )
			pluginsConfig.get(e.nextElement()).save();
	}
	
	public void refreshConfiguration(){
		mainConfig=new Config();
		pluginsConfig=new Hashtable<String, PluginConfig>();
		
		loadConfiguration();
	}
	
	public static ConfigManager getInstance(){
		if(me==null)
			me=new ConfigManager();
		return me;
	}
	
	public void installPlugin(String name){
		mainConfig.getPluginsInstalled().add(name);
		mainConfig.getPluginsActive().put(name,false);
	}
	
	public void activePlugin(String name, boolean status){
		mainConfig.getPluginsActive().put(name, status);
	}
	
	public Hashtable<String,PluginConfig> getPluginsConfig(){ return pluginsConfig;}
	public Hashtable<String, Boolean> getPluginsActive(){ return mainConfig.getPluginsActive();}
	public Vector<String> getPluginsInstalled(){ return mainConfig.getPluginsInstalled();}
	public String getDefaultValue(String option){ return mainConfig.getDefaultValue(option);}
}
