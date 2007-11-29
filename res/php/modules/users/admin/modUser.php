<?
	include_once('../../includes.php');
	
	$action = strtoupper($_REQUEST['action']);
	$section = 'Users';
	include_once('../../common/checksession.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new User();
	
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
		
		$options["u01_password"]=array('confirm'=>'Confirm');
		$options["u02_id"]=array();
		
		$profiles = new Profile();
		$profiles = $profiles->getList(NULL, 0, -1, 'u02_name');
		
		$userProfiles = new UserProfile();
		$userProfiles = $userProfiles->getList(array("u01_id='".$_REQUEST['id']."'"));
		
		$aux=array();		
		
		for($i=0; $i<count($userProfiles);$i++)
			$aux[$userProfiles[$i]->u02_id] = 1;
		
		for($i=0;$i< count($profiles);$i++){
			array_push($options["u02_id"], 
						array('name'=> $profiles[$i]->u02_name,
							  'value' =>  $profiles[$i]->u02_id, 
							  'selected'=> $aux[$profiles[$i]->u02_id] == 1?'true':'',
							  'onclick'=>'')
			);
		}
		
		//end field options
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>
