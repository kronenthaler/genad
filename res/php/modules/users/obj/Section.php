<?
class Section extends AbstractObject{
	function Section(){
		$this->tablename='u04_sections';
		$this->primarykey='u04_id';
		$this->title = MSG_USERS_SECTION_TITLE;

		//initialize the fields array
		$this->fields['u04_id'] = array(TITLE=>MSG_USERS_SECTION_U04_ID, TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['u04_name'] = array(TITLE=>MSG_USERS_SECTION_U04_NAME, TYPE=>'textfield', VISIBLE=>1, REQUIRED=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array(); //childs classes
		$this->ancestor = ''; //ancestor class
	}
}
?>