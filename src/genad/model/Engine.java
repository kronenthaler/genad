package genad.model;

import java.io.*;
import java.nio.*;
import java.sql.*;
import java.util.*; 
import javax.swing.*;

//import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import genad.*;
import genad.config.*;
import genad.gui.misc.*;

/**
 *  copy and generate the CRUD pages for the entities applying xsl to simples xml files.
 *	the engine can be extends of Model to attach the view for show the progress or any other event 
 *	@author kronenthaler
 */
public class Engine extends Model{
	private Connection conn=null;
	private Statement stmt=null;
	private Model model;
	private ProgressDlg progress;
	private boolean stop=false,allStop=false;

	public Engine(ProgressDlg p){
		progress=p;
	}
	
	public void transformXML(String xml, String xsl, OutputStream out){
		try {
			StreamSource source = new StreamSource(xml);
			StreamSource stylesource = new StreamSource(xsl);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(stylesource);

			StreamResult result = new StreamResult(out); //change the outputstream to a file
			transformer.transform(source, result);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void transformXML(String xml, String xsl, Writer out){
		try {
			StreamSource source = new StreamSource(xml);
			StreamSource stylesource = new StreamSource(xsl);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(stylesource);

			StreamResult result = new StreamResult(out); //change the outputstream to a file
			transformer.transform(source, result);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void transformXML(Reader xml, String xsl, OutputStream out){
		try {
			StreamSource source = new StreamSource(xml);
			StreamSource stylesource = new StreamSource(xsl);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(stylesource);

			StreamResult result = new StreamResult(out); //change the outputstream to a file
			transformer.transform(source, result);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void transformXML(Reader xml, String xsl, Writer out){
		try {
			StreamSource source = new StreamSource(xml);
			StreamSource stylesource = new StreamSource(xsl);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(stylesource);

			StreamResult result = new StreamResult(out); //change the outputstream to a file
			transformer.transform(source, result);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
	
	public void setModel(Model m){model=m;}
	
	/**
	calcular # archivos a copiar
	ordenamiento topologico de los modulos
	copiar archivos base => estructura de directorios
	generar BD (bd.xsl)
	generar js (js.xsl)
	generar archivo de includes (includes.xsl)
	generar archivo de conexion a BD (connection.xsl)
	foreach Module
		copiar modulo
		config modulo (configModule.xsl)1
		execute sql script
	generar entidades (mod.xsl, list.xsl, exec.xsl, obj.xsl)
		usan un xml modificado para poder generar todos los archivos, el nuevo xml es basicamente el mismo que se usa para
		definir cada entidad pero con un tag <parent id="primary-key" class="ClassName"/>
	*/
	public synchronized void generate(){
		try{
			PrintStream out=null;
			
			//create the connection to the db server
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+model.getDBHost()+"/",model.getDBLogin(),model.getDBPassword());
			
			//1) calculate files to copy
			long coreFiles=Utils.countFiles(new File("res/"+model.getLanguage()+"/core"));
			long moduleFiles=0;
			
			for(Enumeration<String> e=model.getModules();e.hasMoreElements();)
				moduleFiles+=Utils.countFiles(new File("res/"+model.getLanguage()+"/modules/"+e.nextElement()));
		
			progress.setText("Copying files: 0/"+(coreFiles+moduleFiles)+"...");
			progress.setMaximum((int)(coreFiles+moduleFiles)+5);
			progress.setProgress(0);
			
			//2) topological sort, which module must be first that other one
			String[] order=Utils.topologicalSort(Utils.convert(model.getModules()), ConfigManager.getInstance().getPluginConfig(model.getLanguage()));
			
			if(stop){ allStop=true; notifyAll(); return; }
			//3) copy base files (basic structure and core inmutable files)
			Utils.copyDirectory(new File("res/"+model.getLanguage()+"/core"), new File(model.getDestinationPath()));
			progress.setText("Copying files "+coreFiles+"/"+(coreFiles+moduleFiles)+"...");
			progress.setProgress((int)coreFiles);
			
			//4) make DB (db.xsl)
			progress.setText("Making database structure...");
			File dbSQL=new File(model.getDestinationPath()+"/db.sql");
			if(replaceIfExists(dbSQL)){
				StringWriter dbOut=new StringWriter();
				transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/db.xsl" ,dbOut);

				if(stop){ allStop=true; notifyAll(); return; }
				executeScript(dbOut.getBuffer().toString(),model);

				out=new PrintStream(dbSQL);
				out.print(dbOut.getBuffer());
				out.close();
			}

			conn.close();
			conn=DriverManager.getConnection("jdbc:mysql://"+model.getDBHost()+"/"+model.getDBSchema(),model.getDBLogin(),model.getDBPassword());
			progress.setProgress(progress.getProgress()+1);
			
			progress.setText("Generating configuration files...");
			//5) make the DB connection file (connection.xsl). asf for overwrite if exists
			File connex=new File(model.getDestinationPath()+"/obj/Connection."+model.getLanguage());
			if(replaceIfExists(connex)){
				out=new PrintStream(connex);
				transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/connection.xsl", out);
				out.close();
			}
			progress.setProgress(progress.getProgress()+1);
			
			if(stop){ allStop=true; notifyAll(); return; }
			//6) make JS validators (js.xsl). ask for overwrite if exists
			File js=new File(model.getDestinationPath()+"/admin/js/validators.js");
			if(replaceIfExists(js)){
				out=new PrintStream(js);
				transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/js.xsl", out);
				out.close();
			}
			progress.setProgress(progress.getProgress()+1);
			
			if(stop){ allStop=true; notifyAll(); return; }
			//7) make the includes file (includes.xsl). asf for overwrite if exists
			File includes=new File(model.getDestinationPath()+"/includes."+model.getLanguage());
			if(replaceIfExists(includes)){
				out=new PrintStream(includes);
				transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/includes.xsl", out);
				out.close();
			}
			progress.setProgress(progress.getProgress()+1);
			
			if(stop){ allStop=true; notifyAll(); return; }
			//8) index.xhtml (index.xsl). ask for overwrite if exists
			File index=new File(model.getDestinationPath()+"/admin/index.html");
			if(replaceIfExists(index)){
				out=new PrintStream(index);
				transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/index.xsl", out);
				out.close();
			}
			progress.setProgress(progress.getProgress()+1);
			
			//9) copy, configure, and dump database for each module following the T.S. calculated in (2)
			for(String s:order){
				progress.setText("Copying Module "+s+"...");
				
				if(stop){ allStop=true; notifyAll(); return; }
				
				File base=new File(model.getDestinationPath()+"/"+s);
				int cont=Utils.copyDirectory(new File("res/"+model.getLanguage()+"/modules/"+s), base);
				
				progress.setProgress(progress.getProgress()+cont);
				
				if(base.exists()){
					StringReader in=new StringReader(model.getModule(s).toString());
					File config=new File(model.getDestinationPath()+"/"+s+"/"+s+"Conf."+model.getLanguage());
					if(replaceIfExists(config)){
						out=new PrintStream(config);
						transformXML(in, "res/"+model.getLanguage()+"/xsl/configModule.xsl",out);
						out.close();
					}

					//execute script (read if exists the file <module>/<module>.sql)
					progress.setText("Making database structure for "+s+"...");
					File sql=new File(model.getDestinationPath()+"/"+s+"/"+s+".sql");
					if(sql.exists()){
						StringBuffer query=new StringBuffer();
						BufferedReader tmp=new BufferedReader(new FileReader(sql));
						char[] buffer = new char[1024];
						int aux=0;
						while((aux=tmp.read(buffer))!=-1) 
							query.append(buffer,0,aux);
						
						if(stop){ allStop=true; notifyAll(); return; }
						executeScript(query.toString(), model);
						tmp.close();
					}
				}
			}
			
			progress.setText("Generating entity files...");
			//10) entities files. ask for overwrite if exists
			for(Enumeration<String> e=model.getEntities();e.hasMoreElements();)
				transformEntities(model.getEntity(e.nextElement()), model);
			
		}catch(RuntimeException e){
			Utils.showError("Fatal Error: "+e.getMessage()+"\n"+e.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null)
				try{conn.close();}catch(Exception e){Utils.showError("Warning: The connection cannot be closed. The database server is running?");}
		}
	}
	
	private synchronized void transformEntities(Entity ent, Model model){
		try{
			if(stop){ allStop=true; notifyAll(); return; }
			
			String xml=ent.genXML();
			PrintStream out =null;
			
			File list = new File(model.getDestinationPath()+"/admin/list"+ent.getName()+"."+model.getLanguage());
			File exec = new File(model.getDestinationPath()+"/admin/exec"+ent.getName()+"."+model.getLanguage());
			File mod = new File(model.getDestinationPath()+"/admin/mod"+ent.getName()+"."+model.getLanguage());
			File obj = new File(model.getDestinationPath()+"/obj/"+ent.getName()+"."+model.getLanguage());

			if((ent.hasJustPages() && !ent.hasJustSchema()) || (!ent.hasJustPages() && !ent.hasJustSchema())){
				if(replaceIfExists(list)){
					out=new PrintStream(list);
					transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/list.xsl", out);
					out.close();
				}
				if(replaceIfExists(exec)){
					out=new PrintStream(exec);
					transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/exec.xsl", out);
					out.close();
				}
				if(replaceIfExists(mod)){
					out=new PrintStream(mod);
					transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/mod.xsl", out);
					out.close();
				}
				if(replaceIfExists(obj)){
					out=new PrintStream(obj);
					transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/obj.xsl", out);
					out.close();
				}
				
				for(Enumeration<String> e=ent.getChilds();e.hasMoreElements();)
					transformEntities(ent.getChild(e.nextElement()), model);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void executeScript(String script, Model model){
		try{
			stmt=conn.createStatement();
			
			script=script.replaceAll("\\s*#.*(\n|\r\n)","");//remove comments
			script=script.replaceAll("\n|\t|(\r\n)", "");	 //remove enters and tabs 
			
			System.err.println(script);
			
			StringTokenizer strT=new StringTokenizer(script,";");
			while(strT.hasMoreTokens())
				stmt.addBatch(strT.nextToken());
			
			stmt.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(stmt!=null)
				try{stmt.close();}catch(Exception e){Utils.showError("Warning: The statement cannot be closed.");}
		}
	}
	
	private boolean replaceIfExists(File f){
		if(f.exists()){
			if(Utils.showConfirm("The file "+f+" already exists.\nDo you want to replace it?")){
				File temp=new File(f.toString()+".old");
				if(temp.exists())
					temp.delete();

				f.renameTo(temp);
				return true;
			}
			return false;
		}
		return true;
	}
	
	//@TODO: can be improve it
	public synchronized void stopGeneration(){
		stop=true;
		notifyAll();
		while(!allStop)
			try{wait();}catch(Exception e){e.printStackTrace();}
	}
}
