<?
/**
 *	Upload files and images, the images can be dealed in a better way, to acomplish 
 *	restrictions in width, height or any other.
 */
 
define('VALID_EXTS','exts');
define('MAX_SIZE','maxSize');
define('PREFIX','prefix');
define('DEST_PATH','destPath');
define('PREV','prev');

class Upload{
	var $fieldName;	//name of the field
	var $fieldValue;	//value of the field a.k.a. the path.
	var $opts;	
	
	function Upload($fieldName, $fieldValue){
		$this->fieldName=$fieldName;
		$this->fieldValue=$fieldValue;
		
		$this->opts=array();
		$this->opts['type']=get_class($this);
		$this->opts[VALID_EXTS]=DEF_VALID_EXTS;
		$this->opts[MAX_SIZE]=DEF_MAX_SIZE;
		$this->opts[PREFIX]=DEF_PREFIX;
		$this->opts[DEST_PATH]=DEF_DEST_PATH;
		$this->opts[PREV]=$this->fieldValue;
	}
	
	function setProperty($key, $value){
		$this->opts[$key]=$value;
	}
	
	function loadVars($ARRAY){
		$keys=array_keys($ARRAY);
		for($i=0,$n=count($ARRAY);$i<$n;$i++){
			$pos=strlen($keys[$i])-strlen($this->fieldName);
			if(substr($keys[$i],$pos)==$this->fieldName)
				$this->opts[substr($keys[$i],0,$pos-1)] = $ARRAY[$keys[$i]];
		}
	}
	
	/**
	 *	Upload the file pointed by $field	
	 *	@return the Error code with the description if exists, otherwise 0 and the final path.
	 */
	function uploadFile($ARRAY){
		$file="file_".$this->fieldName;
		$prev="prev_".$this->fieldName;
		$hdd="str_".$this->fieldName;
		
		$now=explode(" ",microtime());
		$name=$this->opts[PREFIX].date("YmdHis").substr($now[0],1);
		$ext=substr($_FILES[$file]['name'],strrpos($_FILES[$file]['name'],'.'));
		
		$error=$this->isValid($file);
		if($error != NULL) return $error;
		
		$fileName="";
		if($ARRAY[$prev]=="" && $ARRAY[$hdd]!=""){
			move_uploaded_file($_FILES[$file]['tmp_name'],ROOT.$this->opts[DEST_PATH]."/".$name.$ext);
			$fileName=$this->opts[DEST_PATH]."/".$name.$ext;
		}else if($ARRAY[$prev]!="" && $ARRAY[$hdd]==""){
			$this->deleteFile(ROOT.$ARRAY[$prev]);
		}else if($ARRAY[$prev]!="" && $ARRAY[$hdd]!=""){
			move_uploaded_file($_FILES[$file]['tmp_name'],ROOT.$ARRAY[$prev]);
			$fileName=$ARRAY[$prev];
		}else{
			return new Error(666,'Unexpected error');
		}
		
		if(!file_exists(ROOT.$fileName))
			return new Error(-9, "File not moved");
			
		//chmod(ROOT.$fileName,0755);	//can download the file from FTP client.
			
		return new Error(0,$fileName); 	//everything ok
	}
	
	function isValid($file){
		if($_FILES[$file]['error'] == UPLOAD_ERR_NO_FILE) return ""; // no se subio ningun archivo.
		
		if($_FILES[$file]['size'] > $this->opts[MAX_SIZE]){
			if(file_exists($_FILES[$file]['tmp_name']))
				unlink($_FILES[$file]['tmp_name']);
			return new Error(-2,"The file '".$_FILES[$file]['name']."' exceeds ".($this->opts[MAX_SIZE]/1024)." KB.");
		}
		
		if($_FILES[$file]['error'] == UPLOAD_ERR_INI_SIZE){
			if(file_exists($_FILES[$file]['tmp_name']))
				unlink($_FILES[$file]['tmp_name']);
			return new Error(-2,"The file '".$_FILES[$file]['name']."' exceeds ".ini_get('upload_max_filesize')."B.");
		}
		
		//validate the mimetypes
		$exts=$this->opts[VALID_EXTS];
		$type=$_FILES[$file]['type'];
		$flag=true;
		
		if($type!="" && stristr($exts,"*")==""){ //no wildcard
			$exts = implode("','",split(',',$exts)); 
			$rs=mysql_query("SELECT * FROM mimetypes WHERE ext in ('".$exts."') AND mime='".$type."'");
			$flag=($rs && mysql_num_rows($rs)>0);
		}
		
		if(!$flag) 
			return new Error(-3,"The file type of '".$_FILES[$file]['name']."' isn't valid (".
								substr($_FILES[$file]['name'],strrpos($_FILES[$file]['name'],'.'))."). Expected: ".$this->opts[VALID_EXTS]);
		
		return NULL;
	}
	
	/**
	 *	Array to the construction of the options for the mod's XML.
	 */
	function getOptions(){
		$ret=array();
		foreach($this->opts as $key => $val)
			array_push($ret,array('name'=>$key,'value'=>$val));
		return $ret;
	}
	
	function getParams(){
		$ret="";
		foreach($this->opts as $key => $val)
			$ret.='&'.$key.'='.$val;
		return $ret;
	}
	
	function deleteFile($path){
		if(file_exists($path)) unlink($path);
	}
}
?>