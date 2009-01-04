<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<?
if(stristr($_SERVER['HTTP_HOST'],'localhost')){
	define('ROOT',']]><xsl:value-of select="/project/file-server-conf/base-directory"/><![CDATA[');
	define('HTTP_ROOT','http://localhost/');
}else{
	define('ROOT',']]><xsl:value-of select="/project/file-server-conf/base-directory"/><![CDATA[');			  //cambiar por la ruta final
	define('HTTP_ROOT','http://'.$_SERVER['HTTP_HOST'].'/'); //cambiar por la ruta final
}

include_once(ROOT.'/common/localize.php');
include_once(ROOT.'/common/utils.php');
include_once(ROOT.'/obj/AbstractObject.php');
include_once(ROOT.'/obj/AbstractRelation.php');
include_once(ROOT.'/obj/Connection.php');
include_once(ROOT.'/obj/Error.php');
include_once(ROOT.'/obj/Modules.php');

//module's includes
]]><xsl:apply-templates select="/project/modules/module"/>

//*/
//project's classes
<xsl:apply-templates select="/project/entities/entity"/>
<xsl:apply-templates select="/project/relations/relation"/>
<![CDATA[?>]]></xsl:template>
	
	<xsl:template match="module">
secureInclude('<xsl:value-of select="@name"/>/<xsl:value-of select="@name"/>.php');</xsl:template>
	
	<xsl:template match="entity">
<xsl:if test="just-schema/@value = 0">include_once('obj/<xsl:value-of select="@name"/>.php');
</xsl:if><xsl:apply-templates select="entity"/>
	</xsl:template>

	<xsl:template match="relation">
<xsl:if test="just-schema/@value = 0">include_once('obj/<xsl:value-of select="@name"/>.php');
</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>