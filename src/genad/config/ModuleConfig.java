package genad.config;

import java.util.*;

/**
 *
 *	@author kronenthaler
 */
public class ModuleConfig{
	private String name;
	private boolean mandatory=false;
	private Vector<String> depends;
	private Hashtable<String, String> options;
	private Hashtable<String, String> defaults;//only for the option with fixed values separated by |
	
	public ModuleConfig(String _name){
		setName(_name);
		depends=new Vector<String>();
		options=new Hashtable<String,String>();
		defaults=new Hashtable<String,String>();
	}

	public boolean isMandatory(){ return mandatory;	}
	public String getOption(String key){ return options.get(key);}
	public String getDefault(String option){ return defaults.get(option); } 
	public Vector<String> getDependencies(){ return depends; }
	public String getName(){ return name;}
	public int getOptionSize(){ return options.size();}
	public Enumeration<String> getOptions(){ return options.keys();}

	public void setName(String v){ name = v; }
	public void setMandatory(boolean v){ mandatory = v; }
	public void setOption(String key,String value){ options.put(key,value);}
	public void setDefault(String option, String value){ defaults.put(option,value); }
	public void setDependency(String depend){ depends.add(depend); }
		
	public String toString(){
		String ret="\t\t<module name=\""+name+"\" "+(mandatory?"mandatory=\"1\"":"")+(options.size()==0 && depends.size()==0?"/":"")+">\n";
		if(options.size()!=0 || depends.size()!=0){
			if(depends.size()!=0){
				ret+="\t\t\t<depends>\n";
				for(String str : depends)
					ret+="\t\t\t\t<depend name=\""+str+"\"/>\n";
				ret+="\t\t\t</depends>\n";
			}
			
			if(options.size()!=0){
				ret+="\t\t\t<options>\n";
				for(Enumeration<String> e=options.keys();e.hasMoreElements();){
					String key=e.nextElement();
					ret+="\t\t\t\t<option name=\""+key+"\" value=\""+options.get(key)+"\" "+(defaults.get(key)!=null?"default=\""+defaults.get(key)+"\"":"")+"/>\n";
				}
				ret+="\t\t\t</options>\n";
			}
			ret+="\t\t</module>\n";
		}
		return ret;
	}
}
