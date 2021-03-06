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
public class Field implements Serializable{
	private Entity container;
	private FieldConfig fCfg;
	private Hashtable<String, String> options;
	private String label, map, type;
	private boolean required, visible, listable, searchable;
		
	public Field(Entity _container){
		container=_container;
		options=new Hashtable<String, String>();
	}
	
	protected Field load(Node current){
		//recuperar la informacion asociada al campo
		NodeList info=current.getChildNodes();
		type=current.getAttributes().getNamedItem("type").getTextContent();
		
		if(type.equals("primary-key")) return null; //skip
		
		fCfg=ConfigManager.getInstance()
						.getLangConfig(Model.getInstance().getLanguage())
						.getFieldConfig(type);

		if(fCfg==null){
			System.err.println("Warning: Unsupported field type '"+type+"' for the language '"+Model.getInstance().getLanguage()+"'");
			return null;
		}
		
		for(Enumeration<String> e=fCfg.getOptions();e.hasMoreElements();){
			String key=e.nextElement();
			options.put(key,fCfg.getOption(key).indexOf('|')!=-1?fCfg.getDefault(key):fCfg.getOption(key));//this is the default value, at least if isn't a selection
		}
				
		for(int k=0,p=info.getLength();k<p;k++){
			if(info.item(k).getNodeName().equalsIgnoreCase("label"))
				label=(info.item(k).getTextContent());
			else if(info.item(k).getNodeName().equalsIgnoreCase("db-field"))
				map=(info.item(k).getTextContent());
			else if(info.item(k).getNodeName().equalsIgnoreCase("required"))
				required=(info.item(k).getTextContent().equals("0")?false:true);
			else if(info.item(k).getNodeName().equalsIgnoreCase("show-in-list"))
				listable=(info.item(k).getTextContent().equals("0")?false:true);	
			else if(info.item(k).getNodeName().equalsIgnoreCase("show-in-form"))
				visible=(info.item(k).getTextContent().equals("0")?false:true);
			else if(info.item(k).getNodeName().equalsIgnoreCase("searchable"))
				searchable=(info.item(k).getTextContent().equals("0")?false:true);
			else if(info.item(k).getNodeName().equalsIgnoreCase("options")){
				//cargar todas las opciones particulares para control.
				NodeList opts=info.item(k).getChildNodes();
				for(int l=0,t=opts.getLength();l<t;l++){
					if(opts.item(l).getNodeName().equalsIgnoreCase("option"))
						options.put(opts.item(l).getAttributes().getNamedItem("name").getTextContent(),Utils.revertXMLSafe(opts.item(l).getAttributes().getNamedItem("value").getTextContent()));
				}
			}
		}
		return this;
	}
	
	public void setLabel(String label) {
		this.label = label;
		container.setChanged();
	}
		
	public void setMap(String map) {
		this.map = map;
		container.setChanged();
	}

	public void setType(String type) {
		this.type = type;
		//de acuerdo al tipo actual inicializar las opciones con las opciones en el fieldConfig asociado
		options.clear();
		
		if(type.equals("primary-key")) return; //es un campo temporal
			
		fCfg=ConfigManager.getInstance()
						.getLangConfig(Model.getInstance().getLanguage())
						.getFieldConfig(type);
		
		if(fCfg==null){
			System.err.println("Warning: Unsupported field type '"+type+"' for the language '"+Model.getInstance().getLanguage()+"'");
			container.removeField(this);
		}
		
		for(Enumeration<String> e=fCfg.getOptions();e.hasMoreElements();){
			String key=e.nextElement();
			options.put(key,fCfg.getOption(key).indexOf('|')!=-1?fCfg.getDefault(key):fCfg.getOption(key));//this is the default value, at least if isn't a selection
		}
		
		container.setChanged();
	}

	public Enumeration<String> getOptions(){ return options.keys(); }
	public String getOption(String name){ return options.get(name); }
	public FieldConfig getFieldConfig(){ return fCfg; }
	
	public void setOption(String name,String value){ 
		options.put(name, value); 
		container.setChanged();
	}
	
	public void setRequired(boolean required) {
		this.required = required;
		container.setChanged();
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		container.setChanged();
	}
	
	public void setListable(boolean listable) {
		this.listable = listable;
		container.setChanged();
	}
	
	public void setSearchable(boolean v){
		this.searchable=v;
		container.setChanged();
	}

	public String getLabel(){ return label;	}
	public String getMap(){	return map; }
	public String getType(){ return type;}
	public boolean isRequired(){ return required; }
	public boolean isVisible(){	return visible;	}
	public boolean isListable(){ return listable; }
	public boolean isSearchable(){ return searchable; }

	public String toString(String deep){
		StringBuffer ret=new StringBuffer(); 
		ret.append(deep+"		<field type=\""+type+"\">\n")
			.append(deep+"			<label><![CDATA["+label+"]]></label>\n")
			.append(deep+"			<db-field><![CDATA["+Utils.sanitize(map)+"]]></db-field>\n")
			.append(deep+"			<required>"+(required?1:0)+"</required><!-- 0|1 -->\n")
			.append(deep+"			<show-in-list>"+(listable?1:0)+"</show-in-list><!-- 0|1 -->\n")
			.append(deep+"			<show-in-form>"+(visible?1:0)+"</show-in-form><!-- 0|1 -->\n")
			.append(deep+"			<searchable>"+(searchable?1:0)+"</searchable><!-- 0|1 -->\n")
			.append(deep+"			<options>\n");

			for(Enumeration<String> e=options.keys();e.hasMoreElements();){
				String key=e.nextElement();
				ret.append(deep+"				<option name=\""+key+"\" value=\""+Utils.xmlSafe(options.get(key))+"\"/>\n");
			}
				 
		ret.append(deep+"			</options>\n")
			 .append(deep+"		</field>\n");
		return ret.toString();	
	}

	public void cleanOptions() {
		options.clear();
	}

	public boolean equals(Object o1){
		Field o = (Field)o1;
		return map.equals(o.map);	
	}

	@Override
	public int hashCode(){
		int hash = 7;
		hash = 97 * hash + (this.map != null ? this.map.hashCode() : 0);
		return hash;
	}
}
