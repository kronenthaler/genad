<?
$permissions = $_SESSION['permissions'];
$user = new User();

if($action == ''){
	writeError(MSG_UNDEFINED_ACTION);
	exit();
}

if($section == ''){
	writeError(MSG_UNDEFINED_SECTION);
	exit();
}

if($permissions == NULL){
	if($_SESSION["user_id"] == NULL){
		writeError(MSG_SESSION_EXPIRED,true); //have to reload the menu.php to close the possibility of errors.
		session_unset();
		session_destroy();
		exit();
	}
	
	if(!$user->load($_SESSION["user_id"])){
		writeError(MSG_INVALID_USER);
		exit();
	}
}

if(!$user->youCanDo($action, $section, $permissions)){
	writeError(MSG_PERMISSIONS_REQUIRED);
	exit();
}
?>