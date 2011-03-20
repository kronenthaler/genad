<?
class Permission extends AbstractObject{
	function Permission(){
		$this->tablename='u03_permissions';
		$this->primarykey='u03_id';
		$this->title = MSG_USERS_PERMISSION_TITLE;

		//initialize the fields array
		$this->fields['u03_id'] = array(TITLE=>MSG_USERS_PERMISSION_U03_ID, TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u03_name'] = array(TITLE=>MSG_USERS_PERMISSION_U03_NAME, TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['u04_id'] = array(TITLE=>MSG_USERS_PERMISSION_U04_ID, TYPE=>'select', VISIBLE=>1, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>2, ONFORMPOS=>3);
		$this->fields['u03_action'] = array(TITLE=>MSG_USERS_PERMISSION_U03_ACTION, TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>3, ONFORMPOS=>2);
		
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
}
?>
