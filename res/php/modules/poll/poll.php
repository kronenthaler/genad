<?
include_once('pollConf.php');
include_once('obj/Poll.php');
include_once('obj/Options.php');
include_once('obj/Votes.php');

define('POLL_OPTION_ERROR',-1);
$_SESSION['modules']->enableModule('/poll/admin/js/validators.js');
?>