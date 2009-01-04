<?
class Profile extends AbstractObject{
	function Profile(){
		$this->tablename='u02_profiles';
		$this->primarykey='u02_id';
		$this->title = MSG_USERS_PROFILES;

		//initialize the fields array
		$this->fields['u02_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u02_name'] = array(TITLE=>'Name', TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['u03_id'] = array(TITLE=>'Permissions', TYPE=>'checkbox', VISIBLE=>1, LISTABLE=>0, EDITABLE=>1, SEARCHABLE=>0,ONLISTPOS=>1, ONFORMPOS=>2);

		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
	
	function insert($DATA=NULL){
		$DATA = $DATA == NULL?$_REQUEST:$DATA;

		if(parent::insert($DATA)){
			$id=mysql_insert_id();
			
			//eliminar los perfiles actuales
			$profilePermissions = new ProfilePermission();
			$profilePermissions->primarykey = 'u02_id';
			
			$permissions = $DATA['str_u03_id'];
			$flag=true;
			
			if(!is_array($permissions))
				$flag = $profilePermissions->insert(array('str_u02_id'=>$id,'str_u03_id'=>$permissions));
			else {
				for($i=0;$i<count($permissions) && $flag;$i++)
					$flag &= $profilePermissions->insert(array('str_u02_id'=>$id,'str_u03_id'=>$permissions[$i]));
			}
			
			if(!$flag)
				$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
				
			return $flag;
		} else {
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
	}
	
	function update($id, $DATA=NULL){
		$DATA = $DATA == NULL?$_REQUEST:$DATA;

		if(parent::update($id,$DATA)){
			//eliminar los perfiles actuales
			$profilePermissions = new ProfilePermission();
			$profilePermissions->primarykey = 'u02_id';
			
			if($profilePermissions->delete($id)) {
				$permissions = $DATA['str_u03_id'];
				$flag=true;
				
				if(!is_array($permissions))
					$flag = $profilePermissions->insert(array('str_u02_id'=>$id,'str_u03_id'=>$permissions));
				else {
					for($i=0;$i<count($permissions) && $flag;$i++)
						$flag &= $profilePermissions->insert(array('str_u02_id'=>$id,'str_u03_id'=>$permissions[$i]));
				}
				
				if(!$flag)
					$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
					
				return $flag;
			} else {
				$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
				return false;
			}
		} else {
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
	}
	
	//TODO: revisar este delete
	function delete($ids){
		//eliminar todas las referencias de u01u02_has
		$profilePermissions= new ProfilePermission();
		$profilePermissions->primarykey = 'u02_id';
		
		return $profilePermissions->delete($ids) && parent::delete($ids);
	}
}
?>
