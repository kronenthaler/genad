<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="text" encoding="utf-8"/>
	
	<xsl:template match="/">
		CREATE DATABASE IF NOT EXISTS `<xsl:value-of select="project/db-server-conf/dev-schema" />` CHARACTER SET utf8;
		USE `<xsl:value-of select="project/db-server-conf/dev-schema" />`;
		<xsl:apply-templates select="/project/entities/entity"/> 
	</xsl:template>
	
	<xsl:template match="entity">
		<xsl:if test="(just-pages/@value = 0 and just-schema/@value = 0) or (just-pages/@value = 1)">
		DROP TABLE IF EXISTS `<xsl:value-of select="table/@name"/>`;
		CREATE TABLE `<xsl:value-of select="table/@name"/>` (
		<xsl:if test="form/field/db-field != table/@primary-key">
			`<xsl:value-of select="table/@primary-key"/>` BIGINT (30) UNSIGNED NOT NULL AUTO_INCREMENT ,</xsl:if>
		<xsl:for-each select="form/field">
			<xsl:apply-templates select="."/>
			<xsl:if test="../../table/@primary-key = db-field"> NOT NULL</xsl:if>
			<xsl:if test="position() != last()"> ,</xsl:if>
		</xsl:for-each> ,
		<xsl:if test="../table/@name != ./table/@name">
			<xsl:choose>
			<xsl:when test="../table/@primary-key != ../form/field/db-field">
			`<xsl:value-of select="../table/@primary-key"/>` BIGINT (30) UNSIGNED NOT NULL ,</xsl:when>
			<xsl:otherwise>
			<xsl:variable name="pk"><xsl:value-of select="../table/@primary-key"/></xsl:variable>
			<xsl:apply-templates select="../form/field[db-field = $pk]"/> , 
			</xsl:otherwise>	
			</xsl:choose>
			KEY `parent` (`<xsl:value-of select="../table/@primary-key"/>`) ,</xsl:if>
			PRIMARY KEY (`<xsl:value-of select="table/@primary-key"/>`)
		);
		</xsl:if>
		<xsl:apply-templates select="entity"/>
	</xsl:template>
	
	<xsl:template match="field">
		<xsl:choose>
			<xsl:when test="@type = 'textfield' or @type = 'password' or @type = 'email' or @type = 'select' or @type = 'hidden'">
			`<xsl:value-of select="db-field"/>` VARCHAR (50) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'integer' or @type = 'radio' or @type = 'checkbox'">
			`<xsl:value-of select="db-field"/>` INTEGER (10) DEFAULT 0 </xsl:when>
			<xsl:when test="@type = 'decimal'">
			`<xsl:value-of select="db-field"/>` FLOAT (10.5) DEFAULT '0.0' </xsl:when>
			<xsl:when test="@type = 'date' ">
			`<xsl:value-of select="db-field"/>` VARCHAR (8) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'datetime' ">
			`<xsl:value-of select="db-field"/>` VARCHAR (14) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'time' ">
			`<xsl:value-of select="db-field"/>` VARCHAR (6) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'file' or @type='image' ">
			`<xsl:value-of select="db-field"/>` VARCHAR (255) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'textarea' or @type='richtext' ">
			`<xsl:value-of select="db-field"/>` TEXT </xsl:when>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>