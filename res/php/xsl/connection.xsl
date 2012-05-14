<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/">
		<xsl:apply-templates select="/project/db-server-conf"/>
	</xsl:template>
	<xsl:template match="db-server-conf"><![CDATA[<?
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
			$this->connect($this->host,$this->login,$this->password, $this->db);
		}
	}
	
	function closeConnection(){
		mysql_close($this->conn);
	}
	
	function query($query){
		return mysql_query($query,$this->conn);
	}
	
	function getNumRows($rs){
		return mysql_num_rows($rs);
	}
	
	function getNumFields($rs){
		return mysql_num_fields($rs);
	}
	
	function getFieldName($rs,$index){
		return mysql_field_name($rs, $index);
	}
	
	function getResult($rs,$row,$field=NULL){
		return mysql_result($rs, $row, $field);
	}
	
	function getError(){
		return mysql_error($this->conn);
	}
	
	function getLastID(){
		return mysql_insert_id($this->conn);
	}
	
	function connect($host, $login, $pass, $schema){
		//echo "<!-- $login:$pass@$host/$schema -->";
		if($this->conn != NULL) mysql_close($this->conn);
		
		$this->conn = mysql_connect($host, $login, $pass);
		mysql_select_db($schema,$this->conn);
	}
	
	function restoreConnection(){
		$this->connect($this->host, $this->login, $this->password, $this->db);
	}
}
//connect with the database
$connection=new Connection();
?>]]></xsl:template>
</xsl:stylesheet>