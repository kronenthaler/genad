<?
/** Constants for the ubication of the property in the descriptor array */
define("TITLE",0);
define("TYPE",1);
define("VISIBLE",2);
define("LISTABLE",3);
define("EDITABLE",4);
define("SEARCHABLE",5);
define("ONLISTPOS",6);
define("ONFORMPOS",7);
define("REQUIRED",8);

define("SORTABLE","sortable");
define("PAGER","pager");
define("SEARCH","searchable");
define("ADD","add");
define("DELETE","delete");

/** Constants for the error codes */
define(GENERAL_ERROR,1);

/** Constants for typedef */
define('DATE',1);
define('TIME',2);

class AbstractObject{
	var $tablename,$primarykey;
	var $prefixes = array('str_','int_');	/** valid prefixes */
	var $bounds = array("'",'');			/** sourounders for each prefix */
	var $fields = array();					/** array for the definition of each field, map, and properties, one entry per field */
	var $childs = array();					/** array with the names of the classes related with, under this in the hierarchy */
	var $properties = array();				/** array with the properties relative to the class (like sortable, pager, etc)*/
	var $ancestor = '';						/** name of the ancestor class in the hierarchy */
	var $title = '';						/** title of this entity in the list or mod pages */
	var $error = '';
	var $id = 0;							/** last id inserted */
	var $relations = array();				/** array with the names of the relation objects for this object (apply just for entities)*/
	
	/**
	 *	Initialize this object with the data in the record pointed by $id.
	 *	@return true if complete successfully
	 */
	function load($id){
		$query="SELECT * FROM ".$this->tablename." WHERE ".$this->primarykey."='".$id."'";
		$rs=mysql_query($query);
		return $this->makeObject($rs,'$this') != NULL && ($this->id = $id) != NULL;
	}
	
	/**
	 *	Insert a new record with the data in the $DATA array.
	 *	Just the data with the prefixes in $this->prefixes are added to the query.
	 *	If a field has a valid prefix but isn't in the schema for this table is ignored.
	 *	@return true if complete successfully 
	 */
	function insert($DATA=NULL){
		$DATA=$DATA==NULL?$_REQUEST:$DATA;
		$query="INSERT INTO ".$this->tablename." (";		
		$values=" VALUES (";
		
		$keys=array_keys($DATA);
		for($i=0,$k=0,$n=count($keys);$i<$n;$i++){
			for($j=0,$m=count($this->prefixes);$j<$m;$j++){
				if(substr($keys[$i],0,strlen($this->prefixes[$j]))==$this->prefixes[$j] && 
					$this->fields[$str=substr($keys[$i],strlen($this->prefixes[$j]))]!=NULL){
					if($this->fields[$str][TYPE]=='password') $DATA[$keys[$i]]=base64_encode($DATA[$keys[$i]]);
					if($this->fields[$str][TYPE]=='time') $DATA[$keys[$i]]=substr(str_replace(':','',$DATA[$keys[$i]]),0,6);
					if($this->fields[$str][TYPE]=='datetime') $DATA[$keys[$i]]=$DATA[$keys[$i]."_date"].substr(str_replace(':','',$DATA[$keys[$i]."_time"]),0,6);
					if(is_array($DATA[$keys[$i]])) continue; //array skip it
					
					$query.=($k>0?',':'').'`'.$str.'`';
					$values.=($k>0?',':'').$this->bounds[$j].addslashes(stripslashes(htmlspecialchars($DATA[$keys[$i]]))).$this->bounds[$j];
					$k++;
				}
			}
		}
		$query.=") ".$values.")";
		//logOn($query);
		if(!mysql_query($query)){
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
		$this->id = mysql_insert_id();
		
		if($this->properties[SORTABLE])
			return $this->update($this->id, array('str__sort'=>$this->id));
		
		return true;
	}
	
	/**
	 *	Update the record pointer by id, with the data in the $DATA array.
	 *	Just the data with the prefixes in $this->prefixes are added to the query.
	 *	If a field has a valid prefix but isn't in the schema for this table is ignored.
	 *	@return true if complete successfully
	 */
	function update($id, $DATA=NULL){
		$DATA=$DATA==NULL?$_REQUEST:$DATA;
		$query="UPDATE ".$this->tablename." SET ";		
		$values=" WHERE ".$this->primarykey."='".$id."'";
		
		$keys=array_keys($DATA);
		for($i=0,$k=0,$n=count($keys);$i<$n;$i++){
			for($j=0,$m=count($this->prefixes);$j<$m;$j++){
				if(substr($keys[$i],0,strlen($this->prefixes[$j]))==$this->prefixes[$j] && 
					$this->fields[$str=substr($keys[$i],strlen($this->prefixes[$j]))]!=NULL){
					if($this->fields[$str][TYPE]=='password') $DATA[$keys[$i]]=base64_encode($DATA[$keys[$i]]);
					if($this->fields[$str][TYPE]=='time') $DATA[$keys[$i]]=substr(str_replace(':','',$DATA[$keys[$i]]),0,6);
					if($this->fields[$str][TYPE]=='datetime') $DATA[$keys[$i]]=$DATA[$keys[$i]."_date"].substr(str_replace(':','',$DATA[$keys[$i]."_time"]),0,6);
					if(is_array($DATA[$keys[$i]])) continue; //array skip it
					 
					$query.=($k>0?',':'').'`'.$str.'`'."=".$this->bounds[$j].addslashes(stripslashes(htmlspecialchars(''.$DATA[$keys[$i]]))).$this->bounds[$j];
					$k++;
				}
			}
		}
		$query.=$values;
		//logOn($query);
		if(!mysql_query($query)){
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
		return true;
	}
	
	/**
	 *	Delete multiples records if ids is an array, otherwise, delete a 'single' record.
	 * 	For the tables with subentities or relations those relations are deleted recursively.
	 *	@return true if complete successfully
	 */
	function delete($ids){
		//delete the relations with this instance, childs.
		if(count($ids)==0) return true;
		if(count($this->childs)>0){
			$allids=array();//of arrays
			$objs=array();//of AbstractObjects
			
			for($i=0,$n=count($this->childs);$i<$n;$i++){
				eval("\$objs[\$i]=new ".$this->childs[$i]."();");
				$allids[$i]=array();
				
				$query=" SELECT ".$objs[$i]->primarykey." FROM ".$objs[$i]->tablename." WHERE ".$this->primarykey;
				if(is_array($ids)) $query.=" in ('".implode("','",$ids)."')";
				else $query.=" = '".$ids."'";
				
				$rs=mysql_query($query);
				
				if(!$rs) continue;
				for($j=0,$m=mysql_num_rows($rs);$j<$m;$j++)
					array_push($allids[$i],mysql_result($rs,$j));
			}
		}

		$flag=true;
		if(count($this->relations)>0){
			for($i=0,$n=count($this->relations);$i<$n;$i++){
				eval("\$obj = new ".$this->relations[$i].'();');
				$obj->primarykey = $this->primarykey;
				$flag &= $obj->delete($ids);
			}
		}
		//delete the files pointed by the type fields file or image	
		$this->deleteFiles($ids);
		
		$query="DELETE FROM ".$this->tablename." WHERE ".$this->primarykey;	
		if(is_array($ids)) $query.=" IN ('".implode("','",$ids)."')";
		else $query.=" = '".$ids."'";
		
		for($i=0,$n=count($objs);$i<$n && $flag;$i++)
			$flag &= $objs[$i]->delete($allids[$i]);
			
		//echo $query;
		if(!(mysql_query($query) && $flag)){
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
		return true;
	}
	
	function deleteFiles($ids){
		$files=$this->getFieldsByType(TYPE,'file');
		$files=array_merge($files, $this->getFieldsByType(TYPE,'image'));
		
		if(count($files)>0){
			$query="SELECT ".implode(",", $files)." FROM ".$this->tablename." WHERE ".$this->primarykey;
			if(is_array($ids)) $query.=" IN ('".implode("','",$ids)."')";
			else $query.=" = '".$ids."'";
			
			$rs=mysql_query($query);
			if(!$rs) return;
			for($j=0,$m=mysql_num_rows($rs);$j<$m;$j++)
				for($i=0,$n=count($files);$i<$n;$i++){
					if(mysql_result($rs, $j, $files[$i])!=''){
						//TODO: eliminate every file with the same timestamp between the prefix and the last dot
						unlink(ROOT.'/'.mysql_result($rs, $j, $files[$i]));
					}
				}
		}
	}
	
	/**
	 *	Return a list of objects of the invocator type. 
	 *	Following the criteria, begin in $ini and containning at most $cant records, ordered by $orderby.
	 */
	function getList($criteria=NULL, $ini=0, $cant=-1, $orderby=''){
		$query="SELECT * FROM ".$this->tablename;
		if($criteria!=NULL)
			$query.=" WHERE ".implode(" AND ",$criteria);
		
		if($orderby!='') $query.= " ORDER BY ".$orderby; 		
		if($cant!=-1) $query.=" LIMIT ".$ini.",".$cant;
		
		//echo $query.'<br/>';
		//logOn($query);
		return $this->makeObjects(mysql_query($query));
	}
	
	/**
	 *	Build a list of objects from the resultset
	 */	
	function makeObjects($resultSet){
		$array=array();
		if(!$resultSet) return NULL;
		for($i=0,$n=mysql_num_rows($resultSet);$i<$n;$i++){
			eval("\$obj=new ".get_class($this)."();");
			for($j=0;$j<mysql_num_fields($resultSet);$j++){
				$value = mysql_result($resultSet,$i,mysql_field_name($resultSet,$j));
				eval("\$obj->".mysql_field_name($resultSet,$j)." = '".htmlspecialchars_decode(addslashes($value))."';");
			}
			$array[$i]=$obj;
		}
		return $array;
	}
	
	/**
	 *	Build a single object from the resultset
	 */	
	function makeObject($resultSet,$target='$obj'){
		if($resultSet!=NULL && mysql_num_rows($resultSet)>0){
			for($j=0;$j<mysql_num_fields($resultSet);$j++){
				$value = mysql_result($resultSet,0,mysql_field_name($resultSet,$j));
				//$value = addslashes($value);
				eval($target."->".mysql_field_name($resultSet,$j)." = '".htmlspecialchars_decode(addslashes($value))."';");
			}
			return $target;
		}else 
			return NULL;
	}
	
	/**
	 *	Return how many records have the criteria over the table
   	 */
	function totalRows($criteria=NULL){
		$query="SELECT count(*) FROM ".$this->tablename;
		if($criteria!=NULL)
			$query.=" WHERE ".implode(" AND ",$criteria);
		//logOn($query);
		return mysql_result(mysql_query($query),0);
	}
	
	/**
	 *	Search the record that contains in the same field all the tokens in the string $tokens.
	 *	(id like '%tok1%' AND id like '%tok2%' ... ) OR 
	 *	(name like '%tok1%' AND name like '%tok2%' ... ) ...
	 *	The fields used for the search are the ones with the property SEARCHABLE in not-false
	 */
	function search($tokens,&$criteria=NULL,$ini=0,$cant=-1,$orderby=''){
		if($criteria==NULL) $criteria=array();
		$toks=explode(' ',$tokens);
		$str = array();
		$keys = $this->getFieldsByType(SEARCHABLE); //one position for each field to search
		
		for($i=0,$n=count($toks);$i<$n;$i++)
			for($j=0,$m=count($keys);$j<$m;$j++)
				$str[$j].=($i>0?' AND ':'').'`'.$keys[$j]."` like '%".$toks[$i]."%'";

		if(count($keys)>0)
			array_unshift($criteria,'(('.implode(' ) OR (',$str).'))');	
			
		return $this->getList($criteria,$ini,$cant,$orderby);
	}
	
	/**
	 * @param $ids Array with the ids to sort, the sorting is FIFO.
	 * @return boolean 
	 */
	function order($ids){
		$long = count($ids);	
		for($i=0;$i<$long;$i++){
			$query = "UPDATE ".$this->tablename." SET `_sort` = ".($i+1)." WHERE `".$this->primarykey."`='".$ids[$i]."'";
			//logOn($query);
			$rs &= mysql_query($query); 
		}
		return $rs?true:false;
	}

	/**
	 *	Swap the record pointed by $id with the next pointed for the relation $mode
	 */
	function swap($id, $mode){
		$this->load($id);
		$next = $this->getList(array("_sort".($mode=='up'?'<':'>').$this->_sort),0,1,'_sort '.($mode == 'up'?'desc':'asc'));
		if(count($next) == 0) return false;

		eval("\$next_id = \$next[0]->".$this->primarykey.";");
		return $this->update($id,array('str__sort'=>$next[0]->_sort)) && $next[0]->update($next_id,array('str__sort'=>$this->_sort));
	}

	/** 
	 *	Retrieve a list with the name of the fields with the property $type in not-false.
	 */
	function getFieldsByType($type,$value=true){
		$search=array();
		$keys=array_keys($this->fields);
		for($i=0,$n=count($keys);$i<$n;$i++){
			if($this->fields[$keys[$i]][$type]==$value)
				array_push($search,$keys[$i]);
		}
		return $search;
	}
	
	/**
	 *	@return a querystring-like with the ids of the ancestors of this instance
	 */
	function getAncestorsIds($array=NULL){
		if($array==NULL) $array=$_REQUEST;
		
		$ancestor=$this->ancestor;
		while($ancestor!=''){
			eval("\$parent=new ".$ancestor."();");
			$str .= '&'.$parent->primarykey.'='.$array[$parent->primarykey];
			$ancestor = $parent->ancestor;
		}
		return $str;
	}

	function getError(){
		return $this->error->description;
	}
	
	//<!--------------------------------- METHODS FOR XML GENERATION ---------------------------------------------->
	/**
	 *	Retrieve the ancestors parameters section of the list XML
	 */
	function getXMLAncestors($params=NULL){
		if($params==NULL) $params=$_REQUEST;
		$ret='<basepath><![CDATA['.HTTP_ROOT.'/admin/]]></basepath>';
		$ret.='<ancestors>';
		$ancestor=$this->ancestor;
		$ret1="";
		while($ancestor!=''){
			eval("\$parent=new ".$ancestor."();");
			$ret1='<ancestor id="'.$parent->primarykey.'" value="'.$params[$parent->primarykey].'" class="'.$ancestor.'"><![CDATA['.$parent->title.']]></ancestor>'.$ret1;
			$ancestor=$parent->ancestor;
		}
		$ret.=$ret1.'</ancestors>';

		$ret.='<relations>';
		for($i=0;$i<count($this->relations);$i++){
			eval("\$relation = new ".$this->relations[$i]."();");
			$ret.='<relation active="" class="'.$this->relations[$i].'" currentClass="'.get_class($this).'"><![CDATA['.$relation->title.']]></relation>';
		}
		$ret.='</relations>';
		return $ret;
	}
	
	/**
	 *	Create the path to the root entity crossing the hierarchy backwards
	 */
	function getXMLTitle(){
		return '<title><![CDATA['.$this->title.']]></title>';
	}

	function getXMLBack($params=NULL){
		if($params==NULL) $params=$_REQUEST;
		//eliminar del arreglo el id de mi ancestro
		if($this->ancestor!='')
			eval("\$parent=new ".$this->ancestor."();");
			
		$ret='<back entity="'.$this->ancestor.'" title="'.($parent!=NULL?$parent->title:'').'">';
		$keys=array_keys($params);
		$ancestor=$this->ancestor;
		while($ancestor!=''){
			eval("\$parent=new ".$ancestor."();");
			$ret.='<ancestor id="'.$parent->primarykey.'" value="'.$params[$parent->primarykey].'" class="'.$ancestor.'"><![CDATA['.$parent->title.']]></ancestor>';
			$ancestor=$parent->ancestor;
		}
		$ret.='</back>';
		return $ret;
	}
	
	/**
	 *	Info related with the pager system
	 */
	function getXMLListPager($total, $pageSize, $offset){
		return '<pager>
					<total-rows>'.$total.'</total-rows>
					<offset>'.$offset.'</offset>
					<page-size>'.$pageSize.'</page-size>
				</pager>';
	}

	/**
	 *	Get the list of elements to show from the $objects array and intersected with the $this->fields array
	 */
	function getXMLList($objects,$criteria=NULL){
		$ret.="<properties>";
		foreach($this->properties as $key => $value)
			$ret.="<".$key.">".$value."</".$key.">";
		$ret.="</properties>";
		$ret.="<list>";
		$ret.="<listable>";

		uasort($this->fields, create_function('$a,$b','return $a[ONLISTPOS] - $b[ONLISTPOS];'));
		$listables=$this->getFieldsByType(LISTABLE);

		for($i=0,$n=count($listables);$i<$n;$i++)
			$ret.='<field map="'.$listables[$i].'" name="'.$this->fields[$listables[$i]][TITLE].'" type="'.$this->fields[$listables[$i]][TYPE].'"/>';

		for($i=0,$n=count($this->childs);$i<$n;$i++){
			eval("\$childTitle=new ".$this->childs[$i]."();");
			$ret.='<child name="'.$childTitle->title.'"/>';
		}
			
		$ret.="</listable>";

		for($i=0,$n=count($objects);$i<$n;$i++){
			eval("\$key =\$objects[\$i]->".$this->primarykey.';');
			$ret.='<instance primarykey="'.$this->primarykey.'" key="'.$key.'">';
			
			foreach($this->childs as $name){
				eval("\$child=new ".$name."();");
				if($criteria[$name]!=NULL)
					$ret.='<child name="'.$name.'" count="'.$child->totalRows(array_merge($criteria[$name],array($this->primarykey."='".$key."'"))).'"/>';
				else
					$ret.='<child name="'.$name.'" count="'.$child->totalRows(array($this->primarykey."='".$key."'")).'"/>';
			}
				
			for($j=0,$m=count($listables);$j<$m;$j++){
				eval("\$value=\$objects[$i]->".$listables[$j].';');
				if($this->fields[$listables[$j]][TYPE]=='date')
					$value=format($value,DATE,'D/M/Y');
				if($this->fields[$listables[$j]][TYPE]=='datetime')
					$value=format($value,DATE|TIME,'D/M/Y H:m:s');
				if($this->fields[$listables[$j]][TYPE]=='time')
					$value=format($value,TIME,'H:m:s');
				if($this->fields[$listables[$j]][TYPE]=='image')
					$value=str_replace(DEF_PREFIX,DEF_THUMB_PREFIX,$value);
				
				$ret.='<field map="'.$listables[$j].'"><![CDATA['.$value.']]></field>';
			}
			$ret.='</instance>';
		}
		
		$ret.="</list>";
		return $ret;
	}
	
	/**
	 *	@return the XML for the form rendering
	 */
	function getXMLForm($options){
		//sort the field by their positions in the form.
		uasort($this->fields, create_function('$a,$b','return $a[ONFORMPOS] - $b[ONFORMPOS];'));
		$visibles=$this->getFieldsByType(VISIBLE);
		
		$ret='<fields>';
		for($i=0,$n=count($visibles);$i<$n;$i++){
			eval("\$value=\$this->".$visibles[$i].';');
			$current=$this->fields[$visibles[$i]];
			$option=$options[$visibles[$i]];
			if(is_array($option))
				$keys=array_keys($option);
			
			$ret.='<'.$current[TYPE].' map="'.$visibles[$i].'" name="'.$current[TITLE].'"';
			if($option[$keys[0]]!=NULL && !is_array($option[$keys[0]])) //append the options
				foreach($option as $key => $val)
					$ret.=' '.$key.'="'.$val.'" ';
			$ret.='>';
			if(($option[$keys[0]]!=NULL && is_array($option[$keys[0]])) || $current[TYPE] == 'select'){ //append the options
				for($j=0,$m=count($option);$j<$m;$j++){
					if(is_array($option[$j]))
						$ret.='<option name="'.$option[$j]['name'].'" 
									   value="'.$option[$j]['value'].'" 
									   selected="'.$option[$j]['selected'].'" 
									   onclick="'.$option[$j]['onclick'].'"/>';	
				}
			}else
				$ret.="<![CDATA[".htmlspecialchars_decode(stripslashes($value))."]]>";
			
			$ret.= '</'.$current[TYPE].'>';
		}
		$ret.='</fields>';
		return $ret;
	}

	function getXMLJSValidator($options){
		uasort($this->fields, create_function('$a,$b','return $a[ONFORMPOS] - $b[ONFORMPOS];'));
		$visibles=$this->getFieldsByType(VISIBLE);

		$ret = '<validator language="javascript"><![CDATA[';
		$ret.= 'function validate'.get_class($this).'(f){';
		$ret.= '	return true ';
		for($i=0,$n=count($visibles);$i<$n;$i++){
			$ret.= $this->getJSString($visibles[$i],$options);
		}
		$ret.= "}";
		$ret.= ']]></validator>';

		return $ret;
	}

	function getJSString($name,$options){
		$prefix = 'str_';

		//dependiendo del campo se puede necesitar un prefix para la llamada.
		if($options[$name] != NULL &&
		   is_array($options[$name]) &&
		   $options[$name]['prefix']!='')
			$prefix = $options[$name]['prefix'];
		
		$ret = '';
		switch($this->fields[$name][TYPE]){
			case 'email':
				$ret.= ' && isValidEmail(f.'.$prefix.$name.',"'.$this->fields[$name][TITLE].'",'.($this->fields[$name][REQUIRED]?'true':'false').')';
				break;
			case 'decimal':
				$ret.= ' && isValidDecimal(f.'.$prefix.$name.',"'.$this->fields[$name][TITLE].'",'.($this->fields[$name][REQUIRED]?'true':'false').')';
				break;
			case 'integer':
				$ret.= ' && isValidInteger(f.'.$prefix.$name.',"'.$this->fields[$name][TITLE].'",'.($this->fields[$name][REQUIRED]?'true':'false').')';
				break;
			case 'password':
				$ret.= ' && isValidPassword(f.'.$prefix.$name.', f.conf_'.$prefix.$name.', "'.$this->fields[$name][TITLE].'",'.($this->fields[$name][REQUIRED]?'true':'false').')';
				break;
			case 'richtext':
				$ret.= ' && isValidRTE(f.'.$prefix.$name.',"rte_'.$name.'","'.$this->fields[$name][TITLE].'",'.($this->fields[$visibles[$i]][REQUIRED]?'true':'false').')';
				break;
			case 'datetime':
				if($this->fields[$name][REQUIRED]){
					$ret.= ' && isRequired(f.'.$prefix.$name.'_date,"'.$this->fields[$name][TITLE].'")';
					$ret.= ' && isRequired(f.'.$prefix.$name.'_time,"'.$this->fields[$name][TITLE].'")';
				}
				break;
			default:
				if($this->fields[$name][REQUIRED])
					$ret.= ' && isRequired(f.'.$prefix.$name.',"'.$this->fields[$name][TITLE].'")';
				break;
		}
		return $ret;
	}
	//<!--------------------------------- METHODS FOR OTHER XML GENERATION ---------------------------------------------->
}
?>