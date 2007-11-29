<?
$permissions = $_SESSION['permissions'];
$user = new User();

if($action == ''){
	writeError('Undefined action.');
	exit();
}

if($section == ''){
	writeError('Undefined section.');
	exit();
}

if($permissions == NULL){
	if($_SESSION["user_id"] == NULL){
		writeError('Session expired, you must logging in.');
		exit();
	}
	
	if(!$user->load($_SESSION["user_id"])){
		writeError('Unknown/invalid user.');
		exit();
	}
}

if(!$user->youCanDo($action, $section, $permissions)){
	writeError('This section requires especial permissions and you haven\'t.');
	exit();
}
?>