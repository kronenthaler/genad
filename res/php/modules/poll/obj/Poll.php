<?
class Poll extends AbstractObject{
	function Poll(){
		$this->tablename='p01_poll';
		$this->primarykey='p01_id';
		$this->title = MSG_POLL_POLL;

		//initialize the fields array
		
		$this->fields['p01_id'] = array(TITLE=>'ID', TYPE=>'integer', VISIBLE=>0, LISTABLE=>0, EDITABLE=>0, SEARCHABLE=>0);
		
		$this->fields['p01_title'] = array(TITLE=>'Title', TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>1, ONFORMPOS=>1);
		$this->fields['p01_question'] = array(TITLE=>'Question', TYPE=>'textfield', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>1, ONLISTPOS=>2, ONFORMPOS=>2);
		$this->fields['p01_start_date'] = array(TITLE=>'Starting date', TYPE=>'datetime', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>3, ONFORMPOS=>3);
		$this->fields['p01_end_date'] = array(TITLE=>'Ending date', TYPE=>'datetime', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>4, ONFORMPOS=>4);
		$this->fields['p01_published'] = array(TITLE=>'Published', TYPE=>'radio', VISIBLE=>1, LISTABLE=>1, EDITABLE=>0, SEARCHABLE=>0, ONLISTPOS=>5, ONFORMPOS=>5);
		
		$this->properties[PAGER]=1;
		$this->properties[SEARCH]=1;
		$this->properties[ADD]=1;
		$this->properties[DELETE]=1;

		$this->childs=array('Options'); //childs classes
		$this->ancestor = ''; //ancestor class
		$this->controls=array(ORDER=>0,DELETE=>1,ADD=>1);
	}
	
	/**
	 *	Cast a single vote over the $idOPtion for the $idPoll
	 */
	function castVote($idPoll, $idOption){
		$opt = new Options();
		if(!$opt->load($idOption)){
			$this->error = new Error(POLL_OPTION_ERROR, MSG_POLL_ERROR_UNKNOWN_OPTION);
			return false;
		}
		
		if(!POLL_ALLOW_GUEST_VOTING){
			$vote = new Votes();
			$vote->insert(array("str_u01_id"=>$_SESSION['user_id'],"str_p02_id"=>$idOption));
		}
		
		return $opt->update($idOption, array('int_p02_votes'=>'p02_votes+1'));
	}
	
	/**
	 *	Return a list with the polls with the published status to 1 AND start_date <= now <= end_date and 
	 */
	function getActivePolls(){
		$now = date("YmdHis");
		return $this->getList(array('p01_published = 1',
									"p01_start_date <= '".$now."'",
									"p01_end_date >= '".$now."'"), 0, -1, 
									'p01_start_date,p01_end_date');
	}
	
	function getOptions(){
		$opts = new Options();
		return $opts->getList(array('p01_id='.$this->p01_id),0,-1,'_sort');
	}
}
?>