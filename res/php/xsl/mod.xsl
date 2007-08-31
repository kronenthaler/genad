<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:variable name="ids">
		<xsl:for-each select="/entity/parent">.'<![CDATA[&]]><xsl:value-of select="@id"/>='.$_REQUEST['<xsl:value-of select="@id"/>']</xsl:for-each>
	</xsl:variable>
	<xsl:template match="/">
<![CDATA[
<?
	include_once('../includes.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new ]]><xsl:value-of select="//@name"/><![CDATA[();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('No id found');	
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors();
		
		//has an array for each field with options, if this options has many values, then the entry is an array of arrays]]>
		<xsl:apply-templates select="/entity/form/field"/>
		
<![CDATA[				 
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>
]]>
	</xsl:template>
	
	<xsl:template match="field">
		<!-- hacer el choose por los types para determinar cuales
			atributos especificos son necesarios por tipo y 
			adicionalmente los de configuracion -->
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','time' => format($obj->t01_model,TIME,'H:m:s'));
	</xsl:template>
	
	
</xsl:stylesheet>