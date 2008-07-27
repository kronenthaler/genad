<?
include("../includes.php");

header("Content-Type: text/xml");
header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 	// Date in the past

echo '<?xml version="1.0" encoding="utf-8"?>';
echo '<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">';
echo '<xsl:template match="prefix">';

foreach($_SESSION['modules']->enabledModules as $key => $value)
	if($value)
		echo '<script src="../'.$key.'"></script>';

echo '</xsl:template>
	</xsl:stylesheet>';
?>