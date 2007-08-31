<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="/project/db-server-conf"/>
	</xsl:template>
	<xsl:template match="db-server-conf">
<![CDATA[
<?
/**
 *	This class make the connection with the database, and guarantees that no more that one connection
 *	has made if one connection already existing in the same object. The connection object MUST be instatiated
 *	in one single file, like a include file for can guarantee this unique condition.
 */

class Connection{
	var $conn;
	var $host;
	var $password;
	var $login;
	var $db;
		
	function Connection(){
		session_start();
		if($this->conn==NULL){
			if(stristr($_SERVER['HTTP_HOST'],'localhost')){
				$this->host=']]><xsl:value-of select="dev-host"/><![CDATA[';
				$this->login=']]><xsl:value-of select="dev-user"/><![CDATA[';
				$this->password=']]><xsl:value-of select="dev-password"/><![CDATA[';
				$this->db=']]><xsl:value-of select="dev-schema"/><![CDATA[';
			}else{
				$this->host=']]><xsl:value-of select="dev-host"/><![CDATA[';
				$this->login=']]><xsl:value-of select="dev-user"/><![CDATA[';
				$this->password=']]><xsl:value-of select="dev-password"/><![CDATA[';
				$this->db=']]><xsl:value-of select="dev-schema"/><![CDATA[';
			}
			$this->conn=mysql_connect($this->host,$this->login,$this->password);
			mysql_select_db($this->db,$this->conn);
		}
	}
	
	function closeConnection(){
		mysql_close($this->conn);
	}
}
?>
]]>
	</xsl:template>
</xsl:stylesheet>