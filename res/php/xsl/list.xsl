<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/">
<![CDATA[<?]]>
	include_once('../includes.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	$obj=new <xsl:value-of select="//@name"/>();
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=3;
	<xsl:choose>
		<xsl:when test="entity/parent/@id != ''">
	$criteria=array("<xsl:value-of select="entity/parent/@id"/>='".$_REQUEST['<xsl:value-of select="entity/parent/@id"/>']."'"); //parent id
		</xsl:when>
		<xsl:otherwise>
	$criteria=array(); //filters in the search
		</xsl:otherwise>
	</xsl:choose>
	<xsl:choose>
		<xsl:when test="/entity/search/@value = '1'">
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize);
		</xsl:when>
		<xsl:otherwise>
	$list=$obj->getList($criteria,$ini,$pageSize);//change for search($_REQUEST['search'],$criteria,$ini,$pageSize); if is necessary
		</xsl:otherwise>
	</xsl:choose><![CDATA[
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="list.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error>'.$_REQUEST['error'].'</error>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>]]>
	</xsl:template>
</xsl:stylesheet>