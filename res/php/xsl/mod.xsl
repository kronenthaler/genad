<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:variable name="ids">
		<xsl:for-each select="/entity/parent">.'<![CDATA[&]]><xsl:value-of select="@id"/>='.$_REQUEST['<xsl:value-of select="@id"/>']</xsl:for-each>
	</xsl:variable>
	<xsl:template match="/"><![CDATA[<?
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
		//begin field options]]>
		<xsl:apply-templates select="/entity/form/field"/>
<![CDATA[		
		//end field options
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>]]></xsl:template>
	
	<xsl:template match="field">
		<!-- hacer el choose por los types para determinar cuales
			atributos especificos son necesarios por tipo y 
			adicionalmente los de configuracion -->
		<xsl:choose>
		<!-- ignore: textfield, integer, decimal, textarea -->	
		<xsl:when test="@type = 'hidden'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_');</xsl:when>
		<xsl:when test="@type = 'time'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','time' => format($obj-><xsl:value-of select="db-field"/>,TIME,'H:m:s'));</xsl:when>
		<xsl:when test="@type = 'date'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','date' => format($obj-><xsl:value-of select="db-field"/>,DATE,'M/D/Y'));</xsl:when>
		<xsl:when test="@type = 'datetime'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','date' => format($obj-><xsl:value-of select="db-field"/>,DATE,'M/D/Y'), 'time' => format($obj-><xsl:value-of select="db-field"/>,TIME,'H:m:s'));</xsl:when>
		<xsl:when test="@type = 'password'">
		$options["<xsl:value-of select="db-field"/>"]=array('confirm'=>'Confirm');</xsl:when>
		<xsl:when test="@type = 'radio' or @type='checkbox'">
		$options["<xsl:value-of select="db-field"/>"]=array(
			//one array per option with the label, the value associated with, if is selected for this object or not and the javascript 'onclick' code.
			<xsl:for-each select="options/option">array('name'=> '<xsl:value-of select="@name"/>','value' =>'<xsl:value-of select="@value"/>','selected'=>$obj-><xsl:value-of select="../../db-field"/>==<xsl:value-of select="@value"/>?'true':'','onclick'=>'')<xsl:if test="position() != last()">,
			</xsl:if></xsl:for-each>
		);</xsl:when>
		<xsl:when test="@type='select'">
		$options["<xsl:value-of select="db-field"/>"]=array('onclick'=>'',
			//one array per option with the label, the value associated with and if is selected for this object or not.
			<xsl:for-each select="options/option">array('name'=> '<xsl:value-of select="@name"/>','value' =>'<xsl:value-of select="@value"/>','selected'=>$obj-><xsl:value-of select="../../db-field"/>==<xsl:value-of select="@value"/>?'true':'')<xsl:if test="position() != last()">,
			</xsl:if></xsl:for-each>
		);</xsl:when>
		<xsl:when test="@type='file' or @type='image'">
		$options["<xsl:value-of select="db-field"/>"]=array('path' => $obj-><xsl:value-of select="db-field"/>,
			<xsl:for-each select="options/option">array('name'=> '<xsl:value-of select="@name"/>','value' =>'<xsl:value-of select="@value"/>')<xsl:if test="position() != last()">,
			</xsl:if></xsl:for-each>
		);</xsl:when>
		</xsl:choose>
	</xsl:template>
	
	
</xsl:stylesheet>