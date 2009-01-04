<?
	include_once('../../includes.php');
	
	
	$action = 'VIEW';
			
	$section = 'Options';
	include_once('../../common/checksession.php');
	
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	$obj=new Options();
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=$obj->properties[PAGER] && $_REQUEST['action']==''?30:-1;
	
	$criteria=array("p01_id='".$_REQUEST['p01_id']."'"); //parent id
		
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize,'_sort');
		
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="../../admin/'.($_REQUEST['action']==''?'list':$_REQUEST['action']).'.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error>'.$_REQUEST['error'].'</error>
			<prefix>../poll/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		if($obj->properties[PAGER])
			echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>