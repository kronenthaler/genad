<?
	include_once('../../includes.php');
	
	$action = strtoupper($_REQUEST['action']);
	$section = 'Profiles';
	include_once('../../common/checksession.php');
	
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past
  
	$obj=new Profile();
	
	if($_REQUEST['action']=='mod' && $_REQUEST['id']!='' && !$obj->load($_REQUEST['id']))
		die('No id found');	
	
	echo '<?xml version="1.0" encoding="utf-8"?>
		  <?xml-stylesheet type="text/xsl" href="../../admin/getResource.php?file=admin/mod.xsl"?>
		  <entity name="'.get_class($obj).'" 
		  		  ext="php" 
		  		  action="'.$_REQUEST['action'].'" 
		  		  id="'.$_REQUEST['id'].'">
			<prefix>../users/admin/</prefix>';
		echo $obj->getXMLTitle();
		echo $obj->getXMLAncestors(); 
		//begin field options

		$obj->fields['u03_id'][TYPE]="fieldset";
		$options["u03_id"]=array();
		
		$permissions = new Permission();
		$permissions->tablename .=',u04_sections';
		$permissions = $permissions->getList(array('u03_permissions.u04_id=u04_sections.u04_id'), 0, -1, 'u04_sections.u04_name,u03_name');
		
		$profilePermissions = new ProfilePermission();
		$profilePermissions = $profilePermissions->getList(array("u02_id='".$_REQUEST['id']."'"));
		
		$aux=array();
		
		for($i=0;$i < count($profilePermissions);$i++)
			$aux[$profilePermissions[$i]->u03_id] = 1;

		if(count($permissions)>0){
			$set = array();
			$prev = $permissions[0]->u04_id;
			$setName = $permissions[0]->u04_name;
			for($i=0;$i<count($permissions);$i++){
				if($prev != $permissions[$i]->u04_id){
					array_push($options['u03_id'], array_merge(array($setName), $set));
					$set = array();
					$setName = $permissions[$i]->u04_name;
				}
				
				array_push($set,
							array('name'=> $permissions[$i]->u03_name,
							  'value' =>  $permissions[$i]->u03_id,
							  'selected'=> $aux[$permissions[$i]->u03_id] == 1?'true':'',
							  'onclick'=>'')
							);
				

				$prev = $permissions[$i]->u04_id;
			}

			array_push($options['u03_id'], array_merge(array($setName), $set));
		}
		/*for($i=0;$i < count($permissions);$i++){
			array_push($options["u03_id"], 
						array('name'=> $permissions[$i]->u03_name,
							  'value' =>  $permissions[$i]->u03_id, 
							  'selected'=> $aux[$permissions[$i]->u03_id] == 1?'true':'',
							  'onclick'=>'')
			);
		}*/
		
		//end field options
		echo $obj->getXMLJSValidator($options);
		echo $obj->getXMLForm($options);
	echo "</entity>";
?>
