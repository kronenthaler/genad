<?
	include_once('../../includes.php');
	
	$action = strtoupper($_REQUEST['action']);
	$section = 'Permissions';
	include_once('../../common/checksession.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new Permission();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('No id found');	
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">
			  <prefix>../users/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors(); 
		//begin field options
		
		$options["u04_id"]=array('onclick'=>'');
		
		$sections = new Section();
		$sections =$sections->getList(NULL, 0, -1, 'u04_name');
		
		for($i=0;$i<count($sections);$i++){
			array_push($options["u04_id"], 
				array('name'=> $sections[$i]->u04_name,'value' => $sections[$i]->u04_id,'selected'=>$obj->u04_id==$sections[$i]->u04_id?'true':'')
			);
		}
		
		//end field options
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>
