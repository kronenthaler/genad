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

/**
 *
 *	@author kronenthaler
 */
public class Module implements Serializable {
	private Hashtable<String,String> options;
	private String name;
	private ModuleConfig cfg;
	private boolean changed=false;
	
	public Module(){
		options=new Hashtable<String,String>();
	}
	
	public Module(String _name, ModuleConfig _cfg){
		this();
		name=_name;
		cfg=_cfg;
		
		String[] keys=Utils.convert(cfg.getOptions());
		for(String k : keys)
			options.put(k,cfg.getOption(k).indexOf('|')!=-1?cfg.getDefault(k):cfg.getOption(k));//this is the default value, at least if isn't a selection
	}
	
	public Module load(Node current,ModuleConfig _cfg){
		cfg=_cfg;
		
		String[] keys=Utils.convert(cfg.getOptions());
		for(String k : keys)
			options.put(k,cfg.getOption(k).indexOf('|')!=-1?cfg.getDefault(k):cfg.getOption(k));//this is the default value, at least if isn't a selection
		
		name=current.getAttributes().getNamedItem("name").getTextContent();
		NodeList opts=current.getChildNodes();
		for(int i=0,n=opts.getLength();i<n;i++){
			if(opts.item(i).getNodeName().equals("option"))
				options.put(opts.item(i).getAttributes().getNamedItem("name").getTextContent(),
							opts.item(i).getAttributes().getNamedItem("value").getTextContent());
		}
		
		return this;
	}
	public boolean isChanged(){ return changed; }
	public String getName(){ return name; } 
	public ModuleConfig getModuleConfig(){ return cfg; }
	public String getOption(String key){ return options.get(key); }
	public void setOption(String key, String value){ options.put(key,value); setChanged(); }
	protected void setChanged(){
		changed=true;
		Model.getInstance().setChanged();
	}
	
	public String toString(){
		StringBuffer ret=new StringBuffer();
		ret.append("		<module name=\""+name+"\">\n");
		for(Enumeration<String> e=options.keys();e.hasMoreElements();){
			String key=e.nextElement();
			ret.append("			<option name=\""+key+"\" value=\""+options.get(key)+"\"/>\n");
		}
		ret.append("		</module>\n");
		changed=false;
		return ret.toString();
	}
}
