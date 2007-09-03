package genad.engine;

import com.sun.corba.se.impl.logging.UtilSystemException;
import genad.config.ConfigManager;
import java.io.*;
import java.sql.*;
import java.util.*; 

//import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import genad.*;
import genad.model.*;
import genad.config.*;

/**
 *  copy and generate the CRUD pages for the entities applying xsl to simples xml files.
 *	the engine can be extends of Model to attach the view for show the progress or any other event 
 *	@author kronenthaler
 */
public class Engine{
	private Connection conn=null;
	private Statement stmt=null;
	
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
		config modulo (configModule.xsl)
		execute sql script
	generar entidades (mod.xsl, list.xsl, exec.xsl, obj.xsl)
		usan un xml modificado para poder generar todos los archivos, el nuevo xml es basicamente el mismo que se usa para
		definir cada entidad pero con un tag <parent id="primary-key" class="ClassName"/>
	*/
	public void generate(Model model){
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
		
			//2) topological sort, which module must be first that other one
			String[] order=Utils.topologicalSort(Utils.convert(model.getModules()), ConfigManager.getInstance().getPluginConfig(model.getLanguage()));
			
			//3) copy base files (basic structure and core inmutable files)
			Utils.copyDirectory(new File("res/"+model.getLanguage()+"/core"), new File(model.getDestinationPath()));
			
			//4) make DB (db.xsl)
			File dbSQL=new File(model.getDestinationPath()+"/db.sql");
			replaceIfExists(dbSQL);
			
			StringWriter dbOut=new StringWriter();
			transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/db.xsl" ,dbOut);
			
			executeScript(dbOut.getBuffer().toString(),model);
			
			conn.close();
			conn=DriverManager.getConnection("jdbc:mysql://"+model.getDBHost()+"/"+model.getDBSchema(),model.getDBLogin(),model.getDBPassword());
			
			out=new PrintStream(dbSQL);
			out.print(dbOut.getBuffer());
			out.close();
						
			//5) make the DB connection file (connection.xsl). asf for overwrite if exists
			File connex=new File(model.getDestinationPath()+"/obj/Connection."+model.getLanguage());
			replaceIfExists(connex);
			
			out=new PrintStream(connex);
			transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/connection.xsl", out);
			out.close();
			
			//6) make JS validators (js.xsl). ask for overwrite if exists
			File js=new File(model.getDestinationPath()+"/js/validators.js");
			replaceIfExists(js);
			
			out=new PrintStream(js);
			transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/js.xsl", out);
			out.close();
			
			//7) make the includes file (includes.xsl). asf for overwrite if exists
			File includes=new File(model.getDestinationPath()+"/includes."+model.getLanguage());
			replaceIfExists(includes);
			
			out=new PrintStream(includes);
			transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/includes.xsl", out);
			out.close();
			
			//8) copy, configure, and dump database for each module following the T.S. calculated in (2)
			for(String s:order){
				Utils.copyDirectory(new File("res/"+model.getLanguage()+"/modules/"+s), new File(model.getDestinationPath()+"/"+s));
				//transformXML();
				//execute script (read if exists the file <module>/<module>.sql)
			}
			
			//9) index.xhtml (index.xsl). ask for overwrite if exists
			File index=new File(model.getDestinationPath()+"/admin/index.xhtml");
			replaceIfExists(index);
			
			out=new PrintStream(index);
			transformXML(model.getLoadedPath(), "res/"+model.getLanguage()+"/xsl/index.xsl", out);
			out.close();//*/
			
			//10) entities files. ask for overwrite if exists
			for(Enumeration<String> e=model.getEntities();e.hasMoreElements();)
				transformEntities(model.getEntity(e.nextElement()), model);
			
		}catch(RuntimeException e){
			Utils.showError("Fatal Error: "+e.getMessage()+"\n"+e.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn!=null)
				try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
	}
	
	private void transformEntities(Entity ent, Model model){
		try{
			String xml=ent.genXML();
			PrintStream out =null;
			
			File list = new File(model.getDestinationPath()+"/admin/list"+ent.getName()+"."+model.getLanguage());
			File exec = new File(model.getDestinationPath()+"/admin/exec"+ent.getName()+"."+model.getLanguage());
			File mod = new File(model.getDestinationPath()+"/admin/mod"+ent.getName()+"."+model.getLanguage());
			File obj = new File(model.getDestinationPath()+"/obj/"+ent.getName()+"."+model.getLanguage());

			if((ent.hasJustPages() && !ent.hasJustSchema()) || (!ent.hasJustPages() && !ent.hasJustSchema())){
				replaceIfExists(list);
				out=new PrintStream(list);
				transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/list.xsl", out);
				out.close();

				replaceIfExists(exec);
				out=new PrintStream(exec);
				transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/exec.xsl", out);
				out.close();

				replaceIfExists(mod);
				out=new PrintStream(mod);
				transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/mod.xsl", out);
				out.close();

				replaceIfExists(obj);
				out=new PrintStream(obj);
				transformXML(new StringReader(xml), "res/"+model.getLanguage()+"/xsl/obj.xsl", out);
				out.close();

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
			
			script=script.replaceAll("\n|\t", "");
			StringTokenizer strT=new StringTokenizer(script,";");
			while(strT.hasMoreTokens())
				stmt.addBatch(strT.nextToken());
			
			stmt.executeBatch();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(stmt!=null)
				try{stmt.close();}catch(Exception e){e.printStackTrace();}
		}
	}
	
	private void replaceIfExists(File f){
		if(f.exists()){
			File temp=new File(f.toString()+".old");
			if(temp.exists())
				temp.delete();

			//f.renameTo(temp); //TODO: descomentar
		}
	}
}
