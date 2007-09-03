<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:template match="/"><![CDATA[<?]]>
	include_once('../includes.php');
		
	$action =  $_REQUEST['action'];
	$id = $_REQUEST['id'];
	$obj = new <xsl:value-of select="//@name"/>();
	
	//move to insert/update function
	//$obj->encodePasswords($_REQUEST);
	//$obj->formatTimes($_REQUEST);
	$obj->formatDatetimes($_REQUEST);
	
	if($action=='add'){
		if(!$obj->insert())
			$error=$obj->getError();
	}else if($action =='mod'){
		if(!$obj->update($id))
			$error=$obj->getError();
	}else if($action == 'del'){
		if(!$obj->delete($id))
			$error=$obj->getError();
	}else{
		$error="No action especified";
	}
	
	header("Location: list<xsl:value-of select="//@name"/><![CDATA[.php?ini=".$_REQUEST['ini']."&error=".$error.$obj->getAncestorsIds());
?>]]></xsl:template>
</xsl:stylesheet>