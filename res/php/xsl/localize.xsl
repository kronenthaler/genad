<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>

	<xsl:template match="/"><![CDATA[<?]]>
		<xsl:apply-templates select="project/entities/entity"/>
		<xsl:apply-templates select="project/relations/relation"/>
<![CDATA[?>]]></xsl:template>

	<xsl:template match="entity">
define('MSG_<xsl:value-of select="translate(@name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE', '<xsl:value-of select="@title"/>');
<xsl:for-each select="form/field">define('MSG_<xsl:value-of select="translate(../../@name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_<xsl:value-of select="translate(db-field, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE', '<xsl:value-of select="label"/>');
</xsl:for-each>
<xsl:apply-templates select="entity"/>
	</xsl:template>

	<xsl:template match="relation">
define('MSG_<xsl:value-of select="translate(@name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE', '<xsl:value-of select="@title"/>');
<xsl:for-each select="form/field">define('MSG_<xsl:value-of select="translate(../../@name, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_<xsl:value-of select="translate(db-field, 'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>_TITLE', '<xsl:value-of select="label"/>');
</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>