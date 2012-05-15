<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
 	<xsl:output method="text" encoding="utf-8" indent="no"/>
	
	<xsl:template match="/"><xsl:apply-templates/></xsl:template>

	<xsl:template match="entity"><![CDATA[<?]]>
	include_once('../includes.php');
	
	<xsl:if test="permissions/@value != ''">
		<xsl:choose>
			<xsl:when test="permissions/@value = 'standard' or permissions/@value = 'plus'">
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
		<xsl:when test="parent/@id != ''">
	$criteria=array("<xsl:value-of select="parent/@id"/>='".$_REQUEST['<xsl:value-of select="parent/@id"/>']."'"); //parent id
		</xsl:when>
		<xsl:otherwise>
	$criteria=array(); //filters in the search
		</xsl:otherwise>
	</xsl:choose>
	
	if($_REQUEST['asc']=='0') $_REQUEST['orderby']='';
	if($_REQUEST['orderby']!='')
		$sort = $_REQUEST['asc']==1?' ASC':($_REQUEST['asc']==2?' DESC':'');
	
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize,$obj->properties[SORTABLE]?'_sort':$_REQUEST['orderby'].$sort);
	

	<![CDATA[
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="getResource.php?file=admin/'.($_REQUEST['action']==''?'list':$_REQUEST['action']).'.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error><msg>'.str_replace("|", '</msg><msg>', $_REQUEST['error']).'</msg></error>
			<prefix></prefix>';
		if($obj->properties[SEARCH]){
			echo '<search>';
			echo '<![CDATA';
			echo '['.$_REQUEST['search'].']';
			echo ']></search>';
		}
		
		if($_REQUEST['asc']=='') $_REQUEST['asc']=0;
		echo '<order>';
		echo '<field><![CDATA'.'['.(($_REQUEST['asc'])!=0?$_REQUEST['orderby']:'').']'.']></field>';
		echo '<asc><![CDATA'.'['.(($_REQUEST['asc']+1)%3).']'.']></asc>';
		echo '</order>';
		
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		if($obj->properties[PAGER])
			echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>]]></xsl:template>

<xsl:template match="relation"><![CDATA[<?]]>
	include_once('../includes.php');

	<xsl:if test="permissions/@value != ''">
		<xsl:choose>
			<xsl:when test="permissions/@value = 'standard' or permissions/@value = 'plus'">
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
	eval("\$currentClass = new ".$_REQUEST['currentClass']."();");

	if(!$currentClass->load($_REQUEST['rel_id']))
		die('No Id Found');

	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=$obj->properties[PAGER] <![CDATA[&&]]> $_REQUEST['action']==''?30:-1;
	
	$criteria=array('`'.$currentClass->tablename.'`.`'.$currentClass->primarykey.'`='.$currentClass->id); //filters in the search
	
	if($_REQUEST['asc']=='0') $_REQUEST['orderby']='';
	if($_REQUEST['orderby']!='')
		$sort = $_REQUEST['asc']==1?' ASC':($_REQUEST['asc']==2?' DESC':'');
	
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize,$obj->properties[SORTABLE]?'`'.$obj->tablename.'`.`_sort`':$_REQUEST['orderby'].$sort);

	<![CDATA[
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="getResource.php?file=admin/'.($_REQUEST['action']==''?'list':$_REQUEST['action']).'Relation.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php" id="'.$_REQUEST['rel_id'].'">
		  	<error><msg>'.str_replace("|", '</msg><msg>', $_REQUEST['error']).'</msg></error>
			<prefix></prefix>';
		if($obj->properties[SEARCH]){
			echo '<search>';
			echo '<![CDATA';
			echo '['.$_REQUEST['search'].']';
			echo ']></search>';
		}
		
		if($_REQUEST['asc']=='') $_REQUEST['asc']=0;
		echo '<order>';
		echo '<field><![CDATA'.'['.(($_REQUEST['asc'])!=0?$_REQUEST['orderby']:'').']'.']></field>';
		echo '<asc><![CDATA'.'['.(($_REQUEST['asc']+1)%3).']'.']></asc>';
		echo '</order>';
		
		echo $currentClass->getXMLTitle();
		echo $currentClass->getXMLBack();
		echo $obj->getXMLAncestors($currentClass);
		if($obj->properties[PAGER])
			echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>]]></xsl:template>
</xsl:stylesheet>