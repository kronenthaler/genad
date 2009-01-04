<?
class AbstractRelation extends AbstractObject {
	var $primaryKeys = array();	//values of the primary key (each primary key)
	var $classes = array();		//classes in this relation, the order in this array determinates the order in the listing and editing forms.

	function AbstractRelation(){
		
	}

	function getBaseQuery(){
		$tables = '';
		$objs = array();
		for($i=0;$i<count($this->classes);$i++){
			eval("\$obj = new ".$this->classes[$i]."();");
			$tables .= ','.$obj->tablename;
			array_push($objs, $obj);
		}

		$query="SELECT * FROM ".$this->tablename.$tables." WHERE (1=1";
		for($i=0;$i<count($objs);$i++)
			$query .= " AND `".$this->tablename."`.`".$objs[$i]->primarykey."`=`". $objs[$i]->tablename."`.`".$objs[$i]->primarykey."`";

		$query.=')';
		
		return $query;
	}

	/**
	 *	Load one single instance of the relation, the keys must be an array indexed by the primary key of
	 *	the corresponding table in the relation. eg, array('id_1'=>value1, 'id_2'=> value2, ...)
	 *
	 */
	function load($keys){
		$query = $this->getBaseQuery();
		
		if(is_array($keys)){
			foreach($keys as $key => $value)
				$query .= ' AND `'.$this->tablename."`.`".$key."`='".$value."'";
		}else{
			$query .= 'AND `'.$this->primarykey."` = '".$keys."'";
		}
		$rs = mysql_query($query);
		return $this->makeObject($rs,'$this') != NULL;
	}
	
	/**
	 *	Perform the same search of the AbstractObject but appending the first matching
	 *	field from each class in the relation.
	 *	For the searching purposes this function work as-is, but for listing and editing
	 *	the list must be preprocessed to remove the tablename and the backticks. Using those
	 *	tokens for selecting the appropiate set of fields (the classes must be indexed by the tablename)
	 */
	function getFieldsByType($type,$value=true){
		$search=parent::getFieldsByType($type, $value);

		for($i=count($this->classes)-1;$i>=0;$i--){
			eval("\$obj = new ".$this->classes[$i]."();");
			$key = $obj->getFieldsByType($type,$value);
			if(count($key) > 0)
			array_unshift($search, $obj->tablename."`.`".$key[0]); //unshift to keep the order of the classes.
		}

		return $search;
	}

	/**
	 *	Return how many records have the criteria over the table
   	 */
	function totalRows($criteria=NULL){
		$query = str_replace('*','count(*)', $this->getBaseQuery());

		if($criteria!=NULL)
			$query.=' AND '.implode(" AND ",$criteria);
			
		return mysql_result(mysql_query($query),0);
	}

	function getList($criteria=NULL, $ini=0, $cant=-1, $orderby=''){
		$query = $this->getBaseQuery();

		if($criteria!=NULL)
			$query.=' AND '.implode(" AND ",$criteria);

		if($orderby!='') $query.= " ORDER BY ".$orderby;
		if($cant!=-1) $query.=" LIMIT ".$ini.",".$cant;

		//logOn($query);
		return $this->makeObjects(mysql_query($query));
	}

	function getXMLAncestors($currentObj,$params=NULL){
		if($params==NULL) $params=$_REQUEST;
		$ret='<basepath><![CDATA['.HTTP_ROOT.'/admin/]]></basepath>';
		$ret.='<ancestors>';
		$ancestor=$currentObj->ancestor;
		$ret1="";
		while($ancestor!=''){
			eval("\$parent=new ".$ancestor."();");
			$ret1='<ancestor id="'.$parent->primarykey.'" value="'.$params[$parent->primarykey].'" class="'.$ancestor.'"><![CDATA['.$parent->title.']]></ancestor>'.$ret1;
			$ancestor=$parent->ancestor;
		}
		$ret.=$ret1.'</ancestors>';

		$ret.='<relations>';
		for($i=0;$i<count($currentObj->relations);$i++){
			eval("\$relation = new ".$currentObj->relations[$i]."();");
			$ret.='<relation active="'.(get_class($this)==$currentObj->relations[$i]?'selected':'').'" class="'.$currentObj->relations[$i].'" currentClass="'.get_class($currentObj).'"><![CDATA['.$relation->title.']]></relation>';
		}
		$ret.='</relations>';
		return $ret;
	}

	/**
	 *	Get the list of elements to show from the $objects array and intersected with the $this->fields array
	 */
	function getXMLList($objects,$criteria=NULL){
		$objs = array();
		for($i=0;$i<count($this->classes);$i++){
			eval("\$obj = new ".$this->classes[$i]."();");
			$tables .= ','.$obj->tablename;
			//$objs[$this->classes[$i]]=$obj;
			array_push($objs, $obj);
		}
		
		$ret.="<properties>";
		foreach($this->properties as $key => $value)
			$ret.="<".$key.">".$value."</".$key.">";
		$ret.="</properties>";
		$ret.="<list>";
		$ret.="<listable>";

		uasort($this->fields, create_function('$a,$b','return $a[ONLISTPOS] - $b[ONLISTPOS];'));
		$listables=$this->getFieldsByType(LISTABLE);

		for($i=0,$n=count($listables);$i<$n;$i++){
			if(strpos($listables[$i], '`.`')!==FALSE){
				$class = substr($listables[$i],0, strpos($listables[$i], '`.`'));
				$listables[$i] = substr($listables[$i], strpos($listables[$i], '`.`')+3);
				for($j=0;$j<count($objs);$j++){
					if($objs[$j]->tablename == $class){
						$this->fields[$listables[$i]] = $objs[$j]->fields[$listables[$i]];
						$this->fields[$listables[$i]][TITLE] .= ' ('.$objs[$j]->title.') ';
						break;
					}
				}
			}
			$ret.='<field map="'.$listables[$i].'" name="'.$this->fields[$listables[$i]][TITLE].'" type="'.$this->fields[$listables[$i]][TYPE].'"/>';
		}

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
				if(strpos($listables[$j], '`.`')===FALSE){ //just for safety
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
			}
			$ret.='</instance>';
		}

		$ret.="</list>";
		return $ret;
	}

	/**
	 *	@return the XML for the form rendering
	 */
	function getXMLForm($options,$currentClass){
		$objs = array();
		for($i=0;$i<count($this->classes);$i++){
			eval("\$obj = new ".$this->classes[$i]."();");
			$tables .= ','.$obj->tablename;
			array_push($objs, $obj);
		}

		//sort the field by their positions in the form.
		uasort($this->fields, create_function('$a,$b','return $a[ONFORMPOS] - $b[ONFORMPOS];'));
		$visibles=$this->getFieldsByType(VISIBLE);

		$ret='<fields>';
		for($i=0,$n=count($visibles);$i<$n;$i++){
			if(strpos($visibles[$i], '`.`')!==FALSE){
				$class = substr($visibles[$i],0, strpos($visibles[$i], '`.`'));
				$visibles[$i] = substr($visibles[$i], strpos($visibles[$i], '`.`')+3);
				for($j=0;$j<count($objs);$j++){
					if($objs[$j]->tablename == $class){
						eval("\$value=\$this->".$objs[$j]->primarykey."=\$this->".$visibles[$i].';');
						$this->fields[$objs[$j]->primarykey] = $objs[$j]->fields[$visibles[$i]];
						$this->fields[$objs[$j]->primarykey][TITLE] .= ' ('.$objs[$j]->title.')';
						$this->fields[$objs[$j]->primarykey][TYPE] = $objs[$j]->primarykey == $currentClass->primarykey?'keylabel':'select';
						$visibles[$i] = $objs[$j]->primarykey;
						break;
					}
				}
			}
			eval("\$value=\$this->".$visibles[$i].';');
			$current=$this->fields[$visibles[$i]];
			$option=$options[$visibles[$i]];
			if(is_array($option))
				$keys=array_keys($option);

			$ret.='<'.$current[TYPE].' map="'.$visibles[$i].'" name="'.$current[TITLE].'"';
			if($option[$keys[0]]!=NULL && !is_array($option[$keys[0]])) //append the options
				foreach($option as $key => $val)
					if($option[$key]!=NULL && !is_array($val))
						$ret.=' '.$key.'="'.$val.'" ';
			$ret.='>';
			if(($option[$keys[0]]!=NULL && is_array($option[$keys[0]])) || $current[TYPE] == 'select'){ //append the options
				for($j=0,$m=count($option);$j<$m;$j++){
					if(is_array($option[$j]))
						$ret.='<option name="'.$option[$j]['name'].'"
									   value="'.$option[$j]['value'].'"
									   selected="'.$option[$j]['selected'].'"
									   onclick="'.$option[$j]['onclick'].'"
									   onchange="'.$option[$j]['onchange'].'"/>';
				}
			}else
				$ret.="<![CDATA[".htmlspecialchars_decode(stripslashes($value))."]]>";

			$ret.= '</'.$current[TYPE].'>';
		}
		$ret.='</fields>';
		return $ret;
	}
}
?>
