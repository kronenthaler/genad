<?
class UserProfile extends AbstractObject{
	function UserProfile(){
		$this->tablename='u01u02_has';
		$this->primarykey='u01u02_id';
		$this->title = 'User Profiles';
		
		$this->fields['u01_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u02_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u01u02_id']=array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
}
?>
