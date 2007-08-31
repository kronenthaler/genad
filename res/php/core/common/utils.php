<?
  
function format($date, $type,$format){
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

?>