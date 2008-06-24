package genad.model;

import java.io.*;

import genad.model.*;

/**
 *
 * @author kronenthaler
 */
public class DelayedFile{
	private Engine engine;
	private File path;
	private String xsl;
	private int type;
	
	private Reader in;
	private String inPath;
	private Writer out;
	
	private DelayedFile(Engine _engine, File _path, String _xsl){
		engine=_engine;
		path=_path;
		xsl=_xsl;
	}
	
	public DelayedFile(Engine _engine, File _path, String _xsl, Reader _in){
		this(_engine, _path, _xsl);
		in=_in;
		type = 1;
	}
	
	public DelayedFile(Engine _engine, File _path, String _xsl, String _in){
		this(_engine, _path, _xsl);
		inPath = _in;
		type = 2;
	}
	
	public void create(){
		try{
			//backup
			System.err.println("Creating Backup for: "+path);
			if(path.exists()){
				File temp=new File(path.toString()+".old");
				if(temp.exists())
					temp.delete();
				path.renameTo(temp);
			}
			
			if(type == 1){
				PrintStream out = new PrintStream(path);
				engine.transformXML(in, xsl, out);
				out.close();
			}else if(type == 2){
				PrintStream out = new PrintStream(path);
				engine.transformXML(inPath, xsl, out);
				out.close();
			}else if(type == 3){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getPath(){
		return path.getAbsolutePath();
	}
	
	public File getFile(){
		return path;
	}
}
