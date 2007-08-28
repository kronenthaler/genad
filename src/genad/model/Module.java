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
public class Module{
	private Hashtable<String,String> options;
	private String name;
	
	public Module(){
		options=new Hashtable<String,String>();
	}
	
	public Module(String _name){
		this();
		name=_name;
	}
	
	public Module load(Node current){
		name=current.getAttributes().getNamedItem("name").getTextContent();
		NodeList opts=current.getChildNodes();
		for(int i=0,n=opts.getLength();i<n;i++){
			if(opts.item(i).getNodeName().equals("option"))
				options.put(opts.item(i).getAttributes().getNamedItem("name").getTextContent(),
							opts.item(i).getAttributes().getNamedItem("value").getTextContent());
		}
		
		return this;
	}
	
	public String toString(){
		String ret="		<module name=\""+name+"\">\n";
		for(Enumeration<String> e=options.keys();e.hasMoreElements();){
			String key=e.nextElement();
			ret+="			<option name=\""+key+"\" value=\""+options.get(key)+"\"/>\n";
		}
		ret+="		</module>\n";
		return ret;
	}
}
