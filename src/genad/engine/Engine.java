package genad.engine;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;


/**
 *  copy and generate the CRUD pages for the entities applying xsl to simples xml files.
 *	@author kronenthaler
 */
public class Engine{
	
	public Engine(){
	}
	
	public void transformXML(String xml, String xsl){
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

			StreamResult result = new StreamResult(System.out); //change the outputstream to a file
			transformer.transform(source, result);
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}
}
