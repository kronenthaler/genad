<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 	<xsl:output method="text" encoding="utf-8"/>
	<xsl:template match="/">
		<xsl:apply-templates select="entity"/> 
	</xsl:template>
	
	<!-- all the code for importing files and styles, js, etc
	<xsl:template match="header">
		<title><xsl:value-of select="@title"/></title>
		<xsl:text disable-output-escaping="yes"><![CDATA[
			<? include_once('wk_package.php');?>
		]]></xsl:text>
	</xsl:template>
	
	
	<xsl:template match="body">
		<xsl:text disable-output-escaping="yes"><![CDATA[
			<body>
				<table border="1">
					<tr><td>algo</td></tr>
					<tr><td>ad&iacute;os</td></tr>
				</table>
			</body>
		]]></xsl:text>
	</xsl:template>
	<xsl:template match="footer">
		<![CDATA[<? //mysql_close($conn); ?>]]>
	</xsl:template>-->
	<xsl:template match="entity">
		
	</entity>
</xsl:stylesheet>