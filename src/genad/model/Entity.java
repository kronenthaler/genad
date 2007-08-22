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
public class Entity{
	private Vector<Field> form;
	private Hashtable<String, Entity> childs;
	private Entity parent;
	private String name, title, primaryKey, tableName;
	private boolean pager, search, sortable, justSchema, justPages;
	private boolean changed=false;
	
	public Entity(){
		form=new Vector<Field>();
		childs=new Hashtable<String, Entity>();
	}
	
	//load an entity from xml Node
	protected Entity load(Node current, Entity _parent){
		parent=_parent;
		
		//parse the dom loading the entities
		name=Utils.revertXMLSafe(Utils.capitalize(current.getAttributes().getNamedItem("name").getTextContent()));
		title=Utils.revertXMLSafe(current.getAttributes().getNamedItem("title").getTextContent());
		
		//cargar la info adicional
		NodeList aux=current.getChildNodes();
		for(int i=0,n=aux.getLength();i<n;i++){
			if(aux.item(i).getNodeName().equalsIgnoreCase("table")){
				tableName=aux.item(i).getAttributes().getNamedItem("name").getTextContent();
				primaryKey=aux.item(i).getAttributes().getNamedItem("primary-key").getTextContent();
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("splitpage")){
				pager=aux.item(i).getAttributes().getNamedItem("value").getTextContent().equals("0")?false:true;
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("search")){
				search=aux.item(i).getAttributes().getNamedItem("value").getTextContent().equals("0")?false:true;	
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("just-pages")){
				justPages=aux.item(i).getAttributes().getNamedItem("value").getTextContent().equals("0")?false:true;	
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("just-schema")){
				justSchema=(aux.item(i).getAttributes().getNamedItem("value").getTextContent().equals("0")?false:true);
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("sortable")){
				sortable=(aux.item(i).getAttributes().getNamedItem("value").getTextContent().equals("0")?false:true);	
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("form")){
				//recuperar todos los campos
				NodeList fields=aux.item(i).getChildNodes();
				for(int j=0,m=fields.getLength();j<m;j++){
					Field field=new Field();
					if(fields.item(j).getNodeName().equalsIgnoreCase("field")){
						//field.load(fields.item(j));
						form.add(field.load(fields.item(j),this));
					}//ignore anything else
				}
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("permissions")){
				//ret.setPermissions(Integer.parseInt(aux.item(i).getAttributes().getNamedItem("value").getTextContent()));
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("entity")){
				//recurrir para cargar subentidades
				Entity child=new Entity();
				//child.load(aux.item(i),this);
				childs.put(aux.item(i).getAttributes().getNamedItem("name").getTextContent().trim(),child.load(aux.item(i),null));
			}
		}
		return this;
	}
	
	public boolean isChanged(){ return changed; }
	protected void setChanged(){
		changed=true;
		Model.getInstance().setChanged();
	}
	
	public String getName(){ return name; }
	public void setName(String s){ name=s; setChanged();} 
	
	public Enumeration<String> getChilds(){ return childs.keys(); } 
	public Entity getChild(String name){ return childs.get(name); }
	
	private String toString(String deep){
		Enumeration<String> e;
		String key,
		ret=deep+"<entity name=\""+Utils.sanitize(name)+"\" title=\""+Utils.xmlSafe(title)+"\">\n";
		ret+=deep+"	<table name=\""+Utils.sanitize(tableName)+"\" primary-key=\""+Utils.sanitize(primaryKey)+"\"/>\n";
		//ret+=deep+"	<permissions value=\""+permissions+"\"/>\n";
		ret+=deep+"	<splitpage value=\""+(pager?1:0)+"\"/>\n";
		ret+=deep+"	<search value=\""+(search?1:0)+"\"/>\n";
		ret+=deep+"	<just-pages value=\""+(justPages?1:0)+"\"/>\n";
		ret+=deep+"	<just-schema value=\""+(justSchema?1:0)+"\"/>\n";
		ret+=deep+"	<sortable value=\""+(sortable?1:0)+"\"/>\n";
		ret+=deep+"	<form>\n";
		for(Field f:form)
			ret+=f.toString(deep);
		ret+=deep+"	</form>\n";
		
		for(e=childs.keys();e.hasMoreElements();)
			ret+=(childs.get(e.nextElement())).toString(deep+"\t");	
		
		ret+=deep+"</entity>\n";
		return ret;
	}
	
	public String toString(){
		return toString("\t\t");
	}
}