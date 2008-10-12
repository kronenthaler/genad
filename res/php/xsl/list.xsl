<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><![CDATA[<?]]>
	include_once('../includes.php');
	
	<xsl:if test="/entity/permissions/@value != ''">
		<xsl:choose>
			<xsl:when test="/entity/permissions/@value = 'standard' or /entity/permissions/@value = 'plus'">
	$action = 'VIEW';
			</xsl:when>
			<xsl:otherwise>
	$action = 'your-action-here';
			</xsl:otherwise>
		</xsl:choose>
	$section = '<xsl:value-of select="//@name"/>';
	include_once('../common/checksession.php');
	</xsl:if>
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past

	$obj=new <xsl:value-of select="//@name"/>();
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=$obj->properties[PAGER] <![CDATA[&&]]> $_REQUEST['action']==''?30:-1;
	<xsl:choose>
		<xsl:when test="entity/parent/@id != ''">
	$criteria=array("<xsl:value-of select="entity/parent/@id"/>='".$_REQUEST['<xsl:value-of select="entity/parent/@id"/>']."'"); //parent id
		</xsl:when>
		<xsl:otherwise>
	$criteria=array(); //filters in the search
		</xsl:otherwise>
	</xsl:choose>
	
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize<xsl:if test="/entity/sortable/@value=1">,'_sort'</xsl:if>);
	
	<![CDATA[
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="'.($_REQUEST['action']==''?'list':$_REQUEST['action']).'.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error>'.$_REQUEST['error'].'</error>
			<prefix></prefix>';
		if($obj->properties[SEARCH]){
			echo '<search>';
			echo '<![CDATA';
			echo '['.$_REQUEST['search'].']';
			echo ']></search>';
		}
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		if($obj->properties[PAGER])
			echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>]]></xsl:template>
</xsl:stylesheet>