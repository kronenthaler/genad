<?
include_once('../includes.php');
$user = new User();
if($user->authenticate($_POST['login'],$_POST['password']) && $user->youCanDo('LOGIN','Admin')){
	$_SESSION['user_id'] = $user->u01_id;
	echo '0';
}else{
	echo MSG_INVALID_LOGIN;
}
?>
