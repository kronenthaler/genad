<?php
include_once("../includes.php");

$file = $_REQUEST['file'];
$string = file_get_contents(HTTP_ROOT."/".$file);
$type = 'plain';

if(strpos($file,'.js') !==false) $type='javascript';
if(strpos($file,'.xsl')!==false) $type='xml';
if(strpos($file,'.xml')!==false) $type='xml';

header("Content-type: text/".$type);
header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past

$localization = get_defined_constants(true);
$localization = $localization['user'];
//ordenar estos strings por longitud.

$keys = array_keys($localization);
uasort($keys, create_function('$a,$b','return strlen($b) - strlen($a);'));

//foreach($localization as $define => $value){
foreach($keys as $pos => $define){
	if(strpos($define,"MSG_")!==FALSE)
		$string = str_replace($define, $localization[$define], $string);
}

echo $string;
?>
