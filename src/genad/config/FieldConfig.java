package genad.config;

import java.util.*;
/**
 *
 *	@author kronenthaler
 */
public class FieldConfig{
	private String type;
	private boolean visible=false,listable=false;
	private Hashtable<String, String> options;
	 
	public FieldConfig(String _type){
		type=_type;
		options=new Hashtable<String,String>();
	}
	
	public void setVisible(boolean v){visible=v;}
	public void setListable(boolean v){listable=v;}
	public void setOption(String key,String value){ options.put(key,value);}
	
	public String getType(){ return type; }
	public boolean isVisible(){ return visible; }
	public boolean isListable(){ return listable; }
	public String getOption(String key){ return options.get(key); } 
		
	public String toString(){
		String ret="\t\t<field type=\""+type+"\" visible=\""+visible+"\" listable=\""+listable+"\""+(options.size()>0?"":"/")+">\n";
		if(options.size()>0){
			for(Enumeration<String> e=options.keys();e.hasMoreElements();){
				String key=e.nextElement();
				ret+="\t\t\t<option name=\""+key+"\" value=\""+options.get(key)+"\"/>\n";
			}
			ret+="\t\t</field>\n";
		}
		return ret;
	}
}
