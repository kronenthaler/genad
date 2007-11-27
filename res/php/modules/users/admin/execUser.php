<?
	include_once('../../includes.php');
		
	$action =  $_REQUEST['action'];
	$id = $_REQUEST['id'];
	$obj = new User();
	
	if($action=='add'){
		if(!$obj->insert())
			$error=$obj->getError();
	}else if($action =='mod'){
		if(!$obj->update($id))
			$error=$obj->getError();
	}else if($action == 'del'){
		if(!$obj->delete($id))
			$error=$obj->getError();
	}else{
		$error="No action especified";
	}
	
	header("Location: listUser.php?ini=".$_REQUEST['ini']."&error=".$error.$obj->getAncestorsIds());
?>