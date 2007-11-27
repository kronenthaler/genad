<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<?
include_once('common/utils.php');
include_once('obj/AbstractObject.php');	
include_once('obj/Connection.php');
include_once('obj/Error.php');

define('ROOT',']]><xsl:value-of select="/project/file-server-conf/base-directory"/><![CDATA[');

//module's includes
]]><xsl:apply-templates select="/project/modules/module"/>

//*/
//project's classes
<xsl:apply-templates select="/project/entities/entity"/>
<![CDATA[
//connect with the database
$connection=new Connection();
?>]]></xsl:template>
	
	<xsl:template match="module">
secureInclude('<xsl:value-of select="@name"/>/<xsl:value-of select="@name"/>.php');</xsl:template>
	
	<xsl:template match="entity">
include_once('obj/<xsl:value-of select="@name"/>.php');<xsl:apply-templates select="entity"/>
	</xsl:template>		
	
</xsl:stylesheet>