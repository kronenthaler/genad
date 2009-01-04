<?
class Votes extends AbstractObject{
	function Votes(){
		$this->tablename='p03_votes';
		$this->primarykey='p03_id';
		$this->title = MSG_POLL_VOTES;

		//initialize the fields array
		$this->fields['p02_id'] = array(TITLE=>'PARENT_ID', TYPE=>'textfield', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['p03_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->fields['u01_id'] = array(TITLE=>'User', TYPE=>'label', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['u01_login'] = array(TITLE=>'User', TYPE=>'label', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array(); //childs classes
		$this->ancestor = 'Options'; //ancestor class
	}
}
?>