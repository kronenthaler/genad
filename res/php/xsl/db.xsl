<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
							  xmlns:fn="http://www.w3.org/2005/02/xpath-functions">
 	<xsl:output method="text" encoding="utf-8"/>
	
	<xsl:template match="/">
		CREATE DATABASE IF NOT EXISTS `<xsl:value-of select="project/db-server-conf/dev-schema" />` CHARACTER SET utf8;
		USE `<xsl:value-of select="project/db-server-conf/dev-schema" />`;
		<xsl:apply-templates select="/project/entities/entity"/>
		<xsl:apply-templates select="/project/relations/relation"/>
		
		#Set full permissions for the profile Administrator
		INSERT INTO u02u03_has (u02_id, u03_id) (SELECT 1,u03_id FROM u03_permissions) ON DUPLICATE KEY UPDATE u02_id = 1;
	</xsl:template>
	
	<xsl:template match="entity">
		<xsl:if test="(just-pages/@value = 0 and just-schema/@value = 0) or (just-pages/@value = 1)">
		DROP TABLE IF EXISTS `<xsl:value-of select="table/@name"/>`;
		CREATE TABLE `<xsl:value-of select="table/@name"/>` (
		<xsl:for-each select="form/field">
			<xsl:apply-templates select="."/>
			<xsl:if test="../../table/@primary-key = db-field">AUTO_INCREMENT NOT NULL</xsl:if>,</xsl:for-each>
		<xsl:if test="sortable/@value = 1">
		_sort BIGINT(10), </xsl:if>
		<xsl:if test="../table/@name != ./table/@name">
			<xsl:if test="../table/@primary-key != ./table/@primary-key">
			<xsl:variable name="pk"><xsl:value-of select="../table/@primary-key"/></xsl:variable>
			<xsl:apply-templates select="../form/field[db-field = $pk]"/> NOT NULL, 
			KEY `parent` (`<xsl:value-of select="../table/@primary-key"/>`) ,</xsl:if></xsl:if>
			PRIMARY KEY (`<xsl:value-of select="table/@primary-key"/>`)
		);
		</xsl:if>
		
		#Permissions set to: <xsl:value-of select="permissions/@value"/>
		<xsl:if test="permissions/@value != 'none'">
		INSERT INTO u04_sections (u04_name) VALUES ('<xsl:value-of select="@name"/>');
		<xsl:if test="permissions/@value = 'standard' or permissions/@value ='plus'">
		INSERT INTO u03_permissions (u03_name, u03_action, u02_id, u04_id) VALUES 
			<xsl:if test="sortable/@value = 1">('Sort on <xsl:value-of select="@name"/>','SORT',1, last_insert_ID()),</xsl:if>	
			('View on <xsl:value-of select="@name"/>','VIEW',1, last_insert_ID()),
			('Add on <xsl:value-of select="@name"/>','ADD',1, last_insert_ID()),
			('Modify on <xsl:value-of select="@name"/>','MOD',1, last_insert_ID()),
			('Delete on <xsl:value-of select="@name"/>','DEL',1, last_insert_ID());
		</xsl:if>	
		</xsl:if>
		
		#Entity creation
		<xsl:apply-templates select="entity"/>
	</xsl:template>

	<xsl:template match="relation">
		#Relation creation
		DROP TABLE IF EXISTS `<xsl:value-of select="table/@name"/>`;
		CREATE TABLE `<xsl:value-of select="table/@name"/>` (
			`<xsl:value-of select="table/@name"/>_id` BIGINT(10) UNSIGNED AUTO_INCREMENT NOT NULL,
			<xsl:for-each select="form/field">
				<xsl:apply-templates select="."/>,
			</xsl:for-each>
			<xsl:for-each select="entity"><xsl:choose>
			<xsl:when test="@type = 'primary-key'">
			`<xsl:value-of select="@primarykey"/>` BIGINT (30) UNSIGNED </xsl:when>
			<xsl:when test="@type = 'textfield' or @type = 'password' or @type = 'email' or @type = 'select' or @type = 'hidden'">
			`<xsl:value-of select="@primarykey"/>` VARCHAR (50) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'integer' or @type = 'radio' or @type = 'checkbox'">
			`<xsl:value-of select="@primarykey"/>` INTEGER (10) DEFAULT 0 </xsl:when>
			<xsl:when test="@type = 'decimal'">
			`<xsl:value-of select="@primarykey"/>` FLOAT (10,5) DEFAULT '0.0' </xsl:when>
			<xsl:when test="@type = 'date' ">
			`<xsl:value-of select="@primarykey"/>` VARCHAR (8) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'datetime' ">
			`<xsl:value-of select="@primarykey"/>` VARCHAR (14) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'time' ">
			`<xsl:value-of select="@primarykey"/>` VARCHAR (6) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'file' or @type='image' ">
			`<xsl:value-of select="@primarykey"/>` VARCHAR (255) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'textarea' or @type='richtext' ">
			`<xsl:value-of select="@primarykey"/>` TEXT </xsl:when>
			</xsl:choose>,</xsl:for-each>
			<xsl:if test="sortable/@value = 1">_sort BIGINT(10),</xsl:if>
			UNIQUE `fk`(<xsl:for-each select="entity">`<xsl:value-of select="@primarykey"/>`<xsl:if test="position() != last()">,</xsl:if></xsl:for-each>),
			PRIMARY KEY (`<xsl:value-of select="table/@name"/>_id`)
		);

		#Permissions set to: <xsl:value-of select="permissions/@value"/>
		<xsl:if test="permissions/@value != 'none'">
		INSERT INTO u04_sections (u04_name) VALUES ('<xsl:value-of select="@name"/>');
		<xsl:if test="permissions/@value = 'standard' or permissions/@value ='plus'">
		INSERT INTO u03_permissions (u03_name, u03_action, u02_id, u04_id) VALUES
			<xsl:if test="sortable/@value = 1">('Sort on <xsl:value-of select="@name"/>','SORT',1, last_insert_ID()),</xsl:if>
			('View on <xsl:value-of select="@name"/>','VIEW',1, last_insert_ID()),
			('Add on <xsl:value-of select="@name"/>','ADD',1, last_insert_ID()),
			('Modify on <xsl:value-of select="@name"/>','MOD',1, last_insert_ID()),
			('Delete on <xsl:value-of select="@name"/>','DEL',1, last_insert_ID());
		</xsl:if>
		</xsl:if>
	</xsl:template>
	
	<xsl:template match="field">
		<xsl:choose>
			<xsl:when test="@type = 'primary-key'">
			`<xsl:value-of select="db-field"/>` BIGINT (30) UNSIGNED </xsl:when>
			<xsl:when test="@type = 'textfield' or @type = 'password' or @type = 'email' or @type = 'select' or @type = 'hidden'">
			`<xsl:value-of select="db-field"/>` VARCHAR (50) DEFAULT '' </xsl:when>
			<xsl:when test="@type = 'integer' or @type = 'radio' or @type = 'checkbox'">
			`<xsl:value-of select="db-field"/>` INTEGER (10) DEFAULT 0 </xsl:when>
			<xsl:when test="@type = 'decimal'">
			`<xsl:value-of select="db-field"/>` FLOAT (10,5) DEFAULT '0.0' </xsl:when>
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