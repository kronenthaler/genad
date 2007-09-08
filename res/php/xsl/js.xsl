<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="project/entities/entity"/>
	</xsl:template>
	
	<xsl:template match="entity">
function validate<xsl:value-of select="@name"/>(f){
	return true
	<xsl:for-each select="form/field">
		<xsl:choose>
		<xsl:when test="@type = 'textfield' or @type = 'select' or @type = 'hidden' or @type = 'date' or @type='time' or @type='file' or @type='image' or @type='textarea'">
		<xsl:if test="required = 1"><![CDATA[&&]]> isRequired(f.str_<xsl:value-of select="db-field"/>,"<xsl:value-of select="label"/>")</xsl:if></xsl:when>
		<xsl:when test="@type='datetime'">
		<xsl:if test="required = 1"><![CDATA[&&]]> isRequired(f.str_<xsl:value-of select="db-field"/>_date,"<xsl:value-of select="label"/>")
		<![CDATA[&&]]> isRequired(f.str_<xsl:value-of select="db-field"/>_time,"<xsl:value-of select="label"/>")</xsl:if></xsl:when>
		<xsl:when test="@type = 'integer'">
		<![CDATA[&&]]> isValidInteger(f.str_<xsl:value-of select="db-field"/>,"<xsl:value-of select="label"/>",<xsl:value-of select="required = 1"/>)</xsl:when>
		<xsl:when test="@type = 'decimal'">
		<![CDATA[&&]]> isValidDecimal(f.str_<xsl:value-of select="db-field"/>,"<xsl:value-of select="label"/>",<xsl:value-of select="required = 1"/>)</xsl:when>
		<xsl:when test="@type = 'email'">
		<![CDATA[&&]]> isValidEmail(f.str_<xsl:value-of select="db-field"/>,"<xsl:value-of select="label"/>",<xsl:value-of select="required = 1"/>)</xsl:when>
		<xsl:when test="@type = 'password'">
		<![CDATA[&&]]> isValidPassword(f.str_<xsl:value-of select="db-field"/>,f.conf_str_<xsl:value-of select="db-field"/>,"<xsl:value-of select="label"/>",<xsl:value-of select="required = 1"/>)</xsl:when>
		<xsl:when test="@type = 'richtext'">
		<![CDATA[&&]]> isValidRTE(f.str_<xsl:value-of select="db-field"/>,"rte_<xsl:value-of select="db-field"/>","<xsl:value-of select="label"/>",<xsl:value-of select="required = 1"/>)</xsl:when>
		</xsl:choose>
	</xsl:for-each>;
}
		<xsl:apply-templates select="entity"/>
	</xsl:template>
</xsl:stylesheet>