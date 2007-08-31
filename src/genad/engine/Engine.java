package genad.engine;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import genad.*;
import genad.model.*;

/**
 *  copy and generate the CRUD pages for the entities applying xsl to simples xml files.
 *	@author kronenthaler
 */
public class Engine{
	
	public Engine(){
	}
	
	public void transformXML(String xml, String xsl,PrintStream out){
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
	
	public void generate(Model model){
		/*
		calcular # archivos a copiar
		copiar archivos base => estructura de directorios
		generar BD (bd.xsl)
		generar js (js.xsl)
		generar archivo de includes (includes.xsl)
		generar archivo de conexion a BD (connection.xsl)
		ordenamiento topologico de los modulos
		foreach Module
			copiar modulo
			config modulo (configModule.xsl)
			execute sql script
		generar entidades (mod.xsl, list.xsl, exec.xsl, obj.xsl)
			//usan un xml modificado para poder generar todos los archivos, el nuevo xml es basicamente el mismo que se usa para
			//definir cada entidad pero sin las subentidades y con el nombre del primary key del padre como atributo
			//y los ids de todos las entidades que estan encima de esta
		*/
	}
}
