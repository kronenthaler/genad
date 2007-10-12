<?
include_once('../includes.php');

eval("\$upload = new ".$_REQUEST['type_'.$_REQUEST['fieldName']]."(\$_REQUEST['fieldName'],'');");
$upload->loadVars($_REQUEST);
$error=$upload->uploadFile($_REQUEST);

if($error->code!=0)
	header('Location: component.php?finish=1&name='.$_REQUEST['fieldName'].'&error='.$error->description.$upload->getParams());
else{
	header('Location: component.php?finish=1&name='.$_REQUEST['fieldName'].'&path='.$error->description.$upload->getParams());
}?>