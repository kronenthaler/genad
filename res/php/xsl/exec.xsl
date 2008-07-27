<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	<xsl:template match="/"><![CDATA[<?]]>
	include_once('../includes.php');
	
	<xsl:if test="/entity/permissions/@value != ''">
	<xsl:choose>
		<xsl:when test="/entity/permissions/@value = 'standard' or /entity/permissions/@value = 'plus'">
	$action = strtoupper($_REQUEST['action']);
		</xsl:when>
		<xsl:otherwise>
	$action = 'your-action-here';
		</xsl:otherwise>
	</xsl:choose>
	$section = '<xsl:value-of select="//@name"/>';
	include_once('../common/checksession.php');
	</xsl:if>
	
	$action =  $_REQUEST['action'];
	$id = $_REQUEST['id'];
	$obj = new <xsl:value-of select="//@name"/>();
	
	if($action=='add'){
		if(!$obj->insert())
			$error=$obj->getError();
	}else if($action =='mod'){
		if(!$obj->update($id))
			$error=$obj->getError();
	}else if($action == 'del'){
		if(!$obj->delete($id))
			$error=$obj->getError();
	}else if($action == 'sort'){
		if(!$obj->order($id))
			$error=$obj->getError();
	}else{
		$error="No action especified";
	}
	
	header("Location: list<xsl:value-of select="//@name"/><![CDATA[.php?ini=".$_REQUEST['ini']."&error=".$error.$obj->getAncestorsIds());
?>]]></xsl:template>
</xsl:stylesheet>