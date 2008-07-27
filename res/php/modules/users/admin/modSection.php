<?
	include_once('../../includes.php');
	
	$action = strtoupper($_REQUEST['action']);
	$section = 'Sections';
	include_once('../../common/checksession.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new Section();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('No id found');	
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="../../admin/mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">
			  <prefix>../users/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors(); 
		//begin field options
		
		
		//end field options
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>
