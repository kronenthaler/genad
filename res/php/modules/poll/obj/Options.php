<?
class Options extends AbstractObject{
	function Options(){
		$this->tablename='p02_options';
		$this->primarykey='p02_id';
		$this->title = MSG_POLL_OPTIONS;
		$this->order = 'p02_order';

		//initialize the fields array
		$this->fields['p01_id'] = array(TITLE=>'PARENT_ID', TYPE=>'textfield', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		$this->fields['p02_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->fields['p02_label'] = array(TITLE=>MSG_POLL_P02_LABEL, TYPE=>'textfield', VISIBLE=>1, REQUIRED=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['p02_votes'] = array(TITLE=>MSG_POLL_P02_VOTES, TYPE=>'label', VISIBLE=>1, REQUIRED=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>2, ONFORMPOS=>3);
		$this->fields['_sort'] = array(TITLE=>'Order', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>-1, ONFORMPOS=>-1);
		
		$this->properties[SORTABLE]=1;
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;
		
		
		$this->childs=array(); //childs classes
		if(!POLL_ALLOW_GUEST_VOTING)
			array_push($this->childs,'Votes');
		$this->ancestor = 'Poll'; //ancestor class
	}
}
?>