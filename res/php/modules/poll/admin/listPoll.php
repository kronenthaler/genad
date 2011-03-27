<?
	include_once('../../includes.php');
	
	
	$action = 'VIEW';
			
	$section = 'Poll';
	include_once('../../common/checksession.php');
	
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	$obj=new Poll();
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=$obj->properties[PAGER]?30:-1;
	
	$criteria=array(); //filters in the search
		
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize);
		
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="../../admin/getResource.php?file=admin/list.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error><msg>'.str_replace("|", '</msg><msg>', $_REQUEST['error']).'</msg></error>
			<prefix>../poll/admin/</prefix>';
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
?>