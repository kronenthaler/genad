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
public class Field{
	private Entity container;
	private Hashtable<String, String> options;
	public String label, map, type;
	public boolean required,visible,listable;
		
	public Field(){
		options=new Hashtable<String, String>();
	}
	
	protected Field load(Node current,Entity _container){
		container=_container;
		//recuperar la informacion asociada al campo
		NodeList info=current.getChildNodes();
		type=current.getAttributes().getNamedItem("type").getTextContent();

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
	
	public String toString(String deep){
		String 
		ret=deep+"		<field type=\""+type+"\">\n"+
			deep+"			<label><![CDATA["+label+"]]></label>\n"+
			deep+"			<db-field><![CDATA["+Utils.sanitize(map)+"]]></db-field>\n"+
			deep+"			<required>"+(required?1:0)+"</required><!-- 0|1 -->\n"+
			deep+"			<show-in-list>"+(listable?1:0)+"</show-in-list><!-- 0|1 -->\n"+
			deep+"			<show-in-form>"+(visible?1:0)+"</show-in-form><!-- 0|1 -->\n"+
			deep+"			<options>\n";

			for(Enumeration<String> e=options.keys();e.hasMoreElements();){
				String key=e.nextElement();
				ret+=deep+"				<option name=\""+key+"\" value=\""+Utils.xmlSafe(options.get(key))+"\"/>\n";
			}
				 
		ret+=deep+"			</options>\n"+
			 deep+"		</field>\n";
		return ret;	
	}
}
