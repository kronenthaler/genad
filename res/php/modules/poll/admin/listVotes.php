<?
	include_once('../../includes.php');
	
	$action = 'VIEW';
			
	$section = 'Votes';
	include_once('../../common/checksession.php');
		
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	$obj=new Votes();
	$obj->tablename .= ',u01_users';
	
	$ini=$_REQUEST['ini']==''?0:$_REQUEST['ini'];
	$pageSize=$obj->properties[PAGER]?30:-1;
	
	$criteria=array("p02_id='".$_REQUEST['p02_id']."'", "u01_users.u01_id = p03_votes.u01_id"); //parent id GROUP BY u01_users.u01_login
	if($_REQUEST['asc']=='0') $_REQUEST['orderby']='';
	if($_REQUEST['orderby']!='')
		$sort = $_REQUEST['asc']==1?' ASC':($_REQUEST['asc']==2?' DESC':'');	
	$list=$obj->search($_REQUEST['search'],$criteria,$ini,$pageSize,$obj->properties[SORTABLE]?'_sort':$_REQUEST['orderby'].$sort);
		
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
		
		if($_REQUEST['asc']=='') $_REQUEST['asc']=0;
		echo '<order>';
		echo '<field><![CDATA['.(($_REQUEST['asc'])!=0?$_REQUEST['orderby']:'').']]></field>';
		echo '<asc><![CDATA['.(($_REQUEST['asc']+1)%3).']]></asc>';
		echo '</order>';
		
		echo $obj->getXMLTitle();
		echo $obj->getXMLBack();
		echo $obj->getXMLAncestors();
		
		if($obj->properties[PAGER])
			echo $obj->getXMLListPager($obj->totalRows($criteria),$pageSize,$ini);
			
		echo $obj->getXMLList($list);
	echo "</entity>";
?>