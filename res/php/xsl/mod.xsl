<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:variable name="ids">
		<xsl:for-each select="/entity/parent">.'<![CDATA[&]]><xsl:value-of select="@id"/>='.$_REQUEST['<xsl:value-of select="@id"/>']</xsl:for-each>
	</xsl:variable>

	<xsl:template match="/"><xsl:apply-templates/></xsl:template>

	<xsl:template match="entity"><![CDATA[<?
	include_once('../includes.php');]]>
	
	<xsl:if test="permissions/@value != ''">
		<xsl:choose>
			<xsl:when test="permissions/@value = 'standard' or permissions/@value = 'plus'">
	$action = 'MOD';
			</xsl:when>
			<xsl:otherwise>
	$action = 'your-action-here';
			</xsl:otherwise>
		</xsl:choose>
	$section = '<xsl:value-of select="//@name"/>';
	include_once('../common/checksession.php');
	</xsl:if>
	
	<![CDATA[
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past

	$obj=new ]]><xsl:value-of select="//@name"/><![CDATA[();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('ID not found');
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="getResource.php?file=admin/mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">
			<prefix></prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors(); 
		//begin field options]]>
		<xsl:apply-templates select="form/field"/>
<![CDATA[//end field options
		echo $obj->getXMLJSValidator($options);
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>]]></xsl:template>

	<xsl:template match="relation"><![CDATA[<?
	include_once('../includes.php');]]>

	<xsl:if test="permissions/@value != ''">
		<xsl:choose>
			<xsl:when test="permissions/@value = 'standard' or permissions/@value = 'plus'">
	$action = 'MOD';
			</xsl:when>
			<xsl:otherwise>
	$action = 'your-action-here';
			</xsl:otherwise>
		</xsl:choose>
	$section = '<xsl:value-of select="//@name"/>';
	include_once('../common/checksession.php');
	</xsl:if>

	<![CDATA[
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past

	$obj=new ]]><xsl:value-of select="//@name"/><![CDATA[();
	eval("\$currentClass = new ".$_REQUEST['currentClass']."();");

	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('ID not found');

	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="getResource.php?file=admin/modRelation.xsl"?>
		  <entity name="'.get_class($obj).'"
		  		  ext="php"
		  		  action="'.$_REQUEST['action'].'"
		  		  id="'.$_REQUEST['id'].'"
				  rel_id="'.$_REQUEST['rel_id'].'">
			<prefix></prefix>';
		echo $currentClass->getXMLTitle();
		echo $obj->getXMLAncestors($currentClass);

		$listables = $obj->getFieldsByType(LISTABLE);

		for($j=0;$j<count($obj->classes);$j++){
			eval("\$o = new ".$obj->classes[$j]."();");
			
			if($o->primarykey == $currentClass->primarykey){
				if($_REQUEST['action'] == 'add'){
					$currentClass->load($_REQUEST['rel_id']);
					foreach($currentClass->fields as $key => $value){
						eval("\$obj->".$key."= \$currentClass->".$key.';');
					}

					$options[$o->primarykey] = array("value"=>$_REQUEST['rel_id'],"prefix"=>"str_");
				}

				continue;
			}
			
			$field = '';
			if(strpos($listables[$j],$o->tablename) !== FALSE)
				$field = substr($listables[$j],strpos($listables[$j],'`.`')+3);//$o->fields[][TITLE];

			$list = $o->getList();
			$option=array('onclick'=>"");
			for($i=0;$i<count($list);$i++){
				echo "<!--".$field."-->";
				eval("\$name = \$list[\$i]->".$field.";");//nombre del campo name
				eval("\$value = \$list[\$i]->".$list[$i]->primarykey.";");
				array_push($option,array("name"=>$name,"value"=>$value,"selected"=>$value == 0?"selected":""));
			}

			$options[$o->primarykey]=$option;
		}
		//begin field options]]>
		<xsl:apply-templates select="form/field"/>
<![CDATA[
		//end field options
		echo $obj->getXMLJSValidator($options);
		echo $obj->getXMLForm($options,$currentClass);
	echo "</entity>";
?>]]></xsl:template>
	
	<xsl:template match="field">
		<!-- hacer el choose por los types para determinar cuales
			atributos especificos son necesarios por tipo y 
			adicionalmente los de configuracion -->
		<xsl:choose>
		<!-- ignore: textfield, integer, decimal, textarea -->	
		<xsl:when test="@type = 'richtext'">
		$options["<xsl:value-of select="db-field"/>"]=array('toolbar'=>'<xsl:value-of select="options/option/@value"/>');</xsl:when>
		<xsl:when test="@type = 'hidden'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_');</xsl:when>
		<xsl:when test="@type = 'time'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','time' => format($obj-><xsl:value-of select="db-field"/>,TIME,'H:m:s'));</xsl:when>
		<xsl:when test="@type = 'date'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','date' => format($obj-><xsl:value-of select="db-field"/>,DATE,'D/M/Y'));</xsl:when>
		<xsl:when test="@type = 'datetime'">
		$options["<xsl:value-of select="db-field"/>"]=array('prefix'=>'str_','date' => format($obj-><xsl:value-of select="db-field"/>,DATE,'D/M/Y'), 'time' => format(substr($obj-><xsl:value-of select="db-field"/>,8),TIME,'H:m:s'));</xsl:when>
		<xsl:when test="@type = 'password'">
		$options["<xsl:value-of select="db-field"/>"]=array('confirm'=>'Confirm <xsl:value-of select="label"/>');</xsl:when>
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
		<xsl:when test="@type='file'">
			<!-- crear una instancia del objeto upload y escribir las opciones utilizando el metodo -->
			$upload=new Upload("<xsl:value-of select="db-field"/>",$obj-><xsl:value-of select="db-field"/>);
			<xsl:comment>Use the function setProperty to change some parameter of the upload</xsl:comment>
			$options["<xsl:value-of select="db-field"/>"]=$upload->getOptions();
		</xsl:when>
		<xsl:when test="@type='image'">
			<!-- crear una instancia del objeto upload y escribir las opciones utilizando el metodo -->
			$upload=new ImageUpload("<xsl:value-of select="db-field"/>",$obj-><xsl:value-of select="db-field"/>/*,0,array(array(thumb_width,thumb_height,thumb_prefix,filter-name), ... )*/);
			<xsl:comment>Use the function setProperty to change some parameter of the upload</xsl:comment>
			$options["<xsl:value-of select="db-field"/>"]=$upload->getOptions();
		</xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>