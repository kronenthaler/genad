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
public class ConfigManager{
	public static final String PATH="conf/";
	public static final String MAIN_CONF="/conf.xml";
	
	private Hashtable<String, PluginConfig> pluginsConfig;
	private Config mainConfig;
		
	public ConfigManager(){
		mainConfig=new Config();
		pluginsConfig=new Hashtable<String, PluginConfig>();
		
		loadConfiguration();
	}
	
	private void loadConfiguration(){
		mainConfig.load(PATH+MAIN_CONF);
		//foreach module installed in the mainConfig load its configuration.
		Vector<String> installed=mainConfig.getPluginsInstalled();
		for(String id : installed)
			pluginsConfig.put(id,new PluginConfig(PATH + id + MAIN_CONF));
	}
}
