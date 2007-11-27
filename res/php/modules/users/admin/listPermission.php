<?
	include_once('../../includes.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	$obj=new Permission();
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=3;
	
	$criteria=array("u02_id='".$_REQUEST['u02_id']."'"); //parent id
		
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize);
		
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="list.xsl"?>
		  <entity name="'.get_class($obj).'" ext="php">
		  	<error>'.$_REQUEST['error'].'</error>
			<prefix>../users/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
		echo $obj->getXMLList($list);
	echo "</entity>";
?>
