package genad.config;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import genad.*;
import genad.gui.*;
import genad.model.*;

/**
 *	Plugin is equal to Language
 *	@author kronenthaler
 */
public class LangConfig implements Serializable{
	private String name;
	private String description;
	private String path;
	private Hashtable<String, FieldConfig> fields;
	private Hashtable<String, ModuleConfig> modules;
			
	public LangConfig(String path){
		fields=new Hashtable<String, FieldConfig>();
		modules=new Hashtable<String, ModuleConfig>();
		
		load(path);
	}
	
	private void load(String _path){
		try{
			path=_path;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//dbf.setValidating(true); //si se define un dtd para este archivo
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
			
			name=doc.getElementsByTagName("name").item(0).getTextContent();
			description=doc.getElementsByTagName("description").item(0).getTextContent();
			
			loadModules(doc);
			loadFields(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void loadModules(Document doc) {
		NodeList root=doc.getElementsByTagName("modules").item(0).getChildNodes();
		for(int i=0,n=root.getLength();i<n;i++){
			Node current=root.item(i);
			if(current.getNodeName().equals("module")){
				ModuleConfig mc=new ModuleConfig(current.getAttributes().getNamedItem("name").getTextContent());
				mc.setMandatory(current.getAttributes().getNamedItem("mandatory")!=null && current.getAttributes().getNamedItem("mandatory").getTextContent().equals("1"));
				
				NodeList childs=current.getChildNodes();
				for(int j=0,m=childs.getLength();j<m;j++){
					if(childs.item(j).getNodeName().equals("options")){
						NodeList childOpts=childs.item(j).getChildNodes();
						for(int k=0,p=childOpts.getLength();k<p;k++){
							if(childOpts.item(k).getNodeName().equals("option")){
								String name=childOpts.item(k).getAttributes().getNamedItem("name").getTextContent();
								mc.setOption(name, childOpts.item(k).getAttributes().getNamedItem("value").getTextContent());
								if(childOpts.item(k).getAttributes().getNamedItem("default")!=null)
									mc.setDefault(name, childOpts.item(k).getAttributes().getNamedItem("default").getTextContent());
							}
						}
					}else if(childs.item(j).getNodeName().equals("depends")){
						NodeList childDeps=childs.item(j).getChildNodes();
						for(int k=0,p=childDeps.getLength();k<p;k++){
							if(childDeps.item(k).getNodeName().equals("depend"))
								mc.setDependency(childDeps.item(k).getAttributes().getNamedItem("name").getTextContent());
						}
					}
				}
				//add the module config
				modules.put(mc.getName(),mc);
			}
		}
	}

	private void loadFields(Document doc) {
		NodeList root=doc.getElementsByTagName("fields").item(0).getChildNodes();
		for(int i=0,n=root.getLength();i<n;i++){
			Node current=root.item(i);
			if(current.getNodeName().equals("field")){
				FieldConfig fc=new FieldConfig(current.getAttributes().getNamedItem("type").getTextContent());
				fc.setVisible(current.getAttributes().getNamedItem("visible").getTextContent().equals("true"));
				fc.setListable(current.getAttributes().getNamedItem("listable").getTextContent().equals("true"));
				fc.setSearchable(current.getAttributes().getNamedItem("searchable").getTextContent().equals("true"));
				fc.setEditable("true".equals(current.getAttributes().getNamedItem("editable").getTextContent()));
				
				NodeList childs=current.getChildNodes();
				for(int j=0,m=childs.getLength();j<m;j++){
					if(childs.item(j).getNodeName().equals("option")){
						String name=childs.item(j).getAttributes().getNamedItem("name").getTextContent();
						fc.setOption(name,childs.item(j).getAttributes().getNamedItem("value").getTextContent());
						if(childs.item(j).getAttributes().getNamedItem("default")!=null)
							fc.setDefault(name, childs.item(j).getAttributes().getNamedItem("default").getTextContent());
					}
				}
				
				//add the field config
				fields.put(fc.getType(),fc);
			}
		}
	}
	
	protected void addModule(String path){
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//dbf.setValidating(true); //si se define un dtd para este archivo
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(path);
		
			loadModules(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void save()throws IOException{
		PrintStream out=new PrintStream(new FileOutputStream(path));
		out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.println(this);
		out.flush();
		out.close();
	}
	
	public String getName(){return name;}
	public String getDescription(){return description;}
	public FieldConfig getFieldConfig(String name){ return fields.get(name); }
	public String[] getModulesName(){ return Utils.convert(modules.keys()); }
	public ModuleConfig getModuleConfig(String name){ return modules.get(name); }
	public String[] getValidTypes(){ return Utils.convert(fields.keys()); }
		
	public String toString(){
		StringBuffer ret=new StringBuffer();
		ret.append("<plugin>\n")
			.append("\t<name>"+name+"</name>\n")
			.append("\t<description>"+description+"</description>\n")
			.append("\t<!-- MODULES DEFINITION -->\n")
			.append("\t<modules>\n");
			for(Enumeration<String> e=modules.keys();e.hasMoreElements();)
				ret.append(modules.get(e.nextElement()).toString());
		ret.append("\t</modules>\n")
			.append("\t<!-- FIELDS DEFINITION -->\n")
			.append("\t<fields>\n");
			for(Enumeration<String> e=fields.keys();e.hasMoreElements();)
				ret.append(fields.get(e.nextElement()).toString());
		ret.append("\t</fields>\n")
			.append("</plugin>");
		return ret.toString();
	}
}
