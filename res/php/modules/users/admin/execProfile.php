<?
	include_once('../../includes.php');
	
	$action = strtoupper($_REQUEST['action']);
	$section = 'Profiles';
	include_once('../../common/checksession.php');
		
	$action =  $_REQUEST['action'];
	$id = $_REQUEST['id'];
	$obj = new Profile();
	
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
	
	header("Location: listProfile.php?ini=".$_REQUEST['ini']."&error=".$error.$obj->getAncestorsIds());
?>