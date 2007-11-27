<?
class Profile extends AbstractObject{
	function Profile(){
		$this->tablename='u02_profiles';
		$this->primarykey='u02_id';
		$this->title = 'Profile';

		//initialize the fields array
		
		$this->fields['u02_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->fields['u02_name'] = array(TITLE=>'Name', TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
			

		$this->childs=array('Permission'); //childs classes
		$this->ancestor = ''; //ancestor class
	}
}
?>