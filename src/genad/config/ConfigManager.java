package genad.config;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

/**
 *	Loads the configuration files for GenAd itself and the modules installed, leaving the 
 *	information in the class Config
 *	@author kronenthaler
 */
public class ConfigManager implements Serializable{
	public static final String PATH="conf/";
	public static final String MAIN_CONF="/conf.xml";
	private static ConfigManager me=null;
	
	private Hashtable<String, LangConfig> pluginsConfig;
	private Config mainConfig;
		
	private ConfigManager(){
		refreshConfiguration();
	}
	
	private void loadConfiguration(){
		mainConfig.load(PATH+MAIN_CONF);
		//foreach module installed in the mainConfig load its configuration.
		Vector<String> installed=mainConfig.getLangsInstalled();
		for(String id : installed)
			pluginsConfig.put(id,new LangConfig(PATH + id + MAIN_CONF));
	}
	
	public void saveConfiguration() throws IOException {
		mainConfig.save();
		
		//Vector<String> installed=mainConfig.getPluginsInstalled();
		for(Enumeration<String> e=pluginsConfig.keys();e.hasMoreElements(); )
			pluginsConfig.get(e.nextElement()).save();
	}
	
	public void refreshConfiguration(){
		mainConfig=new Config();
		pluginsConfig=new Hashtable<String, LangConfig>();
		
		loadConfiguration();
	}
	
	public static ConfigManager getInstance(){
		if(me==null) me=new ConfigManager();
		return me;
	}
	
	public void installPlugin(String name){
		mainConfig.getLangsInstalled().add(name);
		mainConfig.getLangsActive().put(name,false);
	}
	
	public void installModule(String lang,String path){
		pluginsConfig.get(lang).addModule(path);
	}
	
	public void activePlugin(String name, boolean status){
		if(status)
			mainConfig.getLangsActive().put(name, status);
		else
			mainConfig.getLangsActive().remove(name);
	}
		
	public Enumeration<String> getPluginsName(){ return pluginsConfig.keys(); }
	public LangConfig getLangConfig(String name){ return pluginsConfig.get(name); }
	public Enumeration<String> getLangsActive(){ return mainConfig.getLangsActive().keys(); }
	public boolean isLangActive(String name){ return mainConfig.getLangsActive().get(name); }
	public Vector<String> getLangsInstalled(){ return mainConfig.getLangsInstalled();}
	public String getDefaultValue(String option){ return mainConfig.getDefaultValue(option);}
}
