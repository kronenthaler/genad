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
public class Entity implements Serializable{
	protected Vector<Field> form;
	private Hashtable<String, Entity> childs;
	private Entity parent;
	protected String name, title, primaryKey, tableName;
	protected String permissions = "";
	protected boolean pager, search, sortable, justSchema, justPages;
	protected boolean changed=false;
	
	public static final String STANDARD = "standard";
	public static final String STANDARD_PLUS = "plus";
	public static final String OTHERS = "others";
	public static final String NONE = "";
	
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
						if((field=field.load(fields.item(j)))!=null)
							form.add(field);
					}//ignore anything else
				}
			}else if(aux.item(i).getNodeName().equalsIgnoreCase("permissions")){
				permissions = aux.item(i).getAttributes().getNamedItem("value").getTextContent();
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
		s=Utils.capitalize(s.trim());
		
		if(parent==null) flag=Model.getInstance().renameEntity(name,s);
		else flag=parent.renameChild(name,s);
		
		//TODO:must be a valid ID in almost every language
		if(flag){
			name=s;
			setChanged();
		}
		
		return flag && !"".equals(s);
	} 
	private boolean renameChild(String src, String dst){
		if(childs.get(dst)!=null) return false;
		if("".equals(dst.trim()) || childs.get(src)==null) return false;
		childs.put(dst, childs.get(src));
		childs.remove(src);
		return true;
	}
	
	public String getTitle(){ return title; }
	public boolean setTitle(String s){
		title=s.trim();
		setChanged();
		return !"".equals(s.trim());
	} 
	
	public String getTableName(){ return tableName;	}
	public boolean setTableName(String s){
		tableName=s.trim();
		setChanged();
		return !"".equals(s.trim());
	} 
	
	public String getPrimaryKey(){ return primaryKey;	}
	public boolean setPrimaryKey(String s){
		primaryKey=s.trim();
		setChanged();
		return !"".equals(s.trim());
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
	
	public String getPermissions(){ return permissions; }
	public void setPermissions(String v){ permissions=v; setChanged();}
	
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
	public void removeField(Field f){
		form.remove(f);
		setChanged();
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
	
	protected Entity findEntity(String name){
		if(childs.get(name)!=null) return childs.get(name);
		
		for(Enumeration<String> e=childs.keys();e.hasMoreElements();){
			Entity result = childs.get(e.nextElement()).findEntity(name);
			if(result != null) return result;
		}
		
		return null;
	}

	protected Vector<Entity> getAllEntities(){
		Vector<Entity> ret = new Vector<Entity>();

		for(Enumeration<String> e=childs.keys();e.hasMoreElements();){
			Entity ent = childs.get(e.nextElement());
			ret.add(ent);
			ret.addAll(ent.getAllEntities());
		}

		return ret;
	}
	
	protected boolean isValid(){
		//validate the entity
		if("".equals(title))		return Utils.showError("Title cannot be empty in entity: "+name);
		if("".equals(name))			return Utils.showError("Name cannot be empty in entity: "+name);
		if("".equals(tableName))	return Utils.showError("Table Name cannot be empty in entity: "+name);
		if("".equals(primaryKey))	return Utils.showError("Primary Key cannot be empty in entity: "+name);
		
		if(!justPages && form.size()==0)
			return Utils.showError("The entity: "+name+" must has at least one field");
		
		boolean visible=false,listable=false,searchable=false,repeated=false,empty=false;
		Hashtable<String, Boolean> buffer=new Hashtable<String, Boolean>();
		for(Field f : form){
			visible |= f.isVisible();
			listable |= f.isListable();
			searchable |= f.isSearchable();
			if(f.getMap()!=null && "".equals(f.getMap())){ empty=true; break; }
			if(buffer.get(f.getMap())!=null){ repeated=true;break; }
		}
		
		if(search && !searchable)
			return Utils.showError("At least one field must be searchable in entity: "+name);
			
		if(!justPages && !listable)
			return Utils.showError("At least one field must be listed in entity: "+name);
		
		if(!justPages && !visible)
			return Utils.showError("At least one field must be visible in entity: "+name);
		
		if(empty)
			return Utils.showError("The fields cannot has a empty map in entity: "+name);
		
		if(repeated)
			return Utils.showError("The entity: "+name+" cannot contain repeated map fields");
		
		if(justSchema && childs.size()>0)
			return Utils.showError("The entity: "+name+" cannot contain childs because is just an schema");
		
		return true;
	}
	
	private String toString(String deep){
		if(!isValid()) throw new ArrayIndexOutOfBoundsException("Invalid Entity: "+name);
		
		Enumeration<String> e;
		String key;
		StringBuffer ret =new StringBuffer();
		ret.append(deep+"<entity name=\""+Utils.sanitize(name)+"\" title=\""+Utils.xmlSafe(title)+"\">\n")
			.append(deep+"	<table name=\""+Utils.sanitize(tableName)+"\" primary-key=\""+Utils.sanitize(primaryKey)+"\"/>\n")
			.append(deep+"	<permissions value=\""+permissions+"\"/>\n")
			.append(deep+"	<splitpage value=\""+(pager?1:0)+"\"/>\n")
			.append(deep+"	<search value=\""+(search?1:0)+"\"/>\n")
			.append(deep+"	<just-pages value=\""+(justPages?1:0)+"\"/>\n")
			.append(deep+"	<just-schema value=\""+(justSchema?1:0)+"\"/>\n")
			.append(deep+"	<sortable value=\""+(sortable?1:0)+"\"/>\n")
			.append(deep+"	<form>\n");
		
		//buscar si el campo primarykey esta en la lista de fields, sino escribirlo
		Field pk=new Field(this);
		pk.setMap(primaryKey);
		pk.setType("primary-key");
		
		if(!form.contains(pk))
			ret.append(pk.toString(deep));
		
		for(Field f:form)
			ret.append(f.toString(deep));
		ret.append(deep+"	</form>\n");
		
		for(e=childs.keys();e.hasMoreElements();)
			ret.append((childs.get(e.nextElement())).toString(deep+"\t"));	
		
		ret.append(deep+"</entity>\n");
		changed = false;
		return ret.toString();
	}
	
	public String genXML(){
		Enumeration<String> e;
		String key;
		StringBuffer ret =new StringBuffer();
		ret.append("<entity name=\""+Utils.sanitize(name)+"\" title=\""+Utils.xmlSafe(title)+"\">\n");		
		if(parent!=null)
			ret.append("	<parent id=\""+parent.primaryKey+"\" class=\""+parent.name+"\"/>\n");
		
		for(e=childs.keys();e.hasMoreElements();){
			key=e.nextElement();
			ret.append("	<entity name=\""+Utils.sanitize(key)+"\"/>\n");			
		}
		
		ret.append("	<table name=\""+Utils.sanitize(tableName)+"\" primary-key=\""+Utils.sanitize(primaryKey)+"\"/>\n")
			.append("	<permissions value=\""+permissions+"\"/>\n")
			.append("	<splitpage value=\""+(pager?1:0)+"\"/>\n")
			.append("	<search value=\""+(search?1:0)+"\"/>\n")
			.append("	<just-pages value=\""+(justPages?1:0)+"\"/>\n")
			.append("	<just-schema value=\""+(justSchema?1:0)+"\"/>\n")
			.append("	<sortable value=\""+(sortable?1:0)+"\"/>\n")
			.append("	<form>\n");
		for(Field f:form)
			ret.append(f.toString(""));
		ret.append("	</form>\n");

		ret.append("	<relations>");
		for(Relation r:Model.getInstance().getRelations(name))
			ret.append("		<relation name=\""+r.getName()+"\"/>");
		ret.append("	</relations>");

		ret.append("</entity>\n");
		System.err.println(ret);
		return ret.toString();
	}
	
	public String toString(){
		return toString("\t\t");
	}

	void validateType(LangConfig lc) throws Exception {
		for(Enumeration<String> e=childs.keys();e.hasMoreElements();){
			Entity ent=childs.get(e.nextElement());
			for(Field f:ent.getFields()){
				if(lc.getFieldConfig(f.getType())==null)
					throw new Exception("Warning: Unsupported field type '"+f.getType()+"' for the language '"+lc.getName()+"'");
			}
			ent.validateType(lc);
		}
	}
	
	public boolean equals(Object o1){
		Entity o = (Entity)o1;
		return o.name.equals(this.name);
	}
}