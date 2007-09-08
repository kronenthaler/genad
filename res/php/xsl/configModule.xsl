<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<?]]>
		<xsl:for-each select="module/option">
	define('<xsl:value-of select="@name"/>','<xsl:value-of select="@value"/>');</xsl:for-each>
<![CDATA[?>]]></xsl:template>
</xsl:stylesheet>