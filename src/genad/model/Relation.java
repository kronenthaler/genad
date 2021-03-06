/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genad.model;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import genad.*;
import genad.gui.*;
import genad.config.*;


/**
 *
 * @author kronenthaler
 */
public class Relation extends Entity implements Serializable{
	private Vector<Entity> entities;
	
	public Relation(){
		form = new Vector<Field>();
		entities = new Vector<Entity>();
	}
	
	public Relation(String _name){
		this();
		name = _name;
	}

	public Vector<Entity> getEntities(){
		return entities;
	}

	protected Relation load(Node current){
		Model model = Model.getInstance();
		
		name=Utils.revertXMLSafe(Utils.capitalize(current.getAttributes().getNamedItem("name").getTextContent()));
		title=Utils.revertXMLSafe(current.getAttributes().getNamedItem("title").getTextContent());
		
		//cargar la info adicional
		NodeList aux=current.getChildNodes();
		for(int i=0,n=aux.getLength();i<n;i++){
			if(aux.item(i).getNodeName().equalsIgnoreCase("table")){
				tableName=aux.item(i).getAttributes().getNamedItem("name").getTextContent();
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
				String entName = aux.item(i).getAttributes().getNamedItem("name").getTextContent();
				Entity related = model.findEntity(entName);
				if(related!=null)
					entities.add(related);
				else
					throw new ArrayIndexOutOfBoundsException("Entity '"+entName+"' is missing in relation: '"+name+"'");
			}
		}
		
		return this;
	}
	
	protected boolean isValid(){
		//TODO: validate this relation, must have 2 entities at least, extra fields are optionals,
		//the class name must be unique, equal to the entities.
		
		if(title==null || "".equals(title))		return Utils.showError("Title cannot be empty in relation: "+name);
		if("".equals(name))			return Utils.showError("Name cannot be empty in relation: "+name);
		if(tableName==null || "".equals(tableName))	return Utils.showError("Table Name cannot be empty in relation: "+name);
		if(entities.size() < 2)		return Utils.showError("At least two entities must be related in relation: "+name);

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
			return Utils.showError("At least one field must be searchable in relation: "+name);

		if(empty)
			return Utils.showError("The fields cannot has a empty map in relation: "+name);

		if(repeated)
			return Utils.showError("The relation: "+name+" cannot contain repeated map fields");

		return true;
	}

	public void addEntity(String ent){
		entities.add(Model.getInstance().findEntity(ent));
		setChanged();
	}

	public void removeEntity(String ent){
		entities.remove(Model.getInstance().findEntity(ent));
		setChanged();
	}

	public boolean setName(String s){
		boolean flag=false;
		s=Utils.capitalize(s.trim());
		
		flag=Model.getInstance().renameRelation(name,s);
		
		//TODO: must be a valid ID in almost every language
		if(flag){
			name=s;
			setChanged();
		}
		
		return flag && !"".equals(s);
	} 

	public String genXML(){
		//TODO: generate a more comprenhensive and useful XML for the XSLT.
		return toString();
	}

	public String toString(String deep){
		if(!isValid()) throw new ArrayIndexOutOfBoundsException("Invalid Relation: "+name);
		
		StringBuffer ret = new StringBuffer();
		ret.append(deep+"<relation name=\""+Utils.sanitize(name)+"\" title=\""+Utils.xmlSafe(title)+"\">\n")
			.append(deep+"	<table name=\""+Utils.sanitize(tableName)+"\"/>\n")
			.append(deep+"	<permissions value=\""+permissions+"\"/>\n")
			.append(deep+"	<splitpage value=\""+(pager?1:0)+"\"/>\n")
			.append(deep+"	<search value=\""+(search?1:0)+"\"/>\n")
			.append(deep+"	<just-pages value=\""+(justPages?1:0)+"\"/>\n")
			.append(deep+"	<just-schema value=\""+(justSchema?1:0)+"\"/>\n")
			.append(deep+"	<sortable value=\""+(sortable?1:0)+"\"/>\n")
			.append(deep+"	<form>\n");
		
		for(Field f:form)
			ret.append(f.toString(deep));
		ret.append(deep+"	</form>\n");
			
		for(Entity e: entities){
			Vector<Field> fs = e.getFields();
			String type = "primary-key";
			for(Field f : fs){
				if(f.getMap().equals(e.primaryKey)){
					type = f.getType();
					break;
				}
			}
			ret.append(deep+"	<entity name=\""+Utils.sanitize(e.getName())+"\" primarykey=\""+Utils.sanitize(e.getPrimaryKey())+"\" type=\""+type+"\"><![CDATA["+e.getTitle()+"]]></entity>\n");
		}
		
		ret.append(deep+"</relation>\n");
		changed = false;
		return ret.toString();
	}
	
	public String toString(){
		return toString("\t\t");
	}
}
