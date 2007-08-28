package genad.config;

import java.util.*;
/**
 *
 *	@author kronenthaler
 */
public class FieldConfig{
	private String type;
	private boolean visible=false,listable=false, searchable=false;
	private Hashtable<String, String> options;
	private Hashtable<String, String> defaults;//only for the option with fixed values separated by |
		 
	public FieldConfig(String _type){
		type=_type;
		options=new Hashtable<String,String>();
		defaults=new Hashtable<String,String>();
	}
	
	public void setVisible(boolean v){ visible=v; }
	public void setListable(boolean v){ listable=v; }
	public void setSearchable(boolean v){ searchable=v; }
	public void setOption(String key,String value){ options.put(key,value); }
	public void setDefault(String option, String value){ defaults.put(option,value); }
	
	public String getType(){ return type; }
	public boolean isVisible(){ return visible; }
	public boolean isListable(){ return listable; }
	public boolean isSearchable(){ return searchable; }
	public String getOption(String key){ return options.get(key); } 
	public String getDefault(String option){ return defaults.get(option); } 
	public Enumeration<String> getOptions(){ return options.keys();}
	public int getOptionSize(){ return options.size(); }
		
	public String toString(){
		String ret="\t\t<field type=\""+type+"\" visible=\""+visible+"\" listable=\""+listable+"\""+(options.size()>0?"":"/")+">\n";
		if(options.size()>0){
			for(Enumeration<String> e=options.keys();e.hasMoreElements();){
				String key=e.nextElement();
				ret+="\t\t\t<option name=\""+key+"\" value=\""+options.get(key)+"\" "+(defaults.get(key)!=null?"default=\""+defaults.get(key)+"\"":"")+"/>\n";
			}
			ret+="\t\t</field>\n";
		}
		return ret;
	}
}
