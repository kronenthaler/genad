<?
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
	if(file_exists($path))
		include_once($path);
}
?>