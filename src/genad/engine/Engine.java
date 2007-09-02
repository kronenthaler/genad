package genad.engine;

import genad.config.ConfigManager;
import java.io.*;
import java.util.*; 

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import genad.*;
import genad.model.*;
import genad.config.*;

/**
 *  copy and generate the CRUD pages for the entities applying xsl to simples xml files.
 *	@author kronenthaler
 */
public class Engine{
	
	public void transformXML(String xml, String xsl, OutputStream out){
		try {
			//si se van a usar xml templates con DTD's deberia usarse el DOMSource
			/*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			DOMSource source=new DOMSource(doc);//*/
			
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
			//si se van a usar xml templates con DTD's deberia usarse el DOMSource
			/*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xml);
			DOMSource source=new DOMSource(doc);//*/
			
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
			//1) calculate files to copy
			long coreFiles=Utils.countFiles(new File("res/"+model.getLanguage()+"/core"));
			long moduleFiles=0;
			
			for(Enumeration<String> e=model.getModules();e.hasMoreElements();)
				moduleFiles+=Utils.countFiles(new File("res/"+model.getLanguage()+"/modules/"+e.nextElement()));
			
			/*Vector<String> report=new Vector<String>();
			report.add("Core Files to copy: "+coreFiles);
			report.add("Module Files to copy: "+moduleFiles);
			report.add("Total Files to copy: "+(coreFiles+moduleFiles));
			report.add("Entity Files to create: "+ 4*Utils.convert(model.getEntities()).length);//*/
			
			//2) topological sort, which module must be first that other one
			String[] order=Utils.topologicalSort(Utils.convert(model.getModules()), ConfigManager.getInstance().getPluginConfig(model.getLanguage()));
			
			//3) copy base files (basic structure and core inmutable files)
			
			//4) make DB (db.xsl)
			
			//5) make the DB connection file (connection.xsl). asf for overwrite if exists
			
			//6) make validation JS (js.xsl). ask for overwrite if exists
			
			//7) make the includes file (includes.xsl). asf for overwrite if exists
			
			//8) copy, configure, and dump database for each module following the T.S. calculated in (2)
			
			//9) index.xhtml (index.xsl). ask for overwrite if exists
			
			//10) entities files. ask for overwrite if exists
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
