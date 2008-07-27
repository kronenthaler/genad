<?
	include_once('../../includes.php');
	
	
	$action = strtoupper($_REQUEST['action']);
			
	$section = 'Votes';
	include_once('../../common/checksession.php');
	
	
	$action =  $_REQUEST['action'];
	$id = $_REQUEST['id'];
	$obj = new Votes();
	
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
	
	header("Location: listVotes.php?ini=".$_REQUEST['ini']."&error=".$error.$obj->getAncestorsIds());
?>