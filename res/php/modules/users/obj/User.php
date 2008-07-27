<?
class User extends AbstractObject{
	function User(){
		$this->tablename='u01_users';
		$this->primarykey='u01_id';
		$this->title = 'User';
		
		$this->userProfiles = NULL;
		$this->userPermissions = NULL;

		//initialize the fields array
		
		$this->fields['u01_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u01_login'] = array(TITLE=>'Login', TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['u01_password'] = array(TITLE=>'Password', TYPE=>'password', VISIBLE=>1, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>2, ONFORMPOS=>2);
		$this->fields['u01_email'] = array(TITLE=>'E-mail', TYPE=>'email', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>3, ONFORMPOS=>3);
		$this->fields['u02_id'] = array(TITLE=>'Profiles', TYPE=>'checkbox', VISIBLE=>1, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>4, ONFORMPOS=>4);
		
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
	
	function load($id){
		if(parent::load($id)){
			$this->loadPermissions();
			//logOn(print_r($this->userPermissions,true));
			return true;
		}else
			return false;
	}
	
	function insert($DATA=NULL){
		$DATA = $DATA == NULL?$_REQUEST:$DATA;

		if(parent::insert($DATA)){
			$id = mysql_insert_id();
			
			$userProfiles = new UserProfile();
			$profiles = $DATA['str_u02_id'];
			$flag = true;

			if(!is_array($profiles)){
				$flag = $userProfiles->insert(array('str_u01_id'=> $id, 'str_u02_id'=>$profiles));
			} else {
				for($i=0; $i<count($profiles) && $flag; $i++)
					$flag &= $userProfiles->insert(array('str_u01_id' => $id, 'str_u02_id'=>$profiles[$i]));
			}
			
			if(!$flag)
				$this->error = $userProfiles->error;
				
			return $flag;
		} else {
			return false;
		}
	}
	
	function update($id, $DATA=NULL){
		$DATA = $DATA == NULL?$_REQUEST:$DATA;

		if(parent::update($id,$DATA)){
			//eliminar los perfiles actuales
			$userProfiles = new UserProfile();
			$userProfiles->primarykey = 'u01_id';
			
			if($userProfiles->delete($id)) {
				$profiles = $DATA['str_u02_id'];
				$flag=true;
				
				if(!is_array($profiles))
					$flag = $userProfiles->insert(array('str_u01_id'=>$id,'str_u02_id'=>$profiles));
				else {
					for($i=0;$i<count($profiles) && $flag;$i++)
						$flag &= $userProfiles->insert(array('str_u01_id'=>$id,'str_u02_id'=>$profiles[$i]));
				}
				
				if(!$flag)
					$this->error=$userProfiles->error;
					
				return $flag;
			} else {
				$this->error=$userProfiles->error;
				return false;
			}
		} else {
			$this->error=new Error(GENERAL_ERROR,'SQL error: '.mysql_error());
			return false;
		}
	}
	
	function delete($ids){
		//eliminar todas las referencias de u01u02_has
		$userProfiles= new UserProfile();
		$userProfiles->primarykey = 'u01_id';
		
		return $userProfiles->delete($ids) && parent::delete($ids);
	}
	
	/**
	 *	Cargar los permisos asociados a este usuario o al asociado al id
	 */
	function loadPermissions(){
		if($this->userProfiles == NULL)
			$this->loadProfiles();
		
		$userPermissions = new ProfilePermission();
		$userPermissions->tablename .= ',u03_permissions';
		if(is_array($this->userProfiles))
			$userPermissions = $userPermissions->getList(array('u02u03_has.u02_id in ('.implode(',',$this->userProfiles).') AND u02u03_has.u03_id=u03_permissions.u03_id GROUP BY u03_permissions.u03_id'),0,-1,'u04_id ASC');
		else
			$userPermissions = $userPermissions->getList(array('u02u03_has.u02_id = '.$this->userProfiles.' AND u02u03_has.u03_id=u03_permissions.u03_id GROUP BY u03_permissions.u03_id'),0,-1,'u04_id ASC');
		$this->userPermissions = array();

		$prev = $current = '';
		for($i=0;$i<count($userPermissions);$i++){
			$section = new Section();
			$section->load($userPermissions[$i]->u04_id);
			$current = $section->u04_name;
			if($prev != $current)
				$this->userPermissions[$current] = array();
			
			$this->userPermissions[$current][$userPermissions[$i]->u03_action] = true;
			$prev = $current;
		}
	}
	
	function loadProfiles(){
		$userProfiles = new UserProfile();
		$userProfiles = $userProfiles->getList(array('u01_id='.$this->u01_id)); //ids de los profiles que tiene este usuario
		
		for($i=0,$n=count($userProfiles);$i<$n;$i++)
			$this->userProfiles[$userProfiles[$i]->u02_id] = $userProfiles[$i]->u02_id;
	}

	/**
	 *	Revisa si se tiene permiso de ejecutar $action, sobre $section usando el arreglo de $permissions
	 */	
	function youCanDo($action, $section, $permissions=NULL){
		if($permissions == NULL)
			$permissions = $this->userPermissions;

		return $permissions[$section][$action] != NULL;
	}
	
	/**
	 *	Autentica un usuario en base al login y el password no-encriptado 
	 */
	function authenticate($login, $password){
		$this->primarykey = 'u01_login';
	
		return $this->load($login) && 
			   (($this->primarykey='u01_id') != '') && //restore the primary key, just in case
			   (base64_encode($password) == $this->u01_password);
	}
}
?>
