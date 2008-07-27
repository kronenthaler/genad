<?
define('KB',1024);
define('MB',KB*KB);
define('GB',KB*MB);

$months = array('01'=>'Enero',
				'02'=>'Febrero',
				'03'=>'Marzo',
				'04'=>'Abril',
				'05'=>'Mayo',
				'06'=>'Junio',
				'07'=>'Julio',
				'08'=>'Agosto',
				'09'=>'Septiembre',
				'10'=>'Octubre',
				'11'=>'Noviembre',
				'12'=>'Diciembre');
$days = array('','Lunes','Martes','Miercoles','Jueves','Viernes','Sabado','Domingo');  

 /**
 *	Transform a $date of type $type using the string $format 
 *	Basically replace the placeholders for the equivalents in $date accoding to $type 
 *	remainning the rest of the format
 *	Placeholders: 
 *	Y : 4digits year
 *	M : 2digits month in number begins in 01 ends 12
 *	D : 2digits day of the month
 *	H : 2digits hour
 *	m : 2digits minutes
 *	s : 2digits seconds
 *	Any other character is copied exactly.
 *	By example: to format 20040229 => 29/02/2004 use $format='D/M/Y' and $type=DATE
 */
function format($date, $type, $format){
	if($type&DATE){
		$y=substr($date,0,4);
		$M=substr($date,4,2);
		$d=substr($date,6,2);
		if($type & TIME){
			$h=substr($date,8,2);
			$m=substr($date,10,2);
			$s=substr($date,12,2);
		}
	}else{
		$h=substr($date,0,2);
		$m=substr($date,2,2);
		$s=substr($date,4,2);
	}
	
	return $date==''?'':str_replace('s',$s,
						str_replace('m',$m,
						str_replace('H',$h,
						str_replace('D',$d,
						str_replace('M',$M,
						str_replace('Y',$y,$format))))));
}

//rsa
//startWith
//endsWith

/** include once the file in $path iif exists */
function secureInclude($path){
	if(file_exists(ROOT.'/'.$path))
		include_once(ROOT.'/'.$path);
}

//logging on file
function logOn($str){
	$f = fopen(ROOT.'/files/log.txt', 'a');
	fprintf($f,"%s\n",$str);
	fclose($f);
}

function writeError($msg){
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	echo '<?xml version="1.0" encoding="utf-8"?>';
	echo '<?xml-stylesheet type="text/xsl" href="'.HTTP_ROOT.'/admin/list.xsl"?>';
	echo '<error><basepath><![CDATA['.HTTP_ROOT.'/admin/]]></basepath>';
	echo '<msg href="'.str_replace("&","&amp;",$_SERVER['HTTP_REFERER']).'">'.$msg.'</msg></error>';
}

if (!function_exists("htmlspecialchars_decode")) {
    function htmlspecialchars_decode($string, $quote_style = ENT_COMPAT) {
        return strtr($string, array_flip(get_html_translation_table(HTML_SPECIALCHARS, $quote_style)));
    }
}
?>
