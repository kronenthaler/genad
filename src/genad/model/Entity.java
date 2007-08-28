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
	public Entity parent;
	private String name, title, primaryKey, tableName;
	private boolean pager, search, sortable, justSchema, justPages;
	private boolean changed=false;
	
	public Entity(){
		form=new Vector<Field>();
		childs=new Hashtable<String, Entity>();
	}
	
	public Entity(String _name,Entity _parent){
		this();
		name=_name;
		parent=_parent;
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
					Field field=new Field(this);
					if(fields.item(j).getNodeName().equalsIgnoreCase("field")){
						//field.load(fields.item(j));
						form.add(field.load(fields.item(j)));
					}//ignore anything else
				}
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("permissions")){
				//ret.setPermissions(Integer.parseInt(aux.item(i).getAttributes().getNamedItem("value").getTextContent()));
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("entity")){
				//recurrir para cargar subentidades
				Entity child=new Entity();
				//child.load(aux.item(i),this);
				childs.put(aux.item(i).getAttributes().getNamedItem("name").getTextContent().trim(),child.load(aux.item(i),this));
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
	public boolean setName(String s){
		boolean flag=false;
		
		if(parent==null) flag=Model.getInstance().renameEntity(name,s);
		else flag=parent.renameChild(name,s);
		
		if(flag){
			name=s.trim();//must be a valid ID in almost every language
			setChanged();
		}
		
		return flag && !(s==null || "".equals(s.trim()));
	} 
	private boolean renameChild(String src, String dst){
		if(childs.get(dst)!=null) return false;
		childs.put(dst, childs.get(src));
		childs.remove(src);
		return true;
	}
	
	public String getTitle(){ return title; }
	public boolean setTitle(String s){
		title=s.trim();
		setChanged();
		return !(s==null || "".equals(s.trim()));
	} 
	
	public String getTableName(){ return tableName;	}
	public boolean setTableName(String s){
		tableName=s.trim();
		setChanged();
		return !(s==null || "".equals(s.trim()));
	} 
	
	public String getPrimaryKey(){ return primaryKey;	}
	public boolean setPrimaryKey(String s){
		primaryKey=s.trim();
		setChanged();
		return !(s==null || "".equals(s.trim()));
	} 
	
	public boolean hasPager(){ return pager; }
	public void setPager(boolean v){ pager=v; setChanged();}
	
	public boolean hasSearch(){ return search; }
	public void setSearch(boolean v){ search=v; setChanged();}
	
	public boolean isSortable(){ return sortable; }
	public void setSortable(boolean v){ sortable=v; setChanged();}
	
	public boolean hasJustPages(){ return justPages; }
	public void setJustPages(boolean v){ justPages=v; setChanged();}
	
	public boolean hasJustSchema(){ return justSchema; }
	public void setJustSchema(boolean v){ justSchema=v; setChanged();}
		
	//@unsafe: shouldn't return the vector reference
	public Vector<Field> getFields(){ return form; }
	
	//childs manipulation
	public Enumeration<String> getChilds(){ return childs.keys(); } 
	public Entity getChild(String name){ return childs.get(name); }
	public boolean addChild(String name){
		if(childs.get(name)!=null) return false;
		childs.put(name,new Entity(name,this));
		setChanged();
		return true;
	}
	public void removeChild(String name){
		childs.remove(name);
		setChanged();
	}
	
	//Form manipulation
	public void addField(){
		form.add(new Field(this));
		setChanged();
	}
	public void removeField(int rowIndex){
		if(rowIndex!=-1){
			form.remove(rowIndex);
			setChanged();
		}
	}
	public void moveUp(int rowIndex){
		if(rowIndex!=-1 && rowIndex>0){
			Field aux=form.get(rowIndex);
			form.remove(rowIndex);
			form.insertElementAt(aux,rowIndex-1);
			setChanged();
		}
	}
	public void moveDown(int rowIndex){
		if(rowIndex!=-1 && rowIndex+1<form.size()){
			Field aux=form.get(rowIndex);
			form.remove(rowIndex);
			form.insertElementAt(aux,rowIndex+1);
			setChanged();
		}
	}
	
	private boolean isValid(){
		//validate the entity
		if("".equals(title))		return Utils.showError("Title cannot be empty in entity: "+name);
		if("".equals(name))			return Utils.showError("Name cannot be empty in entity: "+name);
		if("".equals(tableName))	return Utils.showError("Table Name cannot be empty in entity: "+name);
		if("".equals(primaryKey))	return Utils.showError("Primary Key cannot be empty in entity: "+name);
		
		if(!justPages && form.size()==0)
			return Utils.showError("The entity: "+name+" must has at least one field");
		
		boolean visible=false,listable=false,repited=false,empty=false;
		Hashtable<String, Boolean> buffer=new Hashtable<String, Boolean>();
		for(Field f : form){
			visible |= f.isVisible();
			listable |= f.isListable();
			if(f.getMap()!=null && "".equals(f.getMap())){ empty=true; break; }
			if(buffer.get(f.getMap())!=null){ repited=true;break; }
		}
		
		if(!justPages && !listable)
			return Utils.showError("At least one field must be listed in entity: "+name);
		
		if(!justPages && !visible)
			return Utils.showError("At least one field must be visible in entity: "+name);
		
		if(empty)
			return Utils.showError("The fields cannot has a empty map in entity: "+name);
		
		if(repited)
			return Utils.showError("The entity: "+name+" cannot contain repited map fields");
		
		return true;
	}
	
	private String toString(String deep){
		if(!isValid()) throw new ArrayIndexOutOfBoundsException("Invalid Entity: "+name);
		
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
		changed = false;
		return ret;
	}
	
	public String toString(){
		return toString("\t\t");
	}
}