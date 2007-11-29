<?
class ProfilePermission extends AbstractObject{
	function ProfilePermission(){
		$this->tablename='u02u03_has';
		$this->primarykey='u02u03_id';
		$this->title = 'Profile Permissions';
		
		$this->fields['u02_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u03_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u02u03_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
}
?>