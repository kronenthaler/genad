<?
define('KB',1024);
define('MB',KB*KB);
define('GB',KB*MB);

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
/**
 * (b^p)%m
 */
function modpow($b,$p,$m){
	$resul=0;
	if($p == 0){
		$resul = 1;
	}else if($p == 1){
		$resul = bcmod($b,$m);
	}else if($p == 2){
		$aux = bcmod($b,$m);
		$resul = bcmod(bcmul($aux,$aux),$m);
	}else{
		$p1 = bcdiv($p,2); // p/2
		$aux = modpow($b,$p1,$m); //
		$resul = (bcmod($p,2) != 0)?
				bcmod(bcmul(bcmod(bcmul($aux,$aux),$m), bcmod($b,$m)),$m) :
				bcmod(bcmul($aux,$aux),$m);
	}
	return $resul;
}

/**
 *	$publicKey: 	llave publica para el encriptamiento (debe cumplir con ciertos criterios)
 *	$n:				valor del algoritmo. Numero primo alto
 *	$msg:			mensaje a codificar. Debe ser un numero por los momentos.
 */
function encodeRSA($publicKey, $n, $msg){
	return modpow($msg, $publicKey, $n);
}

/**
 *	$privateKey:	llave privada para el desencriptado.
 *	$n:				valor del algoritmo.
 *	$msg:			mensaje a descodificar
 */
function decodeRSA($privateKey, $n, $msg){
	return modpow($msg, $privateKey, $n);
}

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

function writeError($msg,$refresh=FALSE){
	header("Content-Type: text/xml");
	header("Cache-Control: no-cache, must-revalidate");	// HTTP/1.1
	header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");	// Date in the past
	
	echo '<?xml version="1.0" encoding="utf-8"?>';
	echo '<?xml-stylesheet type="text/xsl" href="'.HTTP_ROOT.'/admin/getResource.php?file=admin/list.xsl"?>';
	echo '<error><basepath><![CDATA['.HTTP_ROOT.'/admin/]]></basepath>';
	echo '<msg href="'.str_replace("&","&amp;",$_SERVER['HTTP_REFERER']).'">'.$msg.'</msg>';
	echo '<refresh>'.($refresh?'true':'false').'</refresh>';
	echo '</error>';
}

if (!function_exists("htmlspecialchars_decode")) {
    function htmlspecialchars_decode($string, $quote_style = ENT_COMPAT) {
        return strtr($string, array_flip(get_html_translation_table(HTML_SPECIALCHARS, $quote_style)));
    }
}

/**
 *	Function to replace params placeholders in a message for their respectives values.
 *	@param $msg	Message with the placeholders to be changed.
 *	@param $params	Array with the params to be replaced. If params is null no changes are made.
 *	@return	The message with the placeholders replaced for their respectives positional values.
 */
function getMessage($msg, $params=NULL){
	for($i=0;$i<count($params);$i++)
		$msg = str_replace('%'.($i+1), $params[$i], $msg);
	return $msg;
}
?>
